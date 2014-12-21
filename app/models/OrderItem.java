package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by alexander on 12/21/14.
 */
@Entity
public class OrderItem extends Model {
    @Id
    private int Id;
    private double itemPrice;
    private String itemName;
    private String itemDescription;
}
