package cn.com.izj.utils;

import cn.com.izj.base.enums.ErrorCodeEnum;
import cn.com.izj.base.exception.BussinessException;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 朱鸿平
 * @date: 2018/9/2 0:32
 */
public class DateUtil {

    /**
     * 一天所含秒数
     */
    public final static Long ONE_DAY_SECONDS = 86400L;

    /**
     * 一天所含毫秒数
     */
    public final static Long ONE_DAY_MILL_SECONDS = 86400000L;

    /**
     * 年月日期格式
     */
    public final static String YEAR_MONTH_DATE_FORMAT = "yyyyMM";

    /**
     * 短日期格式
     */
    public final static String SHORT_DATE_FORMAT = "yyyyMMdd";

    /**
     * 长日期格式
     */
    public final static String LONG_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 短全时间格式
     */
    public final static String SHORT_FULL_DATETIME_FORMAT = "yyyyMMddHHmmssSSS";

    /**
     * 长时间格式
     */
    public final static String LONG_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 短时间格式
     */
    public final static String SHORT_DATETIME_FORMAT = "yyyyMMddHHmmss";

    /**
     * 短时间+时分格式
     */
    public final static String SHORT_DATE_HOUR_MIN_FORMAT = "yyyyMMdd HH:mm";

    /**
     * 锁对象
     */
    private final static Object LOCK_OBJ = new Object();

    /**
     * GMT时区标识
     */
    private final static String GMT = "GMT";

    /**
     * UTC时区标识
     */
    private final static String UTC = "UTC";

    /**
     * 存放不同的日期模板格式的日期格式的Map
     */
    private final static Map<String, ThreadLocal<SimpleDateFormat>> SDF_MAP = new HashMap<>();

