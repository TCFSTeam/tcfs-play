package unit;

import helpers.NumbersHelper;
import junit.framework.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Alexander on 6/12/2015.
 */
public class NumbersHelperTest extends CommonUnitTest{

    @Test
    public void testGetReadinessString() throws Exception {
        double ready = 75.98;
        String readyString = NumbersHelper.getReadinessString(ready);
        Assert.assertEquals("76", readyString);

        double ready1 = 12.44;
        String readyString1 = NumbersHelper.getReadinessString(ready1);
        Assert.assertEquals("12", readyString1);

        double ready2 = 5;
        String readyString2 = NumbersHelper.getReadinessString(ready2);
        Assert.assertEquals("5", readyString2);
    }
}