package unit;

import com.avaje.ebean.Ebean;
import models.MenuItem;
import models.OrderItem;
import models.OrderTCFS;
import org.junit.Test;
import play.test.Helpers;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Alexander on 6/12/2015.
 */
public class OrderItemTest extends CommonUnitTest {
    @Test
    public void testOrderItem() {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                loadAllData();
                OrderTCFS order = new OrderTCFS();
                List<MenuItem> menuItems = MenuItem.findAll();
                for(MenuItem menuItem: menuItems) {
                    OrderItem orderItem = new OrderItem(menuItem.id, false);
                    if (menuItem.itemPrice > 20)
                        orderItem.setReady(true);
                    Ebean.save(orderItem);
                    order.items.add(orderItem);
                }
                Ebean.save(order);
                order = OrderTCFS.findById(order.id);
                assertEquals(order.items.size(), menuItems.size());
            }
        });
    }
}
