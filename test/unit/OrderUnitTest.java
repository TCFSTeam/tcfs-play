package unit;

import com.avaje.ebean.Ebean;
import models.MenuItem;
import models.OrderItem;
import models.OrderTCFS;
import models.TableTCFS;
import org.junit.*;
import play.libs.Yaml;
import play.test.Helpers;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class OrderUnitTest extends CommonUnitTest {

    @Test
    public void checkCost() {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                Map<String,List<Object>> all = (Map<String,List<Object>>) Yaml.load("initial-data.yml");
                if(MenuItem.findAll().isEmpty())
                    Ebean.save(all.get("menuitems"));
                if(OrderItem.findAll().isEmpty())
                    Ebean.save(all.get("orderitems"));
                if(OrderTCFS.findAll().isEmpty())
                    Ebean.save(all.get("orders"));
                if(TableTCFS.findAll().isEmpty())
                    Ebean.save(all.get("tables"));
                int orderId = 1;
                OrderTCFS order = OrderTCFS.findById(orderId);
                assertNotNull(order);
                assertEquals(order.id, orderId);
                double orderCostById = OrderTCFS.getOrderCost(order.id);
                double orderCost = OrderTCFS.getOrderCost(order);
                assertEquals(orderCost, orderCostById, 0.001);
                assertEquals(24, orderCost, 0.001);

            }
        });
    }
}