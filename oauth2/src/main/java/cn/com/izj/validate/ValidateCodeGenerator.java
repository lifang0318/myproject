package cn.com.izj.validate;

import cn.com.izj.validate.code.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by GouBo on 2018/6/3 18:30.
 */
public interface ValidateCodeGenerator {
    ValidateCode generate(ServletWebRequest request);
}
