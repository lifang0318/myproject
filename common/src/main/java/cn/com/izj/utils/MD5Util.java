package cn.com.izj.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @author: 朱鸿平
 * @date: 2018/6/25 21:36
 */
public class MD5Util {
    private static MD5Util instance = new MD5Util();

    private MD5Util() {
    }

    public static MD5Util getInstance() {
        if (null == instance)
            instance = new MD5Util();
        return instance;
    }

    public String getMD5Code(String inStr) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
        byte[] md5Bytes = md5.digest(inStr.getBytes());
        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString().toUpperCase();
    }

    /**
     * 加密解密算法 执行一次加密，两次解密
     */
    public String convertMD5(String inStr) {
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        return new String(a);
    }

}
