package cn.com.izj.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 十六进制与一般数据类型之间的互相转换
 * Created by GouBo on 2018/7/11 22:21.
 */
public class HexUtil {

    private static final char[] HEX_DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    /**
     * byte[] -> hexString
     */
    public static String encode(byte[] input) {
        if (input == null) {
            throw new IllegalArgumentException("input should not be null");
        }
        StringBuilder builder = new StringBuilder(input.length * 2);
        for (int i = 0; i < input.length; i++) {
            builder.append(HEX_DIGITS[input[i] >>> 4 & 0xf]);
            builder.append(HEX_DIGITS[input[i] & 0xf]);
        }
        return builder.toString();
    }

    /**
     * hexString -> byte[]
     */
    public static byte[] decodeToByteArray(String input) {
        if (input == null) {
            throw new IllegalArgumentException("input should not be null");
        }
        if (input.length() % 2 != 0) {
            throw new IllegalArgumentException("input should be divisible by four");
        }
        String alphabet = new String(HEX_DIGITS);
        String temp = input.toLowerCase();

        byte[] output = new byte[temp.length() / 2];
        for (int i = 0; i < output.length; i++) {
            int highByte = alphabet.indexOf(temp.charAt(i * 2));
            int lowByte = alphabet.indexOf(temp.charAt(i * 2 + 1));
            if (highByte == -1 || lowByte == -1) {
                throw new IllegalArgumentException("input contiain illegal character");
            }
            output[i] = (byte) (highByte << 4 | lowByte);
        }
        return output;
    }

    /**
     * byte -> hexString
     */
    public static String encode(byte input) {
        return encode(new byte[]{input});
    }

    /**
     * 将字符串密码转为byte数组
     */
    public static byte[] strPasswordToByteArray(String password) {
        if (password == null) {
            throw new IllegalArgumentException("password should not be null");
        }
        String alphabet = new String(HEX_DIGITS);
        byte[] byteArray = new byte[password.length()];
        for (int i = 0; i < byteArray.length; i++) {
            int num = alphabet.indexOf(password.charAt(i));
            byteArray[i] = (byte) (num);
        }
        return byteArray;
    }

    /**
     * 蓝牙密码转为byte数字并加密
     */
    public static byte[] bluePasswordToByteArrayAndEncode(String password) {
        byte[] seed = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};
        byte[] byteArray = strPasswordToByteArray(password);

        for (int i = 0; i < 8; i++) {
            byteArray[i] = (byte) (byteArray[i] ^ seed[i]);
        }
        return byteArray;
    }

    /**
     * hexString -> byte
     */
    public static byte decodeToByte(String input) {
        if (input == null)
            throw new IllegalArgumentException("input should not be null");

        if (input.length() != 2)
            throw new IllegalArgumentException("input should be equal to two");

        byte[] temp = decodeToByteArray(input);
        return temp[0];
    }

    /**
     * short -> hexString
     */
    public static String encode(short input) {
        return encode(new byte[]{(byte) (input >>> 8 & 0xff), (byte) (input & 0xff)});
    }

    /**
     * hexString -> short
     */
    public static short decodeToShort(String input) {
        if (input == null)
            throw new IllegalArgumentException("input should not be null");

        if (input.length() != 4)
            throw new IllegalArgumentException("input should be equal to four");

        byte[] temp = decodeToByteArray(input);
        return (short) ((short) (temp[0] & 0xff) << 8 | (short) (temp[1] & 0xff));
    }

    /**
     * integer -> hexString
     */
    public static String encode(int input) {
        return encode(new byte[]{
                (byte) (input >>> 24 & 0xff),
                (byte) (input >>> 16 & 0xff),
                (byte) (input >>> 8 & 0xff),
                (byte) (input & 0xff)});
    }

    /**
     * hexString -> integer
     */
    public static int decodeToInt(String input) {
        if (input == null)
            throw new IllegalArgumentException("input should not be null");

        if (input.length() != 8)
            throw new IllegalArgumentException("input should be equal to eight");

        byte[] temp = decodeToByteArray(input);
        return (temp[0] & 0xff) << 24 | (temp[1] & 0xff) << 16 | (temp[2] & 0xff) << 8 | (temp[3] & 0xff);
    }

    /**
     * long -> hexString
     */
    public static String encode(long input) {
        return encode(new byte[]{
                (byte) (input >>> 56 & 0xff),
                (byte) (input >>> 48 & 0xff),
                (byte) (input >>> 40 & 0xff),
                (byte) (input >>> 32 & 0xff),
                (byte) (input >>> 24 & 0xff),
                (byte) (input >>> 16 & 0xff),
                (byte) (input >>> 8 & 0xff),
                (byte) (input & 0xff)});
    }

    /**
     * hexString -> long
     */
    public static long decodeToLong(String input) {
        if (input == null)
            throw new IllegalArgumentException("input should not be null");

        if (input.length() != 16)
            throw new IllegalArgumentException("input should be equal to sixteen");

        byte[] temp = decodeToByteArray(input);
        return (long) (temp[0] & 0xff) << 56 |
                (long) (temp[1] & 0xff) << 48 |
                (long) (temp[2] & 0xff) << 40 |
                (long) (temp[3] & 0xff) << 32 |
                (long) (temp[4] & 0xff) << 24 |
                (long) (temp[5] & 0xff) << 16 |
                (long) (temp[6] & 0xff) << 8 |
                (long) (temp[7] & 0xff);
    }

    /**
     * 16进制转换为ASCII
     */
    public static String convertHexToString(String hex) {
        if (StringUtils.isBlank(hex)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hex.length() - 1; i += 2) {
            String output = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(output, 16);
            sb.append((char) decimal);
        }
        return sb.toString();
    }
}