    /**
     * 判断日期是否相等
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return boolean 是表示相等，否表示不相等
     */
    public static boolean equals(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            return date1.compareTo(date2) == 0;
        } else {
            return date1 == null && date2 == null;
        }
    }

    /**
     * 年月日期字符串转换为日期
     *
     * @param dateStr 只包含年月日期字符串 yyyyMM
     * @return Date   日期
     */
    public static Date parseYearMonthDate(String dateStr) {
        return parseDate(dateStr, YEAR_MONTH_DATE_FORMAT);

    }

    /**
     * 短日期字符串转换为日期
     *
     * @param dateStr 短日期字符串 yyyyMMdd
     * @return Date   日期
     */
    public static Date parseShortDate(String dateStr) {
        return parseDate(dateStr, SHORT_DATE_FORMAT);
    }

    /**
     * 短时间字符串转换为日期
     *
     * @param dateStr 短时间字符串 yyyyMMddHHmmss
     * @return Date   日期
     */
    public static Date parseShortDatetime(String dateStr) {
        return parseDate(dateStr, SHORT_DATETIME_FORMAT);
    }

    /**
     * 长日期字符串转换为日期
     *
     * @param dateStr 长日期字符串 yyyy-MM-dd
     * @return Date   日期
     */
    public static Date parseLongDate(String dateStr) {
        return parseDate(dateStr, LONG_DATE_FORMAT);
    }

    /**
     * 长时间字符串转换为日期
     *
     * @param dateStr 长时间字符串 yyyy-MM-dd HH:mm:ss
     * @return Date   日期
     */
    public static Date parseLongDatetime(String dateStr) {
        return parseDate(dateStr, LONG_DATETIME_FORMAT);
    }

    /**
     * 带小时、分钟的短日期格式转换为日期
     *
     * @param dateStr 长时间字符串 yyyyMMdd HH:mm
     * @return Date   日期
     */
    public static Date parseShortDateHourMinDate(String dateStr) {
        return parseDate(dateStr, SHORT_DATE_HOUR_MIN_FORMAT);
    }

    /**
     * 日期字符串转换为日期
     *
     * @param dateStr 日期字符串
     * @param format  日期格式
     * @return Date   日期
     */
    public static Date parseDate(String dateStr, String format) {
        if (StringUtil.isEmpty(dateStr) || dateStr.length() != format.length()) {
            throw new BussinessException(ErrorCodeEnum.DATE_TYPE_TRANSFORM_ERROR);
        }
        try {
            return getSdf(format).parse(dateStr);
        } catch (ParseException pe) {
            throw new BussinessException(ErrorCodeEnum.DATE_TYPE_TRANSFORM_ERROR, pe.getMessage());
        }
    }

    /**
     * 日期转换为短日期字符串
     *
     * @param date 日期
     * @return String 短日期字符串 yyyyMMdd
     */
    public static String formatShortDate(Date date) {
        return formatDate(date, SHORT_DATE_FORMAT);
    }

    /**
     * 日期转换为年月日期字符串
     *
     * @param date 日期
     * @return String 年月日期字符串 yyyyMM
     */
    public static String formatYearMonthDate(Date date) {
        return formatDate(date, YEAR_MONTH_DATE_FORMAT);
    }

    /**
     * 日期转换为短时间字符串
     *
     * @param date 日期
     * @return String 短时间字符串 yyyyMMddHHmmss
     */
    public static String formatShortDatetime(Date date) {
        return formatDate(date, SHORT_DATETIME_FORMAT);
    }

    /**
     * 日期转换为短时间字符串
     *
     * @param date 日期
     * @return String 短时间字符串 yyyyMMddHHmmssSSS
     */
    public static String formatShortFullDatetime(Date date) {
        return formatDate(date, SHORT_FULL_DATETIME_FORMAT);
    }

    /**
     * 日期转换为长日期字符串
     *
     * @param date 日期
     * @return String 长日期字符串 yyyy-MM-dd
     */
    public static String formatLongDate(Date date) {
        return formatDate(date, LONG_DATE_FORMAT);
    }

    /**
     * 日期转换为长时间字符串
     *
     * @param date 日期
     * @return String 长时间字符串 yyyy-MM-dd HH:mm:ss
     */
    public static String formatLongDatetime(Date date) {
        return formatDate(date, LONG_DATETIME_FORMAT);
    }

    /**
     * 日期转换为日期字符串
     *
     * @param date   日期
     * @param format 日期格式
     * @return String 日期字符串
     */
    public static String formatDate(Date date, String format) {
        if (date == null) {
            throw new BussinessException(ErrorCodeEnum.DATE_TYPE_TRANSFORM_ERROR);
        }
        return getSdf(format).format(date);
    }

    /**
     * 长日期格式转换为带小时，分钟的短时间字符串
     *
     * @param date 长日期 yyyy-MM-dd HH:mm:ss
     * @return String   带时分的短时间字符串 yyyyMMdd HH:mm
     */
    public static String formatShortDateHourMinDate(Date date) {
        return formatDate(date, SHORT_DATE_HOUR_MIN_FORMAT);
    }


    /**
     * 获取当前时间所在月份
     *
     * @param dateString 时间字符串yyyyMMdd
     * @return String    月份时间yyyyMM
     */
    public static String getMonth(String dateString) {
        return dateString.substring(0, 6);
    }


    /**
     * 获取当前时间所在月份
     *
     * @param dateString 时间字符串yyyyMMdd
     * @return String    月份时间yyyyMM
     */
    public static String getShortMonth(String dateString) {
        if (dateString == null || dateString.length() < 6) {
            return "";
        }
        return dateString.substring(2, 6);
    }

    /**
     * 获取当前时间所在日期(例如:20170910 -> 10)
     *
     * @param dateString 时间字符串yyyyMMdd
     * @return String    日期
     */
    public static String getDay(String dateString) {
        return dateString.substring(6, dateString.length());
    }

    /**
     * 获得目标时间月份的最后一天
     *
     * @param date 目标时间
     * @return String   月末时间yyyMMdd
     */
    public static String getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        //设为当前月的1号
        calendar.set(Calendar.DATE, 1);

        //加1个月，变为下一个月的1号
        calendar.add(Calendar.MONTH, 1);

        //减去1天，变为当月的最后一天
        calendar.add(Calendar.DATE, -1);

        //获取yyyyMMdd时间格式字符串。
        return DateUtil.formatShortDate(calendar.getTime());
    }

    /**
     * 获取当月的最后一天
     *
     * @param dateString 时间字符串yyyyMMdd
     * @return String    月末时间yyyyMMdd
     */
    public static String getLastDayOfMonth(String dateString) {

        Date date = DateUtil.parseShortDate(dateString);
        return getLastDayOfMonth(date);

    }

    /**
     * 获取当月的第一天
     *
     * @param dateString 时间字符串yyyyMMdd
     * @return String    月初时间yyyMM01
     */
    public static String getFirstDayOfMonth(String dateString) {
        Date date = DateUtil.parseShortDate(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        //设为当前月的1号
        calendar.set(Calendar.DATE, 1);

        //获取yyyyMMdd时间格式字符串。
        return DateUtil.formatShortDate(calendar.getTime());

    }

    /**
     * 最大日期
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return Date 最大日期
     */
    public static Date max(Date date1, Date date2) {
        if (null == date1) {
            return date2;
        }
        if (null == date2) {
            return date1;
        }
        return date1.after(date2) ? date1 : date2;
    }

    /**
     * 最大日期
     *
     * @param shortDate1 yyyyMMdd格式日期1
     * @param shortDate2 yyyyMMdd格式日期2
     * @return String 最大日期
     */
    public static String max(String shortDate1, String shortDate2) {
        Date date1 = DateUtil.parseShortDate(shortDate1);
        Date date2 = DateUtil.parseShortDate(shortDate2);

        Date maxDate = DateUtil.max(date1, date2);

        return DateUtil.formatShortDate(maxDate);
    }

    /**
     * 最小日期
     *
     * @param shortDate1 yyyyMMdd格式日期1
     * @param shortDate2 yyyyMMdd格式日期2
     * @return String    最小日期
     */
    public static String min(String shortDate1, String shortDate2) {
        String maxDateString = max(shortDate1, shortDate2);

        return StringUtils.equals(shortDate1, maxDateString) ? shortDate2 : shortDate1;
    }

    /**
     * 最小日期
     *
     * @param date1 待比较时间1
     * @param date2 待比较时间2
     * @return
     */
    public static Date min(Date date1, Date date2) {
        Date maxDate = max(date1, date2);
        return date1.equals(maxDate) ? date2 : date1;
    }

    /**
     * 短日期添加天数
     *
     * @param shortDate 短日期
     * @param day       天数
     * @return String    加天操作后的日期
     */
    public static String shortDateAddDay(String shortDate, int day) {
        Date date = DateUtil.parseShortDate(shortDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return DateUtil.formatShortDate(cal.getTime());
    }

    /**
     * 短日期添加月份
     *
     * @param shortDate 短日期
     * @param month     天数
     * @return String      加月份操作后的日期
     */
    public static String shortDateAddMonth(String shortDate, int month) {
        Date date = parseShortDate(shortDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, month);
        return DateUtil.formatShortDate(cal.getTime());
    }

    /**
     * 计算两短日期之间的相隔天数
     *
     * @param shortBeginDate 开始日期
     * @param shortEndDate   结束日期
     * @return long           相隔天数
     */
    public static long getMarginDay(String shortBeginDate, String shortEndDate) {
        Date date1 = parseShortDate(shortBeginDate);
        Date date2 = parseShortDate(shortEndDate);
        return (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24);
    }

    /**
     * 计算两个时间之间相隔月份 (取绝对值)
     *
     * @param date1 比较日期1
     * @param date2 比较日期2
     * @return int           间隔多少个月
     */
    public static int getMarginMonth(Date date1, Date date2) {
        // 比较大小 ，endTime始终比startTime大
        Date maxDate = max(date1, date2);
        Date minDate = date1 == maxDate ? date2 : date1;
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        startTime.setTime(minDate);
        endTime.setTime(maxDate);
        // 年间隔
        int yearMargin = endTime.get(Calendar.YEAR) - startTime.get(Calendar.YEAR);
        // 月间隔
        int monthMargin = endTime.get(Calendar.MONTH) - startTime.get(Calendar.MONTH);

        return yearMargin * 12 + monthMargin;
    }

    /**
     * 计算两个时间之间相隔年份 (取绝对值)
     *
     * @param shortBeginDate 比较日期1
     * @param shortEndDate   比较日期2
     * @return int           间隔多少年
     */
    public static int getMarginYear(String shortBeginDate, String shortEndDate) {

        Date date1 = parseShortDate(shortBeginDate);
        Date date2 = parseShortDate(shortEndDate);
        // 比较大小 ，endTime始终比startTime大
        Date maxDate = max(date1, date2);
        Date minDate = date1 == maxDate ? date2 : date1;
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        startTime.setTime(minDate);
        endTime.setTime(maxDate);
        // 年间隔
        return endTime.get(Calendar.YEAR) - startTime.get(Calendar.YEAR);
    }

    /**
     * 返回当前线程的日期格式
     *
     * @param pattern 日期格式字符串
     * @return SimpleDateFormat 日期格式
     */
    private static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> tl = SDF_MAP.get(pattern);
        if (tl == null) {
            synchronized (LOCK_OBJ) {
                tl = SDF_MAP.get(pattern);
                if (tl == null) {
                    tl = ThreadLocal.withInitial(() -> new SimpleDateFormat(pattern));
                    SDF_MAP.put(pattern, tl);
                }
            }
        }
        return tl.get();
    }

    /**
     * 修改timestamp2date
     *
     * @param time
     * @return
     */
    public static Date changeTimestampToDate(Timestamp time) {
        if (time != null) {
            return new Date(time.getTime());
        } else {
            return null;
        }
    }


    /**
     * Date转换为Timestamp
     *
     * @param date
     * @return
     */
    public static Timestamp dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        }

        return new Timestamp(date.getTime());

    }

    /**
     * 严格校验日期格式与有效性, 20170230,2017022x校验不通过
     *
     * @param dateStr 日期字符串
     * @param format  日期格式
     * @return true: 有效日期, false: 无效日期
     */
    public static boolean validateDate(String dateStr, String format) {
        // 取消宽松验证
        SimpleDateFormat sdf = getSdf(format);
        sdf.setLenient(false);
        try {
            // 通过先将dateStr转换为日期，再将日期还原回字符串，规避2017022x会Parse为20170202的情况
            Date tmpDate = sdf.parse(dateStr);
            String tmpStr = sdf.format(tmpDate);
            return tmpStr.equals(dateStr);
        } catch (ParseException pe) {
            return false;
        } finally {
            // 从map中取出sdf，使用结束后，需要将其还原为默认值true
            sdf.setLenient(true);
        }
    }

    /**
     * 严格校验短日期字符串是否为有效日期
     *
     * @param dateStr 短时间字符串 yyyyMMdd
     * @return true: 有效日期, false: 无效日期
     */
    public static boolean validateShortDate(String dateStr) {
        return validateDate(dateStr, SHORT_DATE_FORMAT);
    }

    /**
     * 严格校验年月字符串是否为有效日期
     *
     * @param dateStr 短日期字符串 yyyyMM
     * @return true: 有效日期, false: 无效日期
     */
    public static boolean validateYearMonthDate(String dateStr) {
        if (StringUtil.isEmpty(dateStr)) {
            return false;
        }
        return validateDate(dateStr, YEAR_MONTH_DATE_FORMAT);
    }

    /**
     * 判断当前日期是否在给定周期内
     *
     * @param nowDate   当前日期，yyymmdd
     * @param startDate 周期起始日期，yyyymmdd
     * @param endDate   周期结束日期，yyyymmdd
     * @return true: 在给定周期内   false: 不在给定周期内
     */
    public static boolean isBetweenPeriod(String nowDate, String startDate, String endDate) {
        return (getMarginDay(startDate, nowDate) >= 0 && getMarginDay(nowDate, endDate) >= 0);
    }

    /**
     * 短日期字符串转换为短日期+时分字符串
     *
     * @param dateStr
     * @return
     */
    public static String transShortDateHourMin(String dateStr) {
        String longDate = dateStrTransLongDateTime(dateStr, SHORT_DATE_FORMAT);
        return longDateTransFormat(longDate, SHORT_DATE_HOUR_MIN_FORMAT);
    }

    /**
     * 长日期格式字符串 yyyy-MM-dd HH:mm:ss 转换为 指定时间格式字符串
     *
     * @param dateStr 长日期格式字符串 yyyy-MM-dd HH:mm:ss
     * @param format  指定时间格式
     * @return 指定时间格式字符串
     */
    public static String longDateTransFormat(String dateStr, String format) {
        Date date = parseDate(dateStr, LONG_DATETIME_FORMAT);
        return formatDate(date, format);
    }

    /**
     * 指定时间格式字符串转换为长日期格式字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr 指定时间格式字符串
     * @param format  时间格式
     * @return 长日期格式字符串 yyyy-MM-dd HH:mm:ss
     */
    public static String dateStrTransLongDateTime(String dateStr, String format) {
        Date date = parseDate(dateStr, format);
        return formatLongDatetime(date);
    }
}
