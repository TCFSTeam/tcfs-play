package models;

import com.avaje.ebean.Ebean;
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
    public int guestsCount = 1;
    public String Waiter;
    public String OrderStatus;
    public int Table;
    public boolean saved = false;
    @Formats.DateTime(pattern = "MMM ddd HH:mm")
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
            cost += (MenuItem.findById(item.menuItemId).itemPrice);
        }
        return cost;
        }
        else
            return 0;
    }

    public static long getActiveTime(int orderId){
        OrderTCFS orderTCFS = OrderTCFS.findById(orderId);
        return (now().toDate().getTime() - orderTCFS.createdAt.toDate().getTime()) / 60000;
    }

    public static double getReadinessStatus(int orderId) {
        double readyCount = 0;
        OrderTCFS orderTCFS = OrderTCFS.findById(orderId);
        double allItemsCount = orderTCFS.items.size();
        for (OrderItem item : orderTCFS.items) {
            if (item.isReady)
                readyCount++;
        }
        if (readyCount > 0 && allItemsCount > 0) {
            return (readyCount / allItemsCount) * 100;
        } else
            return 0;
    }

    /**
     * Retrieve order by waiter email and with active status.
     */
    public static List<OrderTCFS> findActiveByUser(User user) {
        return find.where().eq("Waiter", user.email).where().eq("OrderStatus", "Active").where().eq("saved", "true").orderBy("id").findList();
    }

    /**
     * Retrieve all active orders.
     */
    public static List<OrderTCFS> findAllActive() {
        return find.where().eq("OrderStatus", "Active").where().eq("saved", "true").orderBy("id").findList();
    }

    public static List<OrderTCFS> findAllActiveByTable(int table, String waiterId) {
        return find.where().eq("OrderStatus", "Active").where().eq("Table", table).where().eq("Waiter", waiterId).where().eq("saved", "true").orderBy("id").findList();
    }

    public static boolean removeNonSavedOrders() {
        List<OrderTCFS> ordersForDelete = find.where().eq("saved", "true").findList();
        for (OrderTCFS orderForDelete : ordersForDelete) {
            Ebean.delete(orderForDelete);
        }
        return true;
    }

    public void setTable(int table) {
        this.Table = table;
    }

    public void setSaved() {
        this.saved = true;
    }

    public void setNotSaved() {
        this.saved = false;
    }
}
