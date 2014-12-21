package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by alexander on 12/20/14.
 */
@Entity
public class OrderIt extends Model {
    private static final long serialVersionUID = 1L;
    public static Model.Finder<String, OrderIt> find = new Model.Finder<String, OrderIt>(String.class, OrderIt.class);
    @Id
    public int id;
    public String Waiter;
    public String OrderStatus;
    public int Table;

    /**
     * Retrieve all orders.
     */
    public static List<OrderIt> findAll() {
        return find.all();
    }

    /**
     * Retrieve order by waiter email and with active status.
     */
    public static List<OrderIt> findActiveByUser(User user) {
        return find.where().eq("Waiter", user.email).where().eq("OrderStatus", "Active").findList();
    }
}
