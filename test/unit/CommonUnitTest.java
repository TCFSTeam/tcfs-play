package unit;

import com.avaje.ebean.Ebean;
import helpers.DateTimeHelper;
import models.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import play.libs.Yaml;
import play.test.FakeApplication;
import play.test.Helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexander on 5/31/2015.
 */

public class CommonUnitTest {
    FakeApplication fakeAppWithGlobal;
    @Before
    public void onStart() {
        System.out.println("Starting FakeApplication");
        Map<String, String> settings = new HashMap();
        settings.put("db.default.driver", "org.h2.Driver");
        settings.put("db.default.url", "jdbc:h2:mem:play-test;MODE=MySQL");
        settings.put("ebean.default", "models.*");
        settings.put("application.secret","QCY?tAnf");
        fakeAppWithGlobal = Helpers.fakeApplication(settings);
    }

    public void loadAllData(){
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
        List<Reservation> reservations = Reservation.findAll();
        for (Reservation reservation: reservations){
            reservation.setStartAt(DateTimeHelper.getRandomDateTime());
            Ebean.save(reservation);
        }
    }
}
