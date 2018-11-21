package cn.com.izj.authentication;

import cn.com.izj.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * Created by GouBo on 2018/6/3 18:30.
 * 表单登录配置
 */
@Component
public class DefaultFormAuthenticationConfig {

    @Autowired
    protected AuthenticationSuccessHandler defaultAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler defaultAuthenticationFailureHandler;

    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
                .successHandler(defaultAuthenticationSuccessHandler)
                .failureHandler(defaultAuthenticationFailureHandler);
    }

}