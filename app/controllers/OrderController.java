package controllers;

import models.OrderItem;
import models.OrderTCFS;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by alexander on 12/20/14.
 */
@Security.Authenticated(SecuredController.class)
public class OrderController extends Controller {

    public static Result place() {
        return ok(views.html.placeOrder.render(User.find.byId(request().username()), OrderItem.findAll()));
    }

    public static Result active() {
        return ok(views.html.activeOrders.render(User.find.byId(request().username()), OrderTCFS.findActiveByUser(User.find.byId(request().username()))));
    }

}
