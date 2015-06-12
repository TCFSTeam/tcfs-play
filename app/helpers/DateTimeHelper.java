package helpers;


import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by alexander on 10.01.2015.
 */
public class DateTimeHelper {

    /**
     * All Dates are normalized to UTC, it is up the client code to convert to the appropriate TimeZone.
     */
    public static final TimeZone UTC;
    /**
     * @see <a href="http://en.wikipedia.org/wiki/ISO_8601#Combined_date_and_time_representations">Combined Date and Time Representations</a>
     */
    public static final String ISO_8601_24H_FULL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    /**
     * 0001-01-01T00:00:00.000Z
     */
    public static final Date BEGINNING_OF_TIME;

    /**
     * 292278994-08-17T07:12:55.807Z
     */
    public static final Date END_OF_TIME;

    static
    {
        UTC = TimeZone.getTimeZone("UTC");
        TimeZone.setDefault(UTC);
        final Calendar c = new GregorianCalendar(UTC);
        c.set(1, 0, 1, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        BEGINNING_OF_TIME = c.getTime();
        c.setTime(new Date(Long.MAX_VALUE));
        END_OF_TIME = c.getTime();
    }
    public static String getDateTimeInAgoFormat(DateTime dateTime) {
        int minutesAgo = Minutes.minutesBetween(dateTime, DateTime.now()).getMinutes();
        if (minutesAgo <= 0)
            return "now";
        else if (minutesAgo == 1)
            return minutesAgo + " minute ago";
        else if (minutesAgo >= 60) {
            int hoursAgo = minutesAgo / 60;
            if (hoursAgo >= 24) {
                int daysAgo = hoursAgo / 24;
                if (daysAgo < 2)
                    return "about day ago";
                else
                    return daysAgo + " days ago";
            }
            if (hoursAgo < 2)
                return "about hour ago";
            else
                return hoursAgo + " hours ago";
        } else
            return minutesAgo + " minutes ago";
    }

    public static DateTime getNow() {
        return DateTime.now();
    }

    public static DateTime parseDateString(String datetimestring) throws ParseException {
        Date date = null;
        final SimpleDateFormat sdf = new SimpleDateFormat(ISO_8601_24H_FULL_FORMAT);
        sdf.setTimeZone(UTC);
        date = sdf.parse(datetimestring);
        return new DateTime(date);
    }
    public static String getDateString(DateTime datetime){
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
        return formatter.print(datetime);
    }
    public static DateTime getRandomDateTime() {
        long rangebegin = Timestamp.valueOf("2015-05-28 00:00:00").getTime();
        long rangeend = Timestamp.valueOf("2015-01-01 00:58:00").getTime();
        long diff = rangeend - rangebegin + 1;
        Timestamp rand = new Timestamp(rangebegin + (long)(Math.random() * diff));
        return new DateTime(rand);
    }
}
