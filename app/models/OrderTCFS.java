package models;

import org.joda.time.DateTime;
import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

import static org.joda.time.DateTime.now;

/**
 * Created by alexander on 12/20/14.
 */
@Entity
public class OrderTCFS extends Model {
    private static final long serialVersionUID = 1L;
    public static Model.Finder<String, OrderTCFS> find = new Model.Finder<String, OrderTCFS>(String.class, OrderTCFS.class);
    @Id
    public int id;
    public int guestsCount;
    public String Waiter;
    public String OrderStatus;
    public int Table;
    @Formats.DateTime(pattern="MMM ddd d HH:mm yyyy")
    public DateTime createdAt = new DateTime();
    @ManyToMany
    public List<OrderItem> items = new ArrayList<OrderItem>();

    /**
     * Retrieve all orders.
     */
    public static List<OrderTCFS> findAll() {
        return find.all();
    }
    public static OrderTCFS findById(int id) {
        return find.where().eq("id", id).findUnique();
    }

    public static double getOrderCost(int id){
        double cost = 0;
        OrderTCFS orderTCFS = OrderTCFS.findById(id);
        if(orderTCFS != null){
        for (OrderItem item : orderTCFS.items) {
            cost += (item.itemPrice);
        }
        return cost;
        }
        else
            return 0;
    }

    public static long getActiveTime(int orderId){
        OrderTCFS orderTCFS = OrderTCFS.findById(orderId);
        return (now().toDate().getTime()-orderTCFS.createdAt.minusMinutes(25).toDate().getTime())/60000;
    }

    public static float getReadinessStatus(int orderId) {
        int readyCount = 0;

        OrderTCFS orderTCFS = OrderTCFS.findById(orderId);
        int allItemsCount = orderTCFS.items.size();
        for (OrderItem item : orderTCFS.items) {
            if (item.isReady)
                readyCount++;
        }
        if (readyCount > 0 && allItemsCount > 0) {
            return (readyCount / allItemsCount) * 100;
        } else
            return -1;
    }
    /**
     * Retrieve order by waiter email and with active status.
     */
    public static List<OrderTCFS> findActiveByUser(User user) {
        return find.where().eq("Waiter", user.email).where().eq("OrderStatus", "Active").findList();
    }

    /**
     * Retrieve all active orders.
     */
    public static List<OrderTCFS> findAllActive() {
        return find.where().eq("OrderStatus", "Active").findList();
    }
}
