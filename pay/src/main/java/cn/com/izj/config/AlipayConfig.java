package cn.com.izj.config;

/**
 * 支付宝支付配置
 *
 * @author lifang
 */
public interface AlipayConfig {
    /**
     * 线上环境配置
     */
    //支付宝网关
     String GATEWAY_URL = "https://openapi.alipay.com/gateway.do";
    //应用ID
     String APP_ID = "2018062760448255";
     //商户私钥，PKCS8格式的RSA2私钥
      String MERCHANT_PRIVATE_KEY ="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCGu/WXzKpt6tWPLoJ1rc9/KbaV26id2WiCTFjKOTrVKJDZUolvSdeRKkJkgL3E+iH6ulfSyv3AAcgaBK9WDtRrpXOiH9RHaHUWc2Mlf+r3WspPJtGNob62Ykh5/CnyUO8C+dZbC1njDCR7XPEglbw2ndUpNW6Bb+LVlMslEA6qxFsn1wbbt8lGZ9I8coeTPVDXvCpC+ewOoaQvGun5fzECMWcncQ9exAHUQKANdWGaqta8WP50r3zHrWBt3ca03Xa1lN9IuOQ1QAvOCHiRrlHWcCHmQvj8Q9i8oJtCIY3WSUDpmZ6rcFSEYo87IQ4UBqQ+CozMRNQqU1FCM+7T2H45AgMBAAECggEACBG3uriqOF0JH0Mk0xFuzOndoon+Rj3gEXWrF6lnyZ5dVnan/n9IpswGH+jJ9MMaR97jJZ+8pMQ2jMBdE+VdeXj2PO51EzXBVW/hrzxDAT9xwF3NzRKzzk6CkrO0e2u3dNErslYysvNmN/EwC8iL9iVrv8UQcjYWOr5nkDeu+TEk8EVLQaZ2Ist6FbRH+LgkpJVKR/ToVlBLMRWAs7tU0ttDbR7RoshOSYiaEyk2kERJ/Qn1sSwL7ZcWtQbTSKpbWiJY5+Tljyj24XMD6FPlCc6h0MOcZsU0GpyCHWrtQUSVA/zxmRPvMe/BFWaIM1zC2mOCWHbf2dlsVzooWov8BQKBgQDRdb6uvhH7Qsi1XV4Fy2wiDWOXKLWLof4H9pVF21wMtJifB/oiY+GvagNKKSj5G7byQhhpIkG6WYNPL4zt/KZL7eJSQaXLA4H2pvUawOpLUu7+rVBPMOUgsOM7bXCX6UT0o0EwRbmVM81DG/UCj/gb6YJoblIvIR4EFJBD8MfQrwKBgQCkq8DYixwyxFC5ANd6vIassELdPrbjksDcxFCQRk/6nQhcFrVs18xFpEuUZA0o7/5gceg5SKmP+LJdJ8d3gWLuOJ4y37Fdae426UBII5ybXzJfdmzhVuhY9oCau2HNJhj0g0QshU/wLNgmSEmd8r4ZPutdDCTYg883FyuN4c3JlwKBgQC/7fhv/lJTvfpT1d4VyTSob0RWekMAU8uGWHinrj1GlyVSc+jMImHmbmndRqH4wWB90/qyO2Wm7+bWZGfqmpt27992KBlXUTuBW7M0dJk7tB6y1vTD27XYor1U8w88487/q6hMJ1fKU9E+MRvs4ScjzHBmaNB9xwbODlmzJ6qwmQKBgQCanYXqDNwCMuBUhRzN6nxNYwa1NDCHB4E9CLtYbBymhJGX3d6lfw4XCcfJYhQVLxpatjlEqBxFyU64NySL6hnY8aJgxDaGLnN0bZhk48MlA8KJbemXv4FUHsfsASs4c6AU3bfCUb4YFhMyBsfQnV6u16wo5sEl5sVGxeOoSa9uqwKBgHPcnZug/WO3iAP9d4DSW1CZy+D22IZB4UqLwDTu8ve9+WR7WTFEK6HcQ8dXoJvnr1Od+ATGDp9JxCapO/qBpIk83Mc7zxG6Pdyet/WHOaoB6TU6eH+TjtbzzTUiC5iW/tbifiV7uSibWQSTnt6ztkghOTsFN/wLPUW8bDF6BZ2E";
     //字符编码格式
     String CHARSET = "utf-8";
     //支付宝公钥，对应APPID下的支付宝公钥
     String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl4030FizoXAbwtVClhcX4Ipd2vYb8Vl9BUWOhw+GhakAQPHTMCXRJcSaDqu8hF8KgT3lmWIqV9IGmv3L5Azd3Nfidwq7I0dVMG+/H8XSy/FLocyjlqoeelBsorCaUsA6Yn6OoFIPtveEc7/RC9Z945MQXZRL/LOJWinmyKamffiWchxeZb7frEHkXkTfP2SdnXyDKAn/YHp+JRWV1Pu1tG8pRhafJUdmVtiAiX9U63qKe7Tv1td0ifDS4NOsp1KO6v33dnwRTwGvDi8d6QIcAmtc9fWHuzIVUmiOVAFJAKE9itbZu3hGeHGDewWZfk4+F3D62MN30A7vfFS4Nj8hdwIDAQAB";
     //签名方式
     String SIGN_TYPE = "RSA2";

