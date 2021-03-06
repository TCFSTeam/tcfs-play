package controllers;

/**
 * Created by alexander on 10/12/14.
 */

import models.UserTCFS;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Manage userSettings related operations.
 */
@Security.Authenticated(SecuredController.class)
public class UserController extends Controller {

    /**
     * Display the userSettings dashboard.
     */
    public static Result settings() {
        return ok(views.html.userSettings.render(UserTCFS.find.byId(request().username())));
    }

}

