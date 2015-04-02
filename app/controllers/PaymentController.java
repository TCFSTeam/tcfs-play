package controllers;

import models.MenuItem;
import models.OrderTCFS;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by Alexander on 4/1/2015.
 */
public class PaymentController extends Controller {
    /**
     * Pay order
     */
    public static Result dailyProfit() {
        return ok(views.html.dailyProfit.render(User.find.byId(request().username())));
    }
}
