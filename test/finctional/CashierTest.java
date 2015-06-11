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
public class CashierTest extends CommonFuncTest {

    @Test
    public void ReportTest() {
        running(testServer(port, fakeAppWithGlobal), HtmlUnitDriver.class, new F.Callback<TestBrowser>() {
            @Override
            public void invoke(TestBrowser browser) {
                loadInitialData();
                browser.goTo(baseUrl);
                login("megan@tcfs.com", "megan");
                Assert.assertTrue(isLogoutPresent());
                driver.findElement(By.linkText("Daily profit report")).click();
                Assert.assertTrue(driver.findElements(By.xpath("//table[@id='activeList']/tbody/tr")).size() > 0);
                Assert.assertTrue(driver.findElements( By.id("ordersByWaiter") ).size() != 0);
                Assert.assertTrue(driver.findElements( By.id("ordersByTable") ).size() != 0);

            }
        });
    }

    @Test
    public void CloseOrdersTest() throws Exception {
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
