package cn.com.izj.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: 朱鸿平
 * @date: 2018/6/3 18:34
 */
public class StringUtil {

    private final static String COMMA = ",";

    private static String SLPIT_STR = "-";
    private static String EMPTY_STR = "";

    /**
     * 将以逗号分隔的字符串转换成字符串数组
     *
     * @param valStr 目标数据
     * @return String[]
     */
    public static String[] StrtoList(String valStr) {
        int i = 0;
        String TempStr = valStr;
        String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
        valStr = valStr + ",";
        while (valStr.indexOf(',') > 0) {
            returnStr[i] = valStr.substring(0, valStr.indexOf(','));
            valStr = valStr.substring(valStr.indexOf(',') + 1, valStr.length());
            i++;
        }
        return returnStr;
    }

    /**
     * 去掉字符串前后空格
     *
     * @param strSrc 目标数据
     */
    public static String getStr(Object strSrc) {
        if (isEmpty(strSrc)) {
            return "";
        }
        return strSrc.toString().trim();
    }

    /**
     * 判断指定字符串是否为空
     *
     * @param string 指定的字符串
     * @return 若字符串为空对象（string==null）或空串（长度为0），则返回true；否则，返回false.
     */
    public static boolean isEmpty(Object string) {
        return (("null".equals(string)) || (string == null) || (string.toString().trim().length() == 0));
    }

    /**
     * 判定非空
     *
     * @param _string
     * @return
     */
    public static boolean isNotEmpty(Object _string) {
        return (!isEmpty(_string));
    }


    /**
     * 获取字符串(为空则转为默认字符串)
     *
     * @param strSrc     当前字符串
     * @param defaultStr 默认字符串
     */
    public static String getStrByDefault(Object strSrc, String defaultStr) {
        if (isEmpty(strSrc)) {
            return defaultStr;
        }
        return getStr(strSrc.toString());
    }

    /**
     * 获取int
     *
     * @param strSrc 目标数据
     */
    public static int getInt(Object strSrc) {
        if (isEmpty(strSrc)) {
            return 0;
        }
        try {
            return Integer.parseInt(strSrc.toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取long
     *
     * @param strSrc 目标数据
     */
    public static long getLong(Object strSrc) {
        if (isEmpty(strSrc)) {
            return 0;
        }
        try {
            return Long.parseLong(strSrc.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取double
     *
     * @param strSrc 目标数据
     */
    public static double getDouble(Object strSrc) {
        if (isEmpty(strSrc)) {
            return 0;
        }
        try {
            return Double.parseDouble(strSrc.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * list转换为逗号分隔的字符串
     *
     * @param list 目标list
     */
    public static String listToStr(List<?> list) {
        StringBuilder str = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (Object ob : list) {
                str.append(",").append(ob);
            }
            str = new StringBuilder(str.substring(1));
        }
        return str.toString();
    }

    /**
     * 获取注册ip
     *
     * @param request 请求request
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (!StringUtils.isEmpty(ip)) {
            String[] ips = ip.split(",");
            if (ips.length > 1)
                ip = ips[0];
        }
        return ip;
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 字符串替换函数：用于将指定字符串中指定的字符串替换为新的字符串。
     *
     * @param strSrc 源字符串。
     * @param strOld 被替换的旧字符串
     * @param strNew 用来替换旧字符串的新字符串
     * @return 替换处理后的字符串
     */
    public static String replaceStr(String strSrc, String strOld,
                                    String strNew) {
        if (strSrc == null || strNew == null || strOld == null)
            return strSrc;

        // 提取源字符串对应的字符数组
        char[] srcBuff = strSrc.toCharArray();
        int nSrcLen = srcBuff.length;
        if (nSrcLen == 0)
            return "";

        // 提取旧字符串对应的字符数组
        char[] oldStrBuff = strOld.toCharArray();
        int nOldStrLen = oldStrBuff.length;
        if (nOldStrLen == 0 || nOldStrLen > nSrcLen)
            return strSrc;

        StringBuilder retBuilder = new StringBuilder(
                (nSrcLen * (1 + strNew.length() / nOldStrLen)));

        int i, j, nSkipTo;
        boolean bIsFound;

        i = 0;
        while (i < nSrcLen) {
            bIsFound = false;
            // 判断是否遇到要找的字符串
            if (srcBuff[i] == oldStrBuff[0]) {
                for (j = 1; j < nOldStrLen; j++) {
                    if (i + j >= nSrcLen)
                        break;
                    if (srcBuff[i + j] != oldStrBuff[j])
                        break;
                }
                bIsFound = (j == nOldStrLen);
            }
            // 若找到则替换，否则跳过
            if (bIsFound) { // 找到
                retBuilder.append(strNew);
                i += nOldStrLen;
            } else { // 没有找到
                if (i + nOldStrLen >= nSrcLen) {
                    nSkipTo = nSrcLen - 1;
                } else {
                    nSkipTo = i;
                }
                for (; i <= nSkipTo; i++) {
                    retBuilder.append(srcBuff[i]);
                }
            }
        }
        return retBuilder.toString();
    }

    /**
     * 判断字符串是否在指定字符串数组中
     *
     * @param str          字符串
     * @param array        字符串数组
     * @param isIgnoreCase 是否忽略大小写
     */
    public static boolean isInArray(String str, String[] array, boolean isIgnoreCase) {
        if (array == null || array.length == 0) {
            return false;
        }
        if (str == null || str.length() == 0) {
            return false;
        }
        String str0 = str.trim();
        for (String anArray : array) {
            String s1 = anArray;
            if (s1 != null) {
                s1 = s1.trim();
                boolean b0 = isIgnoreCase ? s1.equalsIgnoreCase(str0) : s1.equals(str0);
                if (b0) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 用字符将字符串切割成数组
     *
     * @param str    字符串
     * @param sDelim 分割符
     * @return String[]
     */
    public static String[] split(String str, String sDelim) {
        // String[] str
        if (str == null || sDelim == null) {
            return new String[0];
        }

        StringTokenizer stTemp = new StringTokenizer(str, sDelim);
        int nSize = stTemp.countTokens();
        if (nSize == 0) {
            return new String[0];
        }

        String[] strs = new String[nSize];
        int i = 0;
        while (stTemp.hasMoreElements()) {
            strs[i] = stTemp.nextToken().trim();
            i++;
        }
        return strs;
    }

    /**
     * 将一个list按照给定的连接符联结起来
     *
     * @param arList     进行操作的list
     * @param sSeparator 连接符
     * @return 连接后的字符串
     */
    public static String join(List<Object> arList, String sSeparator) {
        if (CollectionUtils.isEmpty(arList))
            return null;

        return join(arList.toArray(), sSeparator);
    }

    /**
     * 将一个数组按照给定的连接符联结起来
     *
     * @param arColl     进行操作的数组
     * @param sSeparator 连接符
     * @return 连接后的字符串
     */
    public static String join(Object[] arColl, String sSeparator) {
        if (arColl == null || arColl.length == 0 || sSeparator == null)
            return null;

        if (arColl.length == 1)
            return arColl[0].toString();

        StringBuilder result = new StringBuilder(arColl[0].toString());
        for (int i = 1; i < arColl.length; i++) {
            if (arColl[i] == null) {
                continue;
            }
            result.append(sSeparator);
            result.append(arColl[i].toString());
        }
        return result.toString();
    }

    /**
     * 正则匹配参数 获取参数
     *
     * @param exp     正则表达式
     * @param content 参数
     * @return 匹配值
     */
    public static String getRegExp(String exp, String content) {
        Pattern pattern = Pattern.compile(exp); // 正则匹配
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }

    /**
     * 格式化double
     *
     * @param dividend     被除数
     * @param total        除数
     * @param decimalCount 保留小数位数
     * @return 字符串接收
     */
    public static String formatDouble(double dividend, double total, int decimalCount) {
        double doubleNum = (dividend * 1.0) / total;
        String doubleNumStr = "";

        switch (decimalCount) {
            case 1:
                DecimalFormat df1 = new DecimalFormat("######0.0");
                return df1.format(doubleNum);
            case 2:
                DecimalFormat df2 = new DecimalFormat("######0.00");
                return df2.format(doubleNum);
            case 3:
                DecimalFormat df3 = new DecimalFormat("######0.000");
                return df3.format(doubleNum);
            case 4:
                DecimalFormat df4 = new DecimalFormat("######0.0000");
                return df4.format(doubleNum);
            default:
                break;
        }
        return doubleNumStr;
    }

    /**
     * 数组转逗号分隔的字符串
     */
    public static String arrayToCommaStr(String[] mobiles) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String mobile : mobiles) {
            stringBuffer.append(mobile).append(COMMA);
        }
        String str = stringBuffer.toString();
        if (stringBuffer.indexOf(COMMA) != -1) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * 驼峰命名风格的字符串转换为下划线的字符串
     *
     * @param camel 驼峰命名风格的字符串
     * @return 下划线的字符串
     */
    public static final String camelToUnderScore(String camel) {
        return org.apache.commons.lang3.StringUtils.join(org.apache.commons.lang3.StringUtils.splitByCharacterTypeCamelCase(camel), "_").toLowerCase();
    }

    /**
     * 取得GUID
     */
    public static String getGUID() {
        return UUID.randomUUID().toString().replaceAll(SLPIT_STR, EMPTY_STR);
    }
}
