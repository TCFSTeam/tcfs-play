package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.login;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Ok. Hello, motherfucker!"));
    }

    public static Result login() {
        return ok(
                login.render()
        );
    }

    public static Result authenticate() {
        return ok();
    }

    public static class Login {

        public String email;
        public String password;

    }
}
