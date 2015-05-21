package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 4/12/2015.
 * TableTCFS entity managed by Ebean
 * Using - usual represented pre-defined table set
 */
@Entity
public class TableTCFS extends Model {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    public int id;
    public boolean isActive;
    @ManyToMany
    public List<Reservation> reservations = new ArrayList<Reservation>();

    public static Model.Finder<String, TableTCFS> find = new Model.Finder<String, TableTCFS>(String.class, TableTCFS.class);
    /**
     * Retrieve all tables.
     */
    public static List<TableTCFS> findAll() {
        return find.all();
    }
    public static TableTCFS findById(int id) {
        return find.where().eq("id", id).findUnique();
    }

    public static int findAllCount() {
        return find.all().size();
    }

}
