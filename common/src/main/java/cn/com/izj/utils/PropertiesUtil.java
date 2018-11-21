package cn.com.izj.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 配置文件工具类
 */
public class PropertiesUtil {
    private static final String FILEPATH = "common-config";

    public static int bundleToInt(String key) {
        return Integer.parseInt(ResourceBundle.getBundle(FILEPATH).getString(key));
    }

    public static double bundleToDouble(String key) {
        return Double.parseDouble(ResourceBundle.getBundle(FILEPATH).getString(key));
    }

    /**
     * <font color="red">无占位符的字符资源</font>
     *
     * @param key
     * @return {@link String}
     * @Title bundle
     * @since 1.0
     */

    public static String bundle(String key) {
        return ResourceBundle.getBundle(FILEPATH).getString(key);
    }

    /**
     * 获取属性文件数据
     *
     * @param key      文件内容kay
     * @param filePath 属性文件路径
     * @return
     */
    public static String getValueByKey(String key, String filePath) {
        try {
            return StringUtil.getStr(ResourceBundle.getBundle(filePath).getString(key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getValueByKey(String key) {
        return StringUtil.getStr(ResourceBundle.getBundle(FILEPATH).getString(key));
    }

    public static int getIntValueByKey(String key) {
        return StringUtil.getInt(ResourceBundle.getBundle(FILEPATH).getString(key));
    }

    public static String getApiCodeMsgByKey(String key) {
        return StringUtil.getStr(ResourceBundle.getBundle(FILEPATH).getString(key));
    }

    public static Double getDoubleApiCodeMsgByKey(String key) {
        return StringUtil.getDouble(ResourceBundle.getBundle(FILEPATH).getString(key));
    }

    /**
     * <font color="red">有占位符的字符资源</font>
     *
     * @param key
     * @param arguments
     * @return {@link String}
     * @Title bundle
     * @since 1.0
     */
    public static String bundle(String key, Object... arguments) {
        return MessageFormat.format(ResourceBundle.getBundle(FILEPATH).getString(key), arguments);
    }
}
