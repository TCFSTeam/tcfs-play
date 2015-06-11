package finctional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import play.libs.F;
import play.test.TestBrowser;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

/**
 * Created by Alexander on 6/11/2015.
 */
public class AdminTest extends CommonFuncTest {

    @Test
    public void LoginLogoutTest() {
        running(testServer(port, fakeAppWithGlobal), HtmlUnitDriver.class, new F.Callback<TestBrowser>() {
            @Override
            public void invoke(TestBrowser browser) {
                loadInitialData();
                browser.goTo(baseUrl);
                login("joe@tcfs.com", "joe");
                Assert.assertTrue(isLogoutPresent());
                driver.findElement(By.xpath("//table[@id='contacts_table']/tbody/tr/td/a/i")).click();
                driver.findElement(By.id("table2")).click();
                driver.findElement(By.cssSelector("button.btn.btn-default")).click();
                driver.findElement(By.xpath("//div[@id='page-wrapper']/div/form/table/tbody/tr/td[5]/a/i")).click();
                driver.findElement(By.xpath("(//button[@type='button'])[8]")).click();
                driver.findElement(By.linkText("Liza")).click();
                driver.findElement(By.linkText("Show orders")).click();
                driver.findElement(By.xpath("//table[@id='contacts_table']/tbody/tr/td/a/i")).click();
                Assert.assertTrue(driver.findElements( By.id("returned_items") ).size() != 0);
            }
        });
    }

    @Test
    public void AdminReportTest() {
        running(testServer(port, fakeAppWithGlobal), HtmlUnitDriver.class, new F.Callback<TestBrowser>() {
            @Override
            public void invoke(TestBrowser browser) {
                loadInitialData();
                browser.goTo(baseUrl);
                login("joe@tcfs.com", "joe");
                Assert.assertTrue(isLogoutPresent());
                driver.findElement(By.linkText("Daily profit report")).click();
                Assert.assertTrue(driver.findElements(By.xpath("//table[@id='activeList']/tbody/tr")).size() > 0);
                Assert.assertTrue(driver.findElements( By.id("ordersByWaiter") ).size() != 0);
                Assert.assertTrue(driver.findElements( By.id("ordersByTable") ).size() != 0);

            }
        });
    }
}
