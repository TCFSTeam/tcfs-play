package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

/**
 * Created by alexander on 12/20/14.
 */

public class Order extends Model {

    @Constraints.Required
    public int table;

    @Constraints.Required
    public int Waiter;
    private long OrderNumber;
    private int OrderStatus;



}
