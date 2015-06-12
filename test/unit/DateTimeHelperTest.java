package unit;

import helpers.DateTimeHelper;
import junit.framework.Assert;
import org.joda.time.DateTime;
import org.junit.Test;

import java.sql.Time;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Created by Alexander on 6/12/2015.
 */
public class DateTimeHelperTest {

    @Test
    public void testGetDateTimeInAgoFormat() throws Exception {
        assertEquals("now", DateTimeHelper.getDateTimeInAgoFormat(DateTime.now()));
        assertEquals("1 minute ago", DateTimeHelper.getDateTimeInAgoFormat(DateTime.now().minusMinutes(1)));
        assertEquals("3 minutes ago", DateTimeHelper.getDateTimeInAgoFormat(DateTime.now().minusMinutes(3)));
        assertEquals("about hour ago", DateTimeHelper.getDateTimeInAgoFormat(DateTime.now().minusHours(1)));
        assertEquals("6 hours ago", DateTimeHelper.getDateTimeInAgoFormat(DateTime.now().minusHours(6)));
        assertEquals("about day ago", DateTimeHelper.getDateTimeInAgoFormat(DateTime.now().minusDays(1)));
        assertEquals("4 days ago", DateTimeHelper.getDateTimeInAgoFormat(DateTime.now().minusDays(4)));
    }

    @Test
    public void testGetNow() throws Exception {
        DateTimeHelper helper = new DateTimeHelper();
        DateTime now = DateTime.now();
        DateTime nowFromHelper = helper.getNow();
        assertEquals(now.secondOfDay(), nowFromHelper.secondOfDay());
    }
}