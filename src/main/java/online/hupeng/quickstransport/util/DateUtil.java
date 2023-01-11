package online.hupeng.quickstransport.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {

    /**
     * 获取下一天零点的时间
     * @param millions 指定时间的时间戳
     */
    public static Date getNextDay(long millions) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(millions));
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
