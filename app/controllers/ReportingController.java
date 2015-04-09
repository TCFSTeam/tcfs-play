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
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Alexander on 4/5/2015.
 */
@Security.Authenticated(SecuredController.class)
public class ReportingController extends Controller {
    /**
     * Get statistic for each waiter
     */
    public static Result getOrdersStatistics() {
        ObjectNode result = Json.newObject();
        Map<String, Integer> map = OrderTCFS.getOrdersByWaiterForADay();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            result.put((String)pair.getKey(), (Integer)pair.getValue());
        }
        return ok(result);
    }

    /**
     * Get statistic for each table
     */
    public static Result getTablesStatistics() {
        ObjectNode result = Json.newObject();
        Map<String, Integer> map = OrderTCFS.getOrdersByTableForADay();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            result.put((String)pair.getKey(), (Integer)pair.getValue());
        }
        return ok(result);
    }
}
