package cn.com.izj.base.enums;

/**
 * @author: 朱鸿平
 * @date: 2018/9/2 0:37
 */
public enum ErrorCodeEnum {
    DATE_TYPE_TRANSFORM_ERROR(1001,"日期类型转换出错");

    /**
     * 编码
     */
    private int value;
    /**
     * 描述
     */
    private String desc;

    // 构造方法
    private ErrorCodeEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 获取枚举
     *
     * @param value
     * @return
     */
    public static ErrorCodeEnum getByValue(int value) {
        for (ErrorCodeEnum e : values()) {
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
