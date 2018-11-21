package cn.com.izj.base.enums;

import cn.com.izj.base.RESPONSE;
import cn.com.izj.base.CONSTANT;
import cn.com.izj.utils.PropertiesUtil;

/**
 * @author: 朱鸿平
 * @date: 2018/6/6 20:49
 */
public enum ResponseEnum {

    SUCCESS(RESPONSE.SUCCESS),// 请求成功
    ERROR(RESPONSE.ERROR),// 请求失败

    CAR_IS_USED(1000),//车辆正在使用中
    CAR_FUEL_LACK(1001),//车辆油量不足
    CAR_NOT_IN_PARK(1002),//车辆不在停车场
    CAR_IS_EXIST(1003),//车牌号已存在
    FUND_ERROR(1004),//支付金额错误
    RESEARVATION_NOT_FOUND(1005),//未找到用户预约信息
    RESEARVATION_OVER_TIME(1006),//预约信息超时
    USERNMAE_IS_NULL(1007),//用户名为空
    CARGRADERULE_NOT_FOUND(1008),//未找到对应车辆等级规则
    POINT_AT_LEAST_TWO(1009),//无法计算行程，至少需要两个坐标点
    BALANCE_NOT_ENOUGH(1010),//用户余额不足
    ORDER_NOT_FOUND(1011),//订单未获取到
    /**
     * 微信支付相关的返回
     */
    GET_PREPAYID_SUCCESS(1012),//微信统一订单下单成功
    GET_PREPAYID_FAILED(1013),//微信统一订单下单失败

    RETURN_CAR_FAILED(1014),//还车失败
    CAR_NOT_INPARK(1015),//车辆不在指定停车场
    PARK_COUNT_NOT_ENOUGH(1016),//停车位不足
    NO_IDCARD(1017),//未进行身份认证
    NO_DRIVER_CARD(1018),//未进行驾驶认证
    NO_DEPOSIT(1019),//未交纳押金
    PARK_NOT_EXIST(1020),//停车场不存在
    USER_EXIST(1021),//身份证号码已被认证
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
    ResponseEnum(int value) {
        this.value = value;
        this.desc = PropertiesUtil.getValueByKey(CONSTANT.API_CODE_MSG_PREFIX_KEY + value);
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 获取枚举
     *
     * @param value 值
     * @return ResponseEnum
     */
    public static ResponseEnum getByValue(int value) {
        for (ResponseEnum e : values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }
}
