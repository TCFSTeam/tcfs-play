package controllers;

/**
 * Created by alexander on 10/12/14.
 */

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class SecuredController extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("email");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect(controllers.routes.ApplicationController.login());
    }
}