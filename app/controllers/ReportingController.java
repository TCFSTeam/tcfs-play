package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.MenuItem;
import models.OrderTCFS;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander on 4/5/2015.
 */
@Security.Authenticated(SecuredController.class)
public class ReportingController extends Controller {
    /**
     * Place new order
     */
    public static Result getOrdersStatistics() {
        ObjectNode result = Json.newObject();
        result.put("Penny", 10);
        result.put("Liza", 4);
        result.put("Megan", 5);
        result.put("Joe", 9);

        return ok(result);
    }
}
