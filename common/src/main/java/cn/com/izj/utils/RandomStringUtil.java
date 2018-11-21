package cn.com.izj.utils;

import java.util.Random;
import java.util.UUID;

/**
 * @author: 朱鸿平
 * @date: 2018/6/4 22:04
 */
public class RandomStringUtil {

    /**
     * 生成 0 至指定位数范围内的随机数
     *
     * @param range 随机数范围上限
     * @return 生成String类型随机数
     */
    public static long random(int range) {
        return new Random().nextInt(range);
    }

    /**
     * 生成用户登录令牌，不重复
     *
     * @return 生成唯一的uuid字符串
     */
    public static String token() {
        return UUID.fromString(UUID.nameUUIDFromBytes(UUID.randomUUID().toString().getBytes()).toString()).toString();
    }

    public static String captcha() {
        return String.valueOf((int) ((Math.random() * ((1 << 3) + 1) + 1) * 1000));
    }

    /**
     * 随机生成数字
     *
     * @param randomSome 随机数个数
     * @return string
     */
    public static String randomStr(int randomSome) {
        if (randomSome == 1) {
            return new Random().nextInt(10) + "";
        }
        int rangeNumber = 1;
        for (int i = 0; i < (randomSome - 1); i++) {
            rangeNumber = rangeNumber * 10;
        }
        return String.valueOf((int) ((Math.random() * ((1 << 3) + 1) + 1) * rangeNumber));
    }

    private static Random random = new Random();

    static {
        random.setSeed(System.currentTimeMillis());
    }


    /**
     * 生产min和max之间的随机数，但是概率不是平均的，从min到max方向概率逐渐加大。
     * 先平方，然后产生一个平方值范围内的随机数，再开方，这样就产生了一种“膨胀”再“收缩”的效果。
     *
     * @param min 最小
     * @param max 最大
     * @return long
     */
    public static long xRandom(long min, long max) {
        long val = sqrt(nextLong(sqr(max - min)));
        if (val <= 0) {
            val = min;
        }
        return val;
    }

    private static long sqrt(long n) {
        // 开方
        return (long) Math.sqrt(n);
    }

    private static long sqr(long n) {
        // 平方
        return n * n;
    }

    private static long nextLong(long n) {
        if (n == 0)
            return 0L;
        return random.nextInt((int) n);
    }

    /**
     * 生成给定长度的随机数字字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        StringBuffer buffer = new StringBuffer("0123456789");
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        int range = buffer.length();
        for (int i = 0; i < length; i++) {
            sb.append(buffer.charAt(random.nextInt(range)));
        }
        return sb.toString();
    }

}
