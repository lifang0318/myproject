package cn.com.izj.utils;

/**
 * Created by GouBo on 2018/7/12 23:41.
 */
public class CodecUtil {

    public static final String ESCAPE_7E = "7d02";
    public static final String ORIGIN_7E = "7e";
    public static final String ESCAPE_7D = "7d01";
    public static final String ORIGIN_7D = "7d";

    /**
     * 计算校验码
     */
    public static byte calculationVerifyCode(byte[] bytes) {
        byte l = 0;
        for (int i = 0; i < bytes.length; i++) {
            if (i == 1) {
                byte b1 = bytes[i - 1];
                byte b2 = bytes[i];
                l = (byte) (b1 ^ b2);
            } else if (i > 1) {
                byte c = bytes[i];
                l = (byte) (l ^ c);
            }
        }
        return l;
    }
}
