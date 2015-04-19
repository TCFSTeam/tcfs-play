package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by alexander on 12/27/14.
 */
@Entity
public class OrderItem extends Model{

    private static final long serialVersionUID = 1L;
    public static Model.Finder<String, OrderItem> find = new Model.Finder<String, OrderItem>(String.class, OrderItem.class);

    @Id
    @GeneratedValue
    public int id;
    public int menuItemId;
    public boolean isReady = false;
    public boolean isReturned = false;

    public OrderItem(int menuItemId, boolean isready) {
        this.id = findAll().size() + 1;
        this.isReady = isready;
        this.menuItemId = menuItemId;
    }

    /**
     * Setters
     */
    public void setReady(boolean ready) { this.isReady = ready; }
    public void setReturned(boolean returned) {
        this.isReturned = returned;
    }

    public static List<OrderItem> findAll() {
        return find.all();
    }
    public static OrderItem findById(int id) {
        return find.where().eq("id",id).findUnique();
    }

}
