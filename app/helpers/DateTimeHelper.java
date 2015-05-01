package helpers;


import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.util.Calendar;
import java.util.Date;

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
        else if(minutesAgo > 60){
            int hoursAgo = minutesAgo/60;
            if(hoursAgo > 24)
            {
                int daysAgo = hoursAgo /24;
                if (hoursAgo < 2)
                    return " about day ago";
                else
                    return daysAgo + " days ago";
            }
            if(hoursAgo < 2)
                return " about hour ago";
            else
                return hoursAgo + " hours ago";
        }
        else
            return minutesAgo + " minutes ago";
    }

    public static Date getEndOfDay(Date date) {
        return DateUtils.addMilliseconds(DateUtils.ceiling(date, Calendar.DATE), -1);
    }

    public static Date getStartOfDay(Date date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }
}
