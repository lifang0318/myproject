package cn.com.izj.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: 朱鸿平
 * @date: 2018/6/4 21:40
 */
public class DateTimeUtils {
    private static final int TYPE_YEAR = 1;// 类型年
    private static final int TYPE_MONTH = 2;// 类型月
    private static final int TYPE_DAY = 3;// 类型日
    private static final int TYPE_HOUR = 4;// 类型时
    private static final int TYPE_MINUTE = 5;// 类型分
    private static final int TYPE_SECOND = 6;// 类型秒

    /**
     * 将时间字符串转换为Date类型
     *
     * @param dateStr 时间字符串
     * @return Date
     */
    public static Date toDate(String dateStr) {
        Date date = null;
        SimpleDateFormat formater = new SimpleDateFormat();
        formater.applyPattern("yyyy-MM-dd");
        try {
            date = formater.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将Date类型的时间转换为LocalDateTime
     *
     * @param date 时间
     * @return LocalDateTime
     */
    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).withNano(0);
    }

    /**
     * 将Date类型的当前时间转换为LocalDateTime
     *
     * @return LocalDateTime
     */
    public static LocalDateTime convertDateToLocalDateTime() {
        return LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()).withNano(0);
    }

    /**
     * 将Date类型的时间转换为LocalTime
     *
     * @param date 时间
     * @return LocalTime
     */
    public static LocalTime convertDateToLocalTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalTime().withNano(0);
    }

    /**
     * 将Date类型的当前时间转换为LocalTime
     *
     * @return LocalTime
     */
    public static LocalTime convertDateToLocalTime() {
        return LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()).toLocalTime().withNano(0);
    }

    /**
     * 将Date类型的时间转换为LocalDate
     *
     * @param date 时间
     * @return LocalDate
     */
    public static LocalDate convertDateToLocalDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 将Date类型的当前时间转换为LocalDate
     *
     * @return LocalDate
     */
    public static LocalDate convertDateToLocalDate() {
        return LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 将LocalDateTime类型的时间转换为Date
     *
     * @param localDateTime
     * @return Date
     */
    public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将LocalDateTime类型的当前时间转换为Date
     *
     * @return Date
     */
    public static Date convertLocalDateTimeToDate() {
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将LocalDate类型的时间转换为Date
     *
     * @param localDate
     * @return Date
     */
    public static Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将LocalDate类型的当前时间转换为Date
     *
     * @return Date
     */
    public static Date convertLocalDateToDate() {
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将LocalTime类型的时间转换为Date
     *
     * @param localTime
     * @return Date
     */
    public static Date convertLocalTimeToDate(LocalTime localTime) {
        return Date.from(LocalDateTime.of(LocalDate.now(), localTime).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将LocalTime类型的当前时间转换为Date
     *
     * @return Date
     */
    public static Date convertLocalTimeToDate() {
        return Date.from(LocalDateTime.of(LocalDate.now(), LocalTime.now()).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将LocalDateTime转换为指定pattern格式的日期时间字符串
     *
     * @param localDateTime
     * @param pattern 时间格式
     * @return String
     */
    public static String convertLocalDateTimeToStringWithoutMillionsecond(LocalDateTime localDateTime, String pattern) {
        if (pattern == null)
            throw new NullPointerException("pattern should not be null.");
        if (pattern.equals(""))
            throw new IllegalArgumentException("pattern should be a date format");
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将LocalDateTime转换为 "yyyy-MM-dd HH:mm:ss" 格式的日期时间字符串
     *
     * @param localDateTime
     * @return String
     */
    public static String convertLocalDateTimeToStringWithoutMillionsecond(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate() + " " + localDateTime.toLocalTime().withNano(0);
    }

    private static final long ONE_MINUTE = 60;
    private static final long ONE_HOUR = 3600;
    private static final long ONE_DAY = 86400;
    private static final long ONE_MONTH = 2592000;
    private static final long ONE_YEAR = 31104000;

    public static Calendar calendar = Calendar.getInstance();

    /**
     * 距离当前时间多久
     *
     * @param date 当前时间
     * @return 显示
     */
    public static String fromToday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        long time = date.getTime() / 1000;
        long now = new Date().getTime() / 1000;
        long ago = now - time;
        if (ago <= ONE_HOUR)
            return ago / ONE_MINUTE + "分钟前";
        else if (ago <= ONE_DAY)
            return ago / ONE_HOUR + "小时" + (ago % ONE_HOUR / ONE_MINUTE) + "分钟前";
        else if (ago <= ONE_DAY * 2)
            return "昨天" + calendar.get(Calendar.HOUR_OF_DAY) + "点" + calendar.get(Calendar.MINUTE) + "分";
        else if (ago <= ONE_DAY * 3)
            return "前天" + calendar.get(Calendar.HOUR_OF_DAY) + "点" + calendar.get(Calendar.MINUTE) + "分";
        else if (ago <= ONE_MONTH) {
            long day = ago / ONE_DAY;
            return day + "天前" + calendar.get(Calendar.HOUR_OF_DAY) + "点" + calendar.get(Calendar.MINUTE) + "分";
        } else if (ago <= ONE_YEAR) {
            long month = ago / ONE_MONTH;
            long day = ago % ONE_MONTH / ONE_DAY;
            return month + "个月" + day + "天前" + calendar.get(Calendar.HOUR_OF_DAY) + "点" + calendar.get(Calendar.MINUTE)
                    + "分";
        } else {
            long year = ago / ONE_YEAR;
            int month = calendar.get(Calendar.MONTH) + 1;// JANUARY which is 0
            // so month+1
            return year + "年前" + month + "月" + calendar.get(Calendar.DATE) + "日";
        }

    }


    /**
     * 格式化时间
     *
     * @param date 时间
     * @return string
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime localDateTime = convertDateToLocalDateTime(date);
        return localDateTime.format(dtf);
    }

    /**
     * 格式化时间
     *
     * @param date    时间
     * @param pattern 定义时间格式
     * @return string
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        if (StringUtil.isEmpty(pattern)) {
            return formatDate(date);
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = convertDateToLocalDateTime(date);
        return localDateTime.format(dtf);
    }


    /**
     * 年份差值
     *
     * @param date
     * @return
     */
    public static String getYearDiff(Date date) {
        return String.valueOf(calculateDiffFromNow(date).getYears());
    }

    /**
     * 计算差值
     *
     * @param date
     * @return
     */
    public static Period calculateDiffFromNow(Date date) {
        return Period.between(DateTimeUtils.convertDateToLocalDate(date), LocalDate.now());
    }

    /**
     * Date ---> LocalDateTime
     *
     * @param date 时间
     * @return LocalDateTime
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).withNano(0);
    }

    /**
     * Date ---> LocalDateTime
     *
     * @return LocalDateTime
     */
    public static LocalDateTime date2LocalDateTime() {
        return date2LocalDateTime(new Date());
    }

    /**
     * Date ---> LocalTime
     *
     * @param date 时间
     * @return LocalTime
     */
    public static LocalTime date2LocalTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalTime().withNano(0);
    }

    /**
     * Date ---> LocalTime
     *
     * @return LocalTime
     */
    public static LocalTime date2LocalTime() {
        return date2LocalTime(new Date());
    }

    /**
     * Date ---> LocalDate
     *
     * @param date 时间
     * @return LocalDate
     */
    public static LocalDate date2LocalDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Date ---> LocalDate
     *
     * @return LocalDate
     */
    public static LocalDate date2LocalDate() {
        return date2LocalDate(new Date());
    }

    /**
     * LocalDateTime ---> Date
     *
     * @param localDateTime
     * @return Date
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDateTime ---> Date
     *
     * @return Date
     */
    public static Date localDateTime2Date() {
        return localDateTime2Date(LocalDateTime.now());
    }

    /**
     * LocalDate ---> Date
     *
     * @param localDate
     * @return Date
     */
    public static Date localDate2Date(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalTime ---> Date
     *
     * @param localTime
     * @return Date
     */
    public static Date localTime2Date(LocalTime localTime) {
        return Date.from(LocalDateTime.of(LocalDate.now(), localTime).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalTime ---> Date
     *
     * @return Date
     */
    public static Date localTime2Date() {
        return localTime2Date(LocalTime.now());
    }

    /**
     * LocalDateTime ---> String
     *
     * @param localDateTime
     * @param pattern
     * @return String
     */
    public static String localDateTime2String(LocalDateTime localDateTime, String pattern) {
        if (pattern == null)
            throw new NullPointerException("pattern should not be null.");
        if (pattern == "")
            throw new IllegalArgumentException("pattern should be a date format");
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * LocalDateTime ---> String
     *
     * @param localDateTime
     * @return String
     */
    public static String localDateTime2String(LocalDateTime localDateTime) {
        return localDateTime2String(localDateTime, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * String ---> LocalDateTime
     *
     * @param string
     * @param pattern
     * @return
     */
    public static LocalDateTime string2LocalDateTime(String string, String pattern) {
        return LocalDateTime.parse(string, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * String ---> LocalDateTime
     *
     * @param string
     * @return
     */
    public static LocalDateTime string2LocalDateTime(String string) {
        return string2LocalDateTime(string, "yyyy-MM-dd HH:mm:ss");
    }
}
