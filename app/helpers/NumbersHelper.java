package helpers;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by malexander on 10.01.2015.
 */
public class NumbersHelper {
    public static String getReadinessString(double readinessProcent) {
        NumberFormat formatter = new DecimalFormat("#0");
        return formatter.format(readinessProcent);
    }
}
