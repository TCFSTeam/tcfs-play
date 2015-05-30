package models;

import com.avaje.ebean.annotation.ConcurrencyMode;
import com.avaje.ebean.annotation.EntityConcurrencyMode;
import org.joda.time.DateTime;
import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by Alexander on 4/12/2015.
 * TableTCFS entity managed by Ebean
 */
@Entity
@EntityConcurrencyMode(ConcurrencyMode.NONE)
public class Reservation extends Model {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private int id;
    private int tableId;
    private boolean isActive = true;
    private String reservator;
    @Formats.DateTime(pattern = "dd/MM/yyyy HH:mm")
    private DateTime createdAt = new DateTime();
    @Formats.DateTime(pattern = "dd/MM/yyyy HH:mm")
    private DateTime startAt = new DateTime();

    public Reservation(){
        this.setCreatedAt(DateTime.now().minusDays(1));
        this.setStartAt(DateTime.now().plusHours(4));
    }
    public Reservation(DateTime createdAt, DateTime startAt){
        this.setCreatedAt(createdAt);
        this.setStartAt(startAt);
    }
    public static Model.Finder<String, Reservation> find = new Model.Finder<String, Reservation>(String.class, Reservation.class);
    /**
     * Retrieve all reservations.
     */
    public static List<Reservation> findAll() {
        return find.all();
    }
    public static List<Reservation> findAllActive() {
        return find.where().eq("isActive", true).findList();
    }

    /**
     * Retrieve reservation by id.
     */
    public static Reservation findById(int id) {
        return find.where().eq("id", id).findUnique();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getReservator() {
        return reservator;
    }

    public void setReservator(String reservator) {
        this.reservator = reservator;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public DateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(DateTime startAt) {
        this.startAt = startAt;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getTableId() {
        return tableId;
    }
}
