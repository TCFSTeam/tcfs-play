package helpers;


import org.joda.time.DateTime;
import org.joda.time.Minutes;

/**
 * Created by alexander on 10.01.2015.
 */
public class DateTimeHelper {

    public static String getDateTimeInAgoFormat(DateTime dateTime) {
        int minutesAgo = Minutes.minutesBetween(dateTime, DateTime.now()).getMinutes();
        if (minutesAgo <= 0)
            return "now";
        else if (minutesAgo == 1)
            return minutesAgo + " minute ago";
        else
            return minutesAgo + " minutes ago";
    }
}
