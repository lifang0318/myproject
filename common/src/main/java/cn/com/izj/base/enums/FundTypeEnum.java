package cn.com.izj.base.enums;

/**
 * 资金操作类型
 *
 * @author: 朱鸿平
 * @date: 2018/6/29 20:53
 */
public enum FundTypeEnum {
    RECHARGE(0, "充值"),
    PAY(1, "支付"),
    WITHDRAW(2, "提现"),
    INCOME(3, "车主收益");

    /**
     * 编码
     */
    private int value;
    /**
     * 描述
     */
    private String desc;

    // 构造方法
    private FundTypeEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 获取枚举
     *
     * @param value
     * @return
     */
    public static FundTypeEnum getByValue(int value) {
        for (FundTypeEnum e : values()) {
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
