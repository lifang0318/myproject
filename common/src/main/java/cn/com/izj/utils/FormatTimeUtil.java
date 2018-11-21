package cn.com.izj.utils;

import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Date;

/**
 * @author: 朱鸿平
 * @date: 2018/6/4 21:40
 */
public class FormatTimeUtil {
    private static final long ONE_HOUR = 3600000L;
    private static final String ONE_MINUTE_AGO = "分钟前";

    public static String format(Date date) {
        long delta = new Date().getTime() - date.getTime();
        int i = Period.between(DateTimeUtils.date2LocalDate(date), DateTimeUtils.date2LocalDate()).getDays();

        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdff = new SimpleDateFormat("yyyy");
        Integer s1 = Integer.parseInt(df.format(date));
        Integer s2 = Integer.parseInt(sdff.format(new Date()));
        if (s1 < s2) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
            sdf.format(date);
            return sdf.format(date);
        }
        if (delta <= ONE_HOUR) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta > 24L * ONE_HOUR || i == 1) {
            SimpleDateFormat sdf = new SimpleDateFormat("M-dd HH:mm");
            sdf.format(date);
            return sdf.format(date);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.format(date);
        return sdf.format(date);

    }

    /**
     * 获取当前时间戳（秒）固定10位
     * @return
     */
    public static String currentTimeSeconds() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }
}
