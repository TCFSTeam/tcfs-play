package models;

import org.joda.time.DateTime;
import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by Alexander on 4/12/2015.
 * Table entity managed by Ebean
 */
@Entity
public class Reservation extends Model {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    public int id;
    public boolean isActive;
    public String reservator;
    @Formats.DateTime(pattern = "MMM ddd HH:mm")
    public DateTime createdAt = new DateTime();
    @Formats.DateTime(pattern = "MMM ddd HH:mm")
    public DateTime startAt = new DateTime();

    public Reservation(){
        this.createdAt = DateTime.now().minusDays(1);
        this.startAt = DateTime.now().plusHours(4);
    }
    public Reservation(DateTime createdAt, DateTime startAt){
        this.createdAt = createdAt;
        this.startAt = startAt;
    }
    public static Model.Finder<String, Reservation> find = new Model.Finder<String, Reservation>(String.class, Reservation.class);
    /**
     * Retrieve all tables.
     */
    public static List<Reservation> findAll() {
        return find.all();
    }
}
