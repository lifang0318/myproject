package cn.com.izj.service;

import cn.com.izj.validate.code.sms.SmsCodeSender;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 中国电信短信网关
 * Created by GouBo on 2018/7/16 23:58.
 */
@Component
public class TeleSmsCodeSender implements SmsCodeSender {

    private Logger logger = LoggerFactory.getLogger(TeleSmsCodeSender.class);

    @Autowired
    private SmsService smsService;

    @Autowired
    private AliSmsService aliSmsService;

    @Override
    public void send(String mobile, String code) {
        logger.info("向手机" + mobile + "发送短信验证码" + code);

//        String content = "验证码为:" + code + ",有效期60秒!";
//        smsService.smsSend(new String[]{mobile}, content);

        //使用阿里短信发送验证码
        SendSmsResponse sendSmsResponse = aliSmsService.sendCaptcha(mobile, code);
        String sendResult = sendSmsResponse == null ? "失败" : sendSmsResponse.getMessage();
        logger.info("阿里向手机" + mobile + "发送短信验证码结果:" + sendResult);
    }
}
