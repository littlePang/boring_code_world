package serivce;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by jaky on 11/30/16.
 */
public class DateUtilTest {

    public static void main(String[] args) throws ParseException {

        Date date = DateUtils.parseDate("2016-11-27 07:30:03", "yyyy-MM-dd HH:mm:ss");
        Date date1 = DateUtils.addHours(new Date(), -336);
        Date date2 = DateUtils.addMinutes(new Date(), -6);

        System.out.println(DateFormatUtils.format(date1, "yyyy-MM-dd HH:mm:ss"));
        System.out.println(DateFormatUtils.format(date2, "yyyy-MM-dd HH:mm:ss"));

    }

}
