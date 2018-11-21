package cn.com.izj.base;

/**
 * @author: 朱鸿平
 * @date: 2018/6/6 20:49
 */
public interface CONSTANT {

    String API_CODE_MSG_PREFIX_KEY = "api_code_msg_";//返回信息前缀

    Integer ONLINE_MINIMUM_FUEL = 20;//车辆上限最低油量标准
    Integer ONLINE_MINIMUM_ELECTRIC = 20;//车辆上限最低电量标准
    Integer ONLINE_MINIMUM_DISTANCE = 10;//车辆续航里程 单位公里

    Double PARK_LIMIT_DISTANCE = 400D;//停车场误差范围值
    String PARK_STATION = "parkStation:";//停车场位置缓存key
    String ORDER_CONTRAIL_PREFIX = "orderContrail";
    String RESERVATION_CAR = "reservation_car:";//预约车辆缓存key
    //String START_ORDER = "start_order:";//开始订单缓存key
    Double NEAREST_PARK_DISTANCE = 100000D;
    String SEARCH_CAR_REDIS = "search_car:";
    String OPEN_CLOSE_CAR_REDIS = "open_close_car:";

    /**
     * 高德地图里程计算API调用地址
     */
    String AMAP_MILEAGE_CALCULATE_URL = "http://restapi.amap.com/v4/grasproad/driving";

    /**
     * 高德地图轨迹图片生成API调用地址
     */
    String AMAP_TRIPCONTRAIL_IMAGE_URL = "http://restapi.amap.com/v3/staticmap";

    /**
     * 高德地图里程计算API调用的key
     */
    String AMAP_KEY = "68f3bec8b461396f135d298bf321df18";

    /**
     * 行程轨迹图片保存路径
     */
//    String TRIPCONTRAIL_IMAGE_SAVE_PATH = "D:/image/tripcontrail/";

    /**
     * 用户提现最小额度 10000 单位 分 默认提现必须大于10元
     */
    Integer WITHDRAW_DEPOSIT_MIN = 10 * 100;

    /**
     * 审核中
     */
    Integer AUDIT_APPLICATION = 0;
    /**
     * 审核通过
     */
    Integer AUDIT_PASS = 1;
    /**
     * 审核不通过
     */
    Integer AUDIT_FAIL = 2;

    /**
     * 订单号前缀 充值
     */
    String PREFIX_ORDER_RECHARGE = "R";
    /**
     * 支付 / 车主收益
     */
    String PREFIX_ORDER_PAY = "P";
    /**
     * 提现
     */
    String PREFIX_ORDER_WITHDRAW = "W";

    /**
     * 提现缓存key
     */
    String WITHDRAW_LIMIT = "withdraw_limit";

    /**
     * 行程订单支付描述信息前缀
     */
    String TRIP_ORDER_PAYINFO_PREFIX = "你行你开行程订单";

    /**
     * 充值订单支付描述信息前缀
     */
    String RECHARGE_ORDER_PAYINFO_PREFIX = "你行你开充值订单";

    /**
     * 押金支付描述信息前缀
     */
    String DEPOSIT_ORDER_PAYINFO_PREFIX = "你行你开押金支付";

    /**
     * 开车门
     */
    Integer OPEN_CAR = 7;

    /**
     * 关车门
     */
    Integer CLOSE_CAR = 8;

    /**
     * 寻车
     */
    Integer FIND_CAR = 3;

    /**
     * 延时更新预约车辆状态 16分钟(单位 ms 毫秒)
     */
    long DELAY_TIME = 16 * 60 * 1000;

    String PAY_KEY = "payKey:";

    String QINIU_ACCESS_KEY = "EIgygDTwX89mAfwEKc3xoy_hrDZGtSQDqWg-NvJy";
    String QINIU_SECRET_KEY = "FjX9_AhS_Bu31o-YueSLITEElP5P4wYtOpFO6KeZ";
    /**
     * 七牛云空间
     */
    String QINIU_BUKET = "izjimage";

    /**
     * 七牛url前缀
     */
    String PREFIX_URL = "http://pdaxdtr0a.bkt.clouddn.com/";

    /**
     * 5折月卡用户
     */
    double HALF_OFF = 0.5;
    String GPS_INFO = "gps_info_";
}
