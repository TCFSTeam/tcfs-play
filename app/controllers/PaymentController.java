package controllers;

import models.OrderTCFS;
import models.UserTCFS;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.activeOrders;

/**
 * Created by Alexander on 4/1/2015.
 */
@Security.Authenticated(controllers.SecuredController.class)
public class PaymentController extends Controller {
    /**
     * Pay order
     */
    public static Result payClose(Integer orderId) {
        OrderTCFS.pay(orderId);
        return ok(activeOrders.render(UserTCFS.find.byId(request().username())));
    }

    /**
     * Proceed to pay order
     */
    public static Result pay(Integer id) {
        OrderTCFS.proceedToPay(id);
        return ok(activeOrders.render(UserTCFS.find.byId(request().username())));
    }
}