    /**
     * 沙箱环境配置
     */
//    //支付宝网关
//    String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
//    //应用ID
//    String APP_ID = "2016091400507052";
//    //商户私钥，PKCS8格式的RSA2私钥
//    String MERCHANT_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCegKKC1YlbgqtNv2x1hjkf/iYH6kDEbXRNRb8ciaLCra7N+lC+FZxL5NhKqgYoM2dUynnxGIY2G6ORrThWN/JNcET8FwEzeDs44liRAt6oyLEGN5frKIuedBXzH+U3Ix9ehDMxZYTuBsFrCm/heRGIW976GRzOzZ6cpzepogVJiuwlfumxkod3mcSVzKtuQJeOQUQzZyOkw2vDk1FrlYEwZYYJoa2NRYSrGg2Plr7WywtETmVo4EDUBR53x7nndt+nht9SaYVvwrbnQaHmUG6UBZGe8Xz2H+nsAUDkxeHfNjw8EmNXsnm+upwzbtYfo4tdH9mzJBRl/hCUYEhW8MLlAgMBAAECggEBAISvTluUAk5WzqxhCJxBZmS/znplwR4uOmPNdTSgi13JI0JOv3TAhnyJ7/5g4pKYpXG3pekMDJ4S+RSkGzshznbA+x7E6z548aqm4FlUO6yzhG2d8FDtOiPFLfK+ypUjk8j9hhVd1K+lfBjtPcdUIDc0IS7gAtukCZk96jzY4vAyR4Zk8g2hEXvHEhwc74JrkfNUUALuRtHF/d7ED3wqjUIwBWbWrDCg4pQNHW5YeOz/oCKQUKFFrmNIEXWKmcwzI3tK8DUeUNoZm6bGGFMKd3BZSy+8sOmDX1MmmGJhX12/N1H1I0C1UfaG9JK/fEIpdCFkir+QMbEfvhgdQATcKqkCgYEA1124vwZ0nfnoyz6pK5nQyP/EgaakBBB76kicAahKXcySpuwJ6gdSUAT3Pd4ryJddn89lg/8Gcn/1dvI2FUXrYN+f6iW1W/ybSpZf+E9iOB9et4aG3RT4g+lF0I4a/HxdWuEttc2pmlmBaVaInxi2aOkPOfb9GD3tAqPwtpz2Te8CgYEAvGhgAGrgPzvuj0oiNDFfx/3cOaoHEK8uYABH/89qkEAL1s4/XIhv0SaXDNLPWua87881fEnR999IB1Je2W49X9KrgK06hav1dUE0PZ3S1DMA5OXJyPqaMZjMnODHzMyQKAsWwB71yzqDevU4MkKBoc9mJ94lbhAc2rkbP9oU0GsCgYA/+atDs5s4wmAQ4BBIz2xiCPhKu0HFo0/sDJ2rMF7G9loqE7vuNTaSZqc7TaAb+jrpmiZBy6QSp0g3iUPfleGR6VwLph/1r1hCE+F2Vb6LhWYRhnjRe8VHCiifB1ek4RyqPnHhq7JSE3yJ2hbDjAfDqlfHlEMizGJ3glOjK4VPKQKBgDr38BQxXz5rY581dL8hNPlDW0oxDUULj1g8vkwyfwfnA82Xf/1WNqkfiudYk82/qiLkl7ju4ejS2ZhxD36+Ijd7mHINxeNQjuLEJbWjT/lafU0nHqDozpn3WRNAx0gyPmxKDDTVe9i/16cEWTJh3nDmmPKhdNWd9Wn/xaeWei1BAoGBAIGq3CIeVm9UTCOhj2mCyP3Tlt8ns1GxM3+Ae/t25tzao8viv0M99K18ppf0A3kvy+oVmQNEI1QMeFqdAp3vsEU2dI48SnBOjhj3OYPpb/m4JgDkmLDutjGr4n0du3yqvdG2owck4XsgI6yCoUUvefyL1QAscViO82pLCs1g4nWB";
//    //字符编码格式
//    String CHARSET = "utf-8";
//    //支付宝公钥，对应APPID下的支付宝公钥
//    String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAz5IECOSR7LtRgoQNXjAdsYL4+21CO6w8u/bYlXW2atWWy7b9QyfW93R63COKpZ3W+cCCrBAUkkYu3sbzFYv27p33ZF7ImAaU0NrvoKIBW2uL0kzgbTVSYFwaSW/EyQrRn5vgsmAdxxC/CiB/GkKU19aSsr8Hd3BGGfSwqjesuANxTVDoFf18BCdj7TeK7Ce6Gi708iypfFIuqmF8hHGE4JijC1aymAZ4OFOcIHbTM2R1VEaBRCcV3gn1+qmq3xKd5Nm7snC8qQsuO4qZzQAiLeBanf6IUit+ODxL+7lneFQIXtH/r7gYHqC3+Gfcvv0NPw8+n16BmrdC8qMrPSFy+wIDAQAB";
//    //签名方式
//    String SIGN_TYPE = "RSA2";

