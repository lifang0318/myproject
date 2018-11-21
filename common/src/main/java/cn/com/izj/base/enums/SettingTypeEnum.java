package cn.com.izj.base.enums;

/**
 * @author: 朱鸿平
 * @date: 2018/7/20 20:50
 */
public enum SettingTypeEnum {
    UPDATE_VERSION(1, "是否强制升级"),
    DEDUCTIBLES(2, "不计免赔额度"),
    RECHARGE(3, "充值赠送比例"),
    RECHARGE_MAX(4, "充值最大赠送额度"),
    DEPOSIT_AMOUNT(5, "押金金额"),
    PARK_FEE(6,"停车费"),
    ;

    /**
     * 编码
     */
    private int value;

    /**
     * 描述
     */
    private String desc;

    // 构造方法
    SettingTypeEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 获取枚举
     *
     * @param value
     */
    public static SettingTypeEnum getByValue(int value) {
        for (SettingTypeEnum e : values()) {
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
