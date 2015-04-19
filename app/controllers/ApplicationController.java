package controllers;
/**
 * Created by alexander on 10/12/14.
 */

import models.UserTCFS;
import play.Routes;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;

import static play.data.Form.form;

public class ApplicationController extends Controller {

    public static Result GO_HOME = redirect(
            controllers.routes.ApplicationController.login()
    );

    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        UserTCFS userTCFS = UserTCFS.findByEmail(loginForm.data().get("email").toString());
        if (userTCFS == null) {
            return badRequest(login.render(loginForm));
        } else {
            session().clear();
            session("email", loginForm.data().get("email"));
            return redirect(
                    routes.OrderController.active()
            );
        }
    }

    /*
    * Custom javascript reverse-routing
     */
    public static Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(Routes.javascriptRouter("jsRoutes",
                controllers.routes.javascript.OrderController.setReady(),
                controllers.routes.javascript.OrderController.setTable(),
                controllers.routes.javascript.OrderController.setGuests()
        ));
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
            if (UserTCFS.authenticate(email, password) == null) {
                return "Invalid username or password";
            }
            return null;
        }
    }
}
