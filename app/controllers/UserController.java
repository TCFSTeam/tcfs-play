package controllers;

/**
 * Created by alexander on 10/12/14.
 */

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.user;

/**
 * Manage user related operations.
 */
@Security.Authenticated(SecuredController.class)
public class UserController extends Controller {

    /**
     * Display the user dashboard.
     */
    public static Result index() {
        return ok(user.render(User.find.byId(request().username())));
    }

}

