package cn.com.izj.authorize;

import cn.com.izj.properties.SecurityConstants;
import cn.com.izj.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * Created by GouBo on 2018/6/3 18:30.
 */
@Component
@Order(Integer.MIN_VALUE)
public class DefaultAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Autowired
    private SecurityProperties securityProperties;

    private final static String DEFAULT_VALIDATE_CODE_URL = SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/**";

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(
                SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                DEFAULT_VALIDATE_CODE_URL,
                SecurityConstants.DEFAULT_GET_TOKEN_URL,
                SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE
        ).permitAll();
        return false;
    }
}
