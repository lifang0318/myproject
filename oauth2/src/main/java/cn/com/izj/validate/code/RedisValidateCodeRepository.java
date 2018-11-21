package cn.com.izj.validate.code;

import cn.com.izj.validate.ValidateCodeException;
import cn.com.izj.validate.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * Created by GouBo on 2018/6/3 18:30.
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    @Autowired(required = false)
    private RedisTemplate<Object, Object> redisTemplate;


    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type) {
        redisTemplate.opsForValue().set(buildKey(request, type), code, 30, TimeUnit.MINUTES);
    }


    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {
        Object value = redisTemplate.opsForValue().get(buildKey(request, type));
        if (value == null) {
            return null;
        }
        return (ValidateCode) value;
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType type) {
        redisTemplate.delete(buildKey(request, type));
    }


    private String buildKey(ServletWebRequest request, ValidateCodeType type) {
        if (ValidateCodeType.SMS.equals(type)) {
            String mobile;
            try {
                mobile = ServletRequestUtils.getStringParameter(request.getRequest(), "mobile");
            } catch (ServletRequestBindingException e) {
                throw new ValidateCodeException("mobile不能为空!");
            }

            if (StringUtils.isBlank(mobile)) {
                throw new ValidateCodeException("mobile不能为空!");
            }
            return "code:" + type.toString().toLowerCase() + ":" + mobile;
        }

        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("请在请求头中携带deviceId参数");
        }
        return "code:" + type.toString().toLowerCase() + ":" + deviceId;
    }
}
