package finctional;

import com.avaje.ebean.Ebean;
import helpers.DateTimeHelper;
import models.*;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import play.libs.Yaml;
import play.test.FakeApplication;
import play.test.Helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alexander on 6/7/2015.
 */
public class CommonFuncTest {

    public WebDriver driver;
    public String baseUrl;
    public boolean acceptNextAlert = true;
    public StringBuffer verificationErrors = new StringBuffer();
    public int port = 9000;
    FakeApplication fakeAppWithGlobal;
    @Before
    public void setUp() throws Exception {
        System.out.println("Starting FakeApplication");
        Map<String, String> settings = new HashMap();
        settings.put("db.default.driver", "org.h2.Driver");
        settings.put("db.default.url", "jdbc:h2:mem:play-test;MODE=MySQL");
        settings.put("ebean.default", "models.*");
        settings.put("application.secret","QCY?tAnf");
        fakeAppWithGlobal = Helpers.fakeApplication(settings);
        System.out.println("FakeApplication started");
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:" + port +"/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public void loadInitialData(){
        System.out.println("Loading initial data");
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
