package cn.com.izj.validate.code.sms;

/**
 * Created by GouBo on 2018/7/1 18:44.
 */
public interface SmsCodeSender {
    void send(String mobile, String code);
}
