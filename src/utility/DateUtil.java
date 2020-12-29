package utility;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author vincent
 */
public class DateUtil {
    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm");


    public static String getNowTime(){
        return dateTimeFormat.format(new Date(System.currentTimeMillis()));
    }

    public static String dateLocalFormat(){
        return "";
    }
}
