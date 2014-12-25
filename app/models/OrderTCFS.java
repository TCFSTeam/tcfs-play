package models;

import org.joda.time.DateTime;
import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexander on 12/20/14.
 */
@Entity
public class OrderTCFS extends Model {
    private static final long serialVersionUID = 1L;
    public static Model.Finder<String, OrderTCFS> find = new Model.Finder<String, OrderTCFS>(String.class, OrderTCFS.class);
    @Id
    public int id;
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
    /**
     * Retrieve order by waiter email and with active status.
     */
    public static List<OrderTCFS> findActiveByUser(User user) {
        return find.where().eq("Waiter", user.email).where().eq("OrderStatus", "Active").findList();
    }
}
