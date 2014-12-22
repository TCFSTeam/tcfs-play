package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by alexander on 12/21/14.
 */
@Entity
public class OrderItem extends Model {
    private static final long serialVersionUID = 1L;
    public static Model.Finder<String, OrderItem> find = new Model.Finder<String, OrderItem>(String.class, OrderItem.class);
    @Id
    public int Id;
    public double itemPrice;
    public int itemsCount;
    public boolean isDeleted;
    public String itemDescription;

    /**
     * Retrieve available order items.
     */
    public static List<OrderItem> findAll() {
        return find.all();
    }

    public static double getCostForAll() {
        double cost = 0;
        for (OrderItem item : findAll()) {
            cost += (item.itemPrice * item.itemsCount);
        }
        return cost;
    }
}