    //行程订单支付宝服务器回调通知URL
    String TRIP_ORDER_NOTIFY_URL = "http://132.232.128.121:8088/mobile/alipay/tripOrder/alipayNotify";
    //String TRIP_ORDER_NOTIFY_URL = "http://58195a4b.ngrok.io/mobile/alipay/tripOrder/alipayNotify";

    //充值订单支付宝服务器回调通知URL
    String RECHARGE_ORDER_NOTIFY_URL = "http://132.232.128.121:8088/mobile/alipay/rechargeOrder/alipayNotify";
    //String RECHARGE_ORDER_NOTIFY_URL = "http://58195a4b.ngrok.io/mobile/alipay/rechargeOrder/alipayNotify";

    //押金缴纳支付宝服务器回调通知URL
    String DEPOSIT_ORDER_NOTIFY_URL = "http://132.232.128.121:8088/mobile/alipay/depositOrder/alipayNotify";
   // String DEPOSIT_ORDER_NOTIFY_URL = "http://58195a4b.ngrok.io/mobile/alipay/depositOrder/alipayNotify";

//    String RETURN_URL = "http://e0dddd4e.ngrok.io/mobile/pay/alipayNotify";
    /**
     * 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
     */
    String TIMEOUT_EXPRESS = "30m";
    /**
     * 销售产品码，商家和支付宝签约的产品码
     */
    String PRODUCT_CODE = "QUICK_MSECURITY_PAY";

}
