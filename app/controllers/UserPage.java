package controllers;

/**
 * Created by test on 10/12/14.
 */

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.user;

/**
 * Manage projects related operations.
 */
@Security.Authenticated(Secured.class)
public class UserPage extends Controller {

    /**
     * Display the dashboard.
     */
    public static Result index() {
        return ok(user.render(User.find.byId(request().username())));
    }


}

