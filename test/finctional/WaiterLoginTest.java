package finctional;

/**
 * Created by Alexander on 6/7/2015.
 */

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import play.libs.F.Callback;
import play.test.TestBrowser;
import java.util.NoSuchElementException;
import static com.thoughtworks.selenium.SeleneseTestBase.fail;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

public class WaiterLoginTest extends CommonFuncTest {

    @Test
    public void LoginTest() {
        running(testServer(port, fakeAppWithGlobal), HtmlUnitDriver.class, new Callback<TestBrowser>() {
            @Override
            public void invoke(TestBrowser browser) {
                browser.goTo(baseUrl);
                loadInitialData();

                driver.get(baseUrl + "home");
                driver.findElement(By.name("email")).clear();
                driver.findElement(By.name("email")).sendKeys("penny@tcfs.com");
                driver.findElement(By.name("password")).clear();
                driver.findElement(By.name("password")).sendKeys("penny");
                driver.findElement(By.xpath("//input[@value='Login']")).click();
                isElementPresent();

                driver.findElement(By.id("logout")).click();
                driver.findElement(By.name("email")).clear();
                driver.findElement(By.name("email")).sendKeys("penny@tcfs.com");
                driver.findElement(By.name("password")).clear();
                driver.findElement(By.name("password")).sendKeys("pennyqwerty");
                driver.findElement(By.xpath("//input[@value='Login']")).click();
                isErrorPresent();
            }
        });
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent() {
        try {
            Assert.assertEquals("Logout", driver.findElement(By.id("logout")).getText());
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isErrorPresent() {
        try {
            Assert.assertEquals("ValidationError(,Invalid username or password,[])", driver.findElement(By.className("error")).getText());
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
