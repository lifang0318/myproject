package cn.com.izj.base.enums;

/**
 * @author: 朱鸿平
 * @date: 2018/6/29 20:53
 */
public enum PayTypeEnum {
    BALANCE(0, "余额支付"),
    ALIPAY(1, "支付宝支付"),
    WECHAT(2, "微信支付");

    /**
     * 编码
     */
    private int value;
    /**
     * 描述
     */
    private String desc;

    // 构造方法
    private PayTypeEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 获取枚举
     *
     * @param value
     * @return
     */
    public static PayTypeEnum getByValue(int value) {
        for (PayTypeEnum e : values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

}
