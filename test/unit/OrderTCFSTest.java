package unit;

import com.avaje.ebean.Ebean;
import models.*;
import org.junit.*;
import play.libs.Yaml;
import play.test.Helpers;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class OrderTCFSTest extends CommonUnitTest {

    @Test
    public void testGetOrdersCost() {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                loadAllData();
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

    @Test
    public void testSetTable() throws Exception {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                loadAllData();
                int orderId = 1;
                OrderTCFS order = OrderTCFS.findById(orderId);
                assertNotNull(order);
                assertEquals(order.id, orderId);
                int table = order.TableId;
                order.setTable(orderId, table + 1);
                OrderTCFS newOrder = OrderTCFS.findById(orderId);
                Assert.assertEquals(order.TableId + 1, newOrder.TableId);
            }
        });
    }

    @Test
    public void testSetGuests() throws Exception {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                loadAllData();
                int orderId = 1;
                OrderTCFS order = OrderTCFS.findById(orderId);
                assertNotNull(order);
                assertEquals(order.id, orderId);
                int guests = order.guestsCount;
                order.setGuests(orderId, guests + 1);
                OrderTCFS newOrder = OrderTCFS.findById(orderId);
                Assert.assertEquals(order.guestsCount + 1, newOrder.guestsCount);

            }
        });
    }

    @Test
    public void testSetStatus() throws Exception {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                loadAllData();
                int orderId = 1;
                OrderTCFS order = OrderTCFS.findById(orderId);
                assertNotNull(order);
                assertEquals(order.id, orderId);
                String status = order.OrderStatus;
                Assert.assertTrue(status.length() > 0);
                order.setStatus(orderId, "Active");
                Ebean.save(order);
                OrderTCFS newOrder = OrderTCFS.findById(orderId);
                Assert.assertEquals("Active", newOrder.OrderStatus);
            }
        });
    }

    @Test
    public void testSetWaiter() throws Exception {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                loadAllData();
                int orderId = 1;
                String waiter = "liza@tcfs.com";
                OrderTCFS order = OrderTCFS.findById(orderId);
                assertNotNull(order);
                assertEquals(order.id, orderId);
                order.setWaiter(orderId, waiter);
                OrderTCFS newOrder = OrderTCFS.findById(orderId);
                Assert.assertEquals(waiter, newOrder.Waiter);
            }
        });
    }

    @Test
    public void testSetSaved() throws Exception {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                loadAllData();
                OrderTCFS order = new OrderTCFS();
                Ebean.save(order);
                assertNotNull(order);
                order = OrderTCFS.findById(order.id);
                Assert.assertTrue(order.id > 0);
                order.setSaved();
                Ebean.save(order);
                OrderTCFS newOrder = OrderTCFS.findById(order.id);
                Assert.assertTrue(newOrder.saved);
            }
        });
    }

    @Test
    public void testSetNotSaved() throws Exception {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                loadAllData();
                OrderTCFS order = new OrderTCFS();
                Ebean.save(order);
                assertNotNull(order);
                order = OrderTCFS.findById(order.id);
                Assert.assertTrue(order.id > 0);
                order.setNotSaved();
                Ebean.save(order);
                OrderTCFS newOrder = OrderTCFS.findById(order.id);
                Assert.assertTrue(!newOrder.saved);
            }
        });
    }

    @Test
    public void testGetOrdersByWaiterForADay() throws Exception {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                loadAllData();
                Map<String, Integer> OrdersByWaiterForADay = OrderTCFS.getOrdersByWaiterForADay();
                Assert.assertTrue(OrdersByWaiterForADay.size() > 0);
                Iterator it = OrdersByWaiterForADay.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    if(pair.getKey() == "penny@tcfs.com")
                        Assert.assertEquals(2, pair.getValue());
                    if(pair.getKey() == "liza@tcfs.com")
                        Assert.assertEquals(1, pair.getValue());
                }
            }
        });
    }

    @Test
    public void testGetOrdersByTableForADay() throws Exception {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                loadAllData();
                Map<String, Integer> OrdersByTableForADay = OrderTCFS.getOrdersByTableForADay();
                Assert.assertTrue(OrdersByTableForADay.size() > 0);
                Iterator it = OrdersByTableForADay.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    Assert.assertEquals(1, pair.getValue());
                }
            }
        });
    }

    @Test
    public void testGetReadinessStatus() throws Exception {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                int orderId = 1;
                Assert.assertEquals(50, OrderTCFS.getReadinessStatus(orderId), 0.5);
            }
        });
    }

    @Test
    public void testFindActiveByUser() throws Exception {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                loadAllData();
                String waiter = "liza@tcfs.com";
                UserTCFS user = UserTCFS.findByEmail(waiter);
                List<OrderTCFS> orders = OrderTCFS.findActiveByUser(user);
                assertNotNull(orders);
                Assert.assertEquals(1, orders.size());
            }
        });
    }

    @Test
    public void testFindAllActive() throws Exception {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                loadAllData();
                List<OrderTCFS> orders = OrderTCFS.findAllActive();
                assertNotNull(orders);
                Assert.assertEquals(3, orders.size());
            }
        });
    }

    @Test
    public void testFindAllCompleted() throws Exception {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                loadAllData();
                List<OrderTCFS> orders = OrderTCFS.findAllCompleted();
                assertNotNull(orders);
                Assert.assertEquals(3, orders.size());
            }
        });
    }

    @Test
    public void testFindAllCompletedToday() throws Exception {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                loadAllData();
                List<OrderTCFS> orders = OrderTCFS.findAllCompletedToday();
                assertNotNull(orders);
                Assert.assertEquals(3, orders.size());
            }
        });
    }

    @Test
    public void testFindAllActiveByTable() throws Exception {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                loadAllData();
                int tableId = 6;
                String waiter = "penny@tcfs.com";
                String waiter2 = "liza@tcfs.com";
                List<OrderTCFS> orders = OrderTCFS.findAllActiveByTable(tableId, waiter);
                assertNotNull(orders);
                Assert.assertEquals(1, orders.size());
                List<OrderTCFS> orders2 = OrderTCFS.findAllActiveByTable(tableId, waiter2);
                assertNotNull(orders2);
                Assert.assertEquals(0, orders2.size());
            }
        });
    }

    @Test
    public void testGetOrderCost() throws Exception {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                int orderId = 2;
                int orderCost = 0;
                OrderTCFS order = OrderTCFS.findById(orderId);
                for (OrderItem orderItem: order.items){
                    if(!orderItem.isReturned)
                        orderCost += MenuItem.findById(orderItem.menuItemId).itemPrice;
                }
                Assert.assertEquals(orderCost, OrderTCFS.getOrderCost(orderId), 0.01);
            }
        });
    }

    @Test
    public void testFindTodaysCompletedAll() throws Exception {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                loadAllData();
                List<OrderTCFS> orders = OrderTCFS.findTodaysCompletedAll();
                assertNotNull(orders);
                Assert.assertEquals(3, orders.size());
            }
        });
    }

    @Test
    public void testStatusChanging() throws Exception {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                int orderId = 2;
                OrderTCFS order = OrderTCFS.findById(orderId);
                for (OrderItem orderItem: order.items){
                    Assert.assertTrue(OrderTCFS.setReadinessStatus(order.id, orderItem.id));
                }
                OrderTCFS newOrder = OrderTCFS.findById(order.id);
                Assert.assertEquals("Ready", newOrder.OrderStatus);
                OrderTCFS.proceedToPay(orderId);
                newOrder = OrderTCFS.findById(orderId);
                assertEquals("WaitForPay", newOrder.OrderStatus);
                OrderTCFS.pay(orderId);
                newOrder = OrderTCFS.findById(orderId);
                assertEquals("Complete", newOrder.OrderStatus);
            }
        });
    }

    @Test
    public void testReturnItems() throws Exception {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                int orderId = 5;
                int itemsReturned = 0;
                OrderTCFS order = OrderTCFS.findById(orderId);
                assertNotNull(order);
                for (OrderItem orderItem: order.items){
                    OrderTCFS.returnItem(orderItem.id);
                }
                order = OrderTCFS.findById(orderId);
                for (OrderItem orderItem: order.items){
                    if(orderItem.isReturned)
                        itemsReturned++;
                }
                List<OrderItem> orderItems = OrderTCFS.getAllReturnedItems(orderId);
                assertNotNull(orderItems);
                assertEquals(itemsReturned, orderItems.size());
                boolean haveReturned = OrderTCFS.haveReturnedItems(orderId);
                assertTrue(haveReturned);
            }
        });
    }

    @Test
    public void testGetActiveTime() throws Exception {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                loadAllData();
                List<OrderTCFS> orders = OrderTCFS.findAllActive();
                double activeTime = OrderTCFS.getActiveTime(orders.get(1).id);
                assertTrue(activeTime < 1);
            }
        });
    }

    @Test
    public void testGetCompletedOrdersCost() throws Exception {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                loadAllData();
                double dayCost = OrderTCFS.getCompletedOrdersCost(true);
                double allCost = OrderTCFS.getCompletedOrdersCost(false);
                assertEquals(54, dayCost, 0.001);
                assertEquals(54, allCost, 0.001);
            }
        });
    }
}