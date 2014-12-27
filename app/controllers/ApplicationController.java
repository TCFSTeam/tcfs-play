package controllers;
/**
 * Created by alexander on 10/12/14.
 */

import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;

import static play.data.Form.form;

public class ApplicationController extends Controller {

    public static Result GO_HOME = redirect(
            routes.ApplicationController.login()
    );

    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        User user = User.findByEmail(loginForm.data().get("email").toString());
        if (user == null) {
            return badRequest(login.render(loginForm));
        } else {
            session().clear();
            session("email", loginForm.data().get("email"));
            return redirect(
                    routes.OrderController.active()
            );
        }
    }

    /**
     * Login and set the session.
     *
     * @return Dash page
     */
    public static Result login() {
        return ok(
                login.render(form(Login.class))
        );
    }

    /**
     * Logout and clean the session.
     *
     * @return Index page
     */
    public static Result logout() {
        session().clear();
        return GO_HOME;
    }


    public static class Login {

        public String email;
        public String password;

        public String validate() {
            if (User.authenticate(email, password) == null) {
                return "Invalid username or password";
            }
            return null;
        }

    }
}
