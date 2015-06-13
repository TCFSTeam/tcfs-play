package finctional;

import org.hamcrest.CoreMatchers;

/**
 * Created by Alexander on 6/10/2015.
 */
import org.junit.*;
import static org.junit.Assert.*;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import play.libs.F;
import play.test.TestBrowser;

public class WaiterTest extends CommonFuncTest{

    @Test
    public void PlaceTest() throws Exception {
        running(testServer(port, fakeAppWithGlobal), HtmlUnitDriver.class, new F.Callback<TestBrowser>() {
            @Override
            public void invoke(TestBrowser browser) {
                loadInitialData();
                browser.goTo(baseUrl);
                Assert.assertTrue(login("penny@tcfs.com", "penny"));
                driver.findElement(By.linkText("Place order")).click();
                driver.findElement(By.id("table5")).click();
                driver.findElement(By.id("guests4")).click();
                driver.findElement(By.cssSelector("button.btn.btn-default")).click();
                driver.findElement(By.cssSelector("button.btn.btn-default")).click();
                driver.findElement(By.linkText("Active orders")).click();
                driver.findElement(By.xpath("//table[@id='contacts_table']/tbody/tr[4]/td/a/i")).click();
                isLogoutPresent();
            }
        });
    }

    @Test
    public void PayTest() throws Exception {
        running(testServer(port, fakeAppWithGlobal), HtmlUnitDriver.class, new F.Callback<TestBrowser>() {
            @Override
            public void invoke(TestBrowser browser) {
                loadInitialData();
                browser.goTo(baseUrl);
                Assert.assertTrue(login("penny@tcfs.com", "penny"));
                isLogoutPresent();
                driver.findElement(By.xpath("//table[@id='contacts_table']/tbody/tr/td[2]/a/i")).click();
                driver.findElement(By.xpath("//table[@id='contacts_table']/tbody/tr/td[2]/a/i")).click();
                driver.findElement(By.xpath("//table[@id='contacts_table']/tbody/tr/td[2]/a/i")).click();
                int rows = driver.findElements(By.xpath("//table[@id='contacts_table']/tbody/tr")).size();
                Assert.assertTrue( rows == 1);
                Assert.assertEquals("No matching records found",  driver.findElement(By.cssSelector(".dataTables_empty")).getText());
            }
        });
    }
}
