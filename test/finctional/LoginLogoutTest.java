package finctional;

/**
 * Created by Alexander on 6/7/2015.
 */

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import play.libs.F.Callback;
import play.test.TestBrowser;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

public class LoginLogoutTest extends CommonFuncTest {

    @Test
    public void LoginLogoutTest() {
        running(testServer(port, fakeAppWithGlobal), HtmlUnitDriver.class, new Callback<TestBrowser>() {
            @Override
            public void invoke(TestBrowser browser) {
                loadInitialData();

                browser.goTo(baseUrl);
                login("penny@tcfs.com", "penny");
                Assert.assertTrue(isLogoutPresent());
                driver.findElement(By.id("logout")).click();
                login("jack@tcfs.com", "jack");
                Assert.assertTrue(isLogoutPresent());
                driver.findElement(By.id("logout")).click();
                login("joe@tcfs.com", "joe");
                Assert.assertTrue(isLogoutPresent());
                driver.findElement(By.id("logout")).click();
                login("megan@tcfs.com", "megan");
                Assert.assertTrue(isLogoutPresent());
                driver.findElement(By.id("logout")).click();
                Assert.assertTrue(logoutIsNotPresent());
                driver.findElement(By.name("email")).clear();
                driver.findElement(By.name("email")).sendKeys("penny@tcfs.com");
                driver.findElement(By.name("password")).clear();
                driver.findElement(By.name("password")).sendKeys("pennyqwerty");
                driver.findElement(By.xpath("//input[@value='Login']")).click();
                Assert.assertTrue(isErrorPresent());
            }
        });
    }
}
