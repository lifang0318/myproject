package cn.com.izj.validate.code.sms;

import cn.com.izj.properties.SecurityConstants;
import cn.com.izj.support.SimpleResponse;
import cn.com.izj.validate.AbstractValidateCodeProcessor;
import cn.com.izj.validate.code.ValidateCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by GouBo on 2018/7/1 18:45.
 */
@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 短信验证码发送器
     */
    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
        smsCodeSender.send(mobile, validateCode.getCode());

        request.getResponse().setContentType("application/json;charset=UTF-8");
        request.getResponse().getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("ok")));
    }

}
