package finctional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import play.libs.F;
import play.test.TestBrowser;

import java.util.NoSuchElementException;

import static com.thoughtworks.selenium.SeleneseTestBase.fail;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

/**
 * Created by Alexander on 6/11/2015.
 */
public class CookReadinessTest extends CommonFuncTest{
    @Test
    public void ChangeReadinessTest() {
        running(testServer(port, fakeAppWithGlobal), HtmlUnitDriver.class, new F.Callback<TestBrowser>() {
            @Override
            public void invoke(TestBrowser browser) {
                loadInitialData();
                browser.goTo(baseUrl);
                login("jack@tcfs.com", "jack");
                Assert.assertTrue(isLogoutPresent());
                int rowCount=driver.findElements(By.xpath("//table[@id='contacts_table']/tbody/tr")).size();
                driver.findElement(By.xpath("//table[@id='contacts_table']/tbody/tr[2]/td/a/i")).click();
                driver.findElement(By.id("chkReady4")).click();
                driver.findElement(By.linkText("Active orders")).click();
                Assert.assertEquals(rowCount -1,driver.findElements(By.xpath("//table[@id='contacts_table']/tbody/tr")).size());
            }
        });
    }
}
