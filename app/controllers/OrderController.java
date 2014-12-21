package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by alexander on 12/20/14.
 */
@Security.Authenticated(Secured.class)
public class OrderController extends Controller {

    public static Result place() {
        return ok(views.html.placeOrder.render(User.find.byId(request().username())));
    }

    public static Result active() {
        return ok(views.html.activeOrders.render(User.find.byId(request().username())));
    }

}
