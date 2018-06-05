package metis.winwin.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Laptop on 10/11/2017.
 */
public class DateTool {

    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public static String changeFormat(String waktu, String formatAwal, String formatAkhir){

        SimpleDateFormat fromdate = new SimpleDateFormat(formatAwal);
        SimpleDateFormat todate = new SimpleDateFormat(formatAkhir);

        String reformatdate = "";

        try {
            reformatdate = todate.format(fromdate.parse(waktu));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return reformatdate;

    }
}
