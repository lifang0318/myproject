package cn.com.izj.config;

/**
 * @author: lifang
 * @description:微信支付配置
 * @date: Created in 2018/6/27 23:14
 * @version:
 */
public interface WechatPayConfig {
    /**
     * 微信开发平台应用ID
     */
//    String APPID = "wx1eec87a2ca1af7ce";
    String APPID = "wx573f46942b7cffbf";
    /**
     * 微信支付商户号
     */
//    String MCH_ID = "1226934302";
    String MCH_ID = "1509046511";

    /**
     * 商户证书文件路径，申请退款时需要加载此文件
     */
    String CERTIFICATE_File_Path = "/usr/local/wechat_certificate/apiclient_cert.p12";
    /**
     * 应用对应的凭证
     */
    String APP_SECRET = "f71e041791a34c5f56756a0ed126421c";
    /**
     * 商户号对应的密钥
     */
    String PARTNER_KEY = "123466";
    /**
     * 商户id
     */
    String PARTNER_ID = "14698sdfs402dsfdew402";
    /**
     * 常量固定值
     */
    String GRANT_TYPE = "client_credential";

    /**
     * 行程订单微信服务器回调通知URL
     */
    String TRIP_ORDER_NOTIFY_URL = "http://132.232.128.121:8088/mobile/wechatpay/tripOrder/wxPayNotify";

    /**
     * 充值订单微信服务器回调通知URL
     */
    String RECHARGE_ORDER_NOTIFY_URL = "http://132.232.128.121:8088/mobile/wechatpay/rechargeOrder/wxPayNotify";

    /**
     * 押金缴纳微信服务器回调通知URL
     */
    String DEPOSIT_ORDER_NOTIFY_URL = "http://132.232.128.121:8088/mobile/wechatpay/depositOrder/wxPayNotify";

    /**
     * 获取预支付ID的接口URL 统一下单接口地址
     */
    String REQUEST_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 关闭订单接口,避免出现(订单重复提交出错)
     */
    String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
    /**
     * 申请退款地址
     */
    String WECHAT_ORDER_REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    /**
     * 查看订单接口
     */
    String ORDER_QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
    /**
     * API密钥
     */
//    String CLIENT_SECRET = "c1xOpSyZWph6VozYx3Mz0FXqS2tboYJ8";
    String CLIENT_SECRET = "mqPnwJePir5i4AIhOQfZegexz2Ijui8U";
    /**
     * 返回状态码
     */
    String RETURN_CODE = "return_code";
    /**
     * 返回信息
     */
    String RETURN_MSG = "return_msg";
    /**
     * 业务结果
     */
    String RESULT_CODE = "result_code";
    /**
     * 预支付交易会话标识
     */
    String PREPAY_ID = "prepay_id";


}
