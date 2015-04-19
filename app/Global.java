/**
 * Created by test on 10/12/14.
 */

import com.avaje.ebean.Ebean;
import models.*;
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;

import java.util.List;
import java.util.Map;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        @SuppressWarnings("unchecked")
        Map<String,List<Object>> all = (Map<String,List<Object>>) Yaml.load("initial-data.yml");
        if(UserTCFS.findAll().isEmpty())
            Ebean.save(all.get("users"));
        if(MenuItem.findAll().isEmpty())
            Ebean.save(all.get("menuitems"));
        if(OrderItem.findAll().isEmpty())
            Ebean.save(all.get("orderitems"));
        if(OrderTCFS.findAll().isEmpty())
            Ebean.save(all.get("orders"));
        if(TableTCFS.findAll().isEmpty())
            Ebean.save(all.get("tables"));
        if(Reservation.findAll().isEmpty())
            Ebean.save(all.get("reservations"));
    }

}