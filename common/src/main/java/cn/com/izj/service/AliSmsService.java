package cn.com.izj.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author GouBo
 * @date 2018/9/8
 */
@Service
public class AliSmsService {

    private static final String DEFAULT_SIGN_NAME = "你行你开";

    /**
     * 阿里短信发送验证码
     *
     * @param mobile 手机号
     * @param code   验证码
     */
    public SendSmsResponse sendCaptcha(String mobile, String code) {
        String regExp = "^[1][3-9][0-9]{9}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(mobile);
        if (!m.matches()) {
            throw new RuntimeException("手机号码格式不对！");
        }

        String templateCode = "SMS_144151527";
        String templateParam = "{\"code\":\"" + code + "\"}";

        return AliSmsSender.sendSms(new String[]{mobile}, DEFAULT_SIGN_NAME, templateCode, templateParam);
    }

    /**
     * 发送键盘密码
     *
     * @param mobile   手机号码
     * @param password 键盘密码
     */
    public SendSmsResponse sendKeyboardPassword(String mobile, String password) {
        String templateCode = "SMS_144151610";
        String templateParam = "{\"code\":\"" + password + "\"}";
        return AliSmsSender.sendSms(new String[]{mobile}, DEFAULT_SIGN_NAME, templateCode, templateParam);
    }

    /**
     * 发送审核成功短信
     *
     * @param mobile 手机号
     */
    public SendSmsResponse sendReviewSuccess(String mobile) {
        String templateCode = "SMS_144151613";
        return AliSmsSender.sendSms(new String[]{mobile}, DEFAULT_SIGN_NAME, templateCode, null);
    }

    /**
     * 发送身份证审核失败短信
     *
     * @param mobile 手机号码
     */
    public SendSmsResponse sendReviewIdentityFailed(String mobile) {
        String templateCode = "SMS_144151617";
        return AliSmsSender.sendSms(new String[]{mobile}, DEFAULT_SIGN_NAME, templateCode, null);
    }

    /**
     * 发送驾驶证审核失败短信
     *
     * @param mobile 手机号码
     */
    public SendSmsResponse sendReviewDriversLicenseFailed(String mobile) {
        String templateCode = "SMS_144151619";
        return AliSmsSender.sendSms(new String[]{mobile}, DEFAULT_SIGN_NAME, templateCode, null);
    }
}
