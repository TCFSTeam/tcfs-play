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

public class WaiterPlaceOrderTest extends CommonFuncTest{

    @Test
    public void testDf() throws Exception {
        running(testServer(port, fakeAppWithGlobal), HtmlUnitDriver.class, new F.Callback<TestBrowser>() {
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
                driver.findElement(By.linkText("Place order")).click();
                driver.findElement(By.id("table5")).click();
                driver.findElement(By.id("guests4")).click();
                driver.findElement(By.cssSelector("button.btn.btn-default")).click();
                driver.findElement(By.cssSelector("button.btn.btn-default")).click();
                driver.findElement(By.linkText("Active orders")).click();
                driver.findElement(By.xpath("//table[@id='contacts_table']/tbody/tr[4]/td/a/i")).click();
                isElementPresent();
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
           String amount = driver.findElement(By.id("order_amount")).getText();
            Assert.assertThat(amount, CoreMatchers.containsString("20"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
