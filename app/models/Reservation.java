package models;

import org.joda.time.DateTime;
import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Alexander on 4/12/2015.
 * Table entity managed by Ebean
 */
@Entity
public class Reservation extends Model {
    private static final long serialVersionUID = 1L;
    public static Model.Finder<String, Reservation> find = new Model.Finder<String, Reservation>(String.class, Reservation.class);
    @Id
    @GeneratedValue
    public int id;
    public int reservationId;
    @Formats.DateTime(pattern = "MMM ddd HH:mm")
    public DateTime createdAt = new DateTime();
    @Formats.DateTime(pattern = "MMM ddd HH:mm")
    public DateTime startAt = new DateTime();
}
