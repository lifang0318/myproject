package cn.com.izj.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 订单号生成工具，生成非重复订单号，理论上限1毫秒1000个，可扩展
 *
 * @author: 朱鸿平
 * @date: 2018/6/4 21:57
 */
public class OrderUtil {
    /**
     * 锁对象，可以为任意对象
     */
    private static final Object lockObj = "lockerOrder";
    /**
     * 订单号生成计数器
     */
    private static long orderNumCount = 0L;

    /**
     * 生成非重复订单号，理论上限1毫秒1000个，可扩展
     */
    public static String makeOrderNum() {
        // 最终生成的订单号
        StringBuilder orderNum = new StringBuilder();
        try {
            String finOrderNum;
            synchronized (lockObj) {
                // 取系统当前时间作为订单号变量前半部分，精确到毫秒
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
                long nowLong = Long.parseLong(LocalDateTime.now().format(dtf));
                // 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值1000个，1秒100万
                int maxPerMSECSize = 1000;
                if (orderNumCount > maxPerMSECSize) {
                    orderNumCount = 0L;
                }
                //组装订单号
                String countStr = maxPerMSECSize + orderNumCount + "";
                finOrderNum = nowLong + countStr.substring(1);
                // 混淆订单号
                String ymd = finOrderNum.substring(0, 8);
                String subFinOrderNum = finOrderNum.substring(8);
                orderNum.append(yearMonthDayMix(ymd));
                orderNum.append(subFinOrderNum);
                orderNumCount++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderNum.toString();
    }

    /**
     * 混淆年月日
     *
     * @param ymd ss
     * @return string
     */
    private static String yearMonthDayMix(String ymd) {
        StringBuilder str = new StringBuilder();
        if (StringUtil.isNotEmpty(ymd)) {
            if (ymd.contains("-")) {
                ymd = ymd.replace("-", "");
            }
            str.append(ymd, 0, 2);
            str.append(ymd, 4, 6);
            str.append(ymd, 2, 4);
            str.append(ymd.substring(6));
            return Integer.valueOf(str.toString(), 16) + "";
        }
        return str.toString();
    }

    /**
     * 反解订单号
     *
     * @param tradeNumber 订单号
     * @return string
     */
    private static String yearMonthDayRecover(String tradeNumber) {
        String str;
        StringBuilder ymd = new StringBuilder();
        if (StringUtil.isNotEmpty(tradeNumber)) {
            str = tradeNumber.substring(0, 9);
            str = Integer.toHexString(Integer.valueOf(str));

            ymd.append(str, 0, 2);
            ymd.append(str, 4, 6);
            ymd.append(str, 2, 4);
            ymd.append(str.substring(6));
            ymd.append(tradeNumber.substring(9));
        }
        return ymd.toString();
    }

    public static void main(String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        System.out.println(LocalDateTime.now().format(dtf));
        // 测试多线程调用订单号生成工具
        try {
            for (int i = 0; i < 200; i++) {
                Thread t1 = new Thread(() -> {
                    for (int j = 0; j < 1000; j++) {
                        String order = makeOrderNum();
                        System.out.println(order);
                    }
                }, "at" + i);
                t1.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
