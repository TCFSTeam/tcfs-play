package models;

import com.avaje.ebean.annotation.ConcurrencyMode;
import com.avaje.ebean.annotation.EntityConcurrencyMode;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by alexander on 12/21/14.
 */
@Entity
@EntityConcurrencyMode(ConcurrencyMode.NONE)
public class MenuItem extends Model {
    private static final long serialVersionUID = 1L;
    public static Model.Finder<String, MenuItem> find = new Model.Finder<String, MenuItem>(String.class, MenuItem.class);
    @Id
    public int id;
    public double itemPrice;
    public boolean isDeleted;
    public String itemDescription;

    public static List<MenuItem> findAll() {
        return find.all();
    }

    public static MenuItem findById(int id) {
        return find.where().eq("id",id).findUnique();
    }

}
