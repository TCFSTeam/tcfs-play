package finctional;

import com.avaje.ebean.Ebean;
import helpers.DateTimeHelper;
import models.*;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import play.libs.Yaml;
import play.test.FakeApplication;
import play.test.Helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static com.thoughtworks.selenium.SeleneseTestBase.fail;

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
    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
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

    public boolean login(String user, String password){
        driver.get(baseUrl + "home");
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys(user);
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.xpath("//input[@value='Login']")).click();
        return driver.findElement(By.id("logout")).getText().contains("Logout");
    }

    protected boolean isLogoutPresent() {
        try {
            Assert.assertEquals("Logout", driver.findElement(By.id("logout")).getText());
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected boolean isErrorPresent() {
        try {
            Assert.assertEquals("ValidationError(,Invalid username or password,[])", driver.findElement(By.className("error")).getText());
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected boolean logoutIsNotPresent() {
        try {
            Assert.assertEquals("Please sign in", driver.findElement(By.id("signin")).getText());
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
