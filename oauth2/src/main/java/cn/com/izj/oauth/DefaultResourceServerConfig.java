package cn.com.izj.oauth;

import cn.com.izj.authentication.DefaultFormAuthenticationConfig;
import cn.com.izj.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import cn.com.izj.authorize.AuthorizeConfigManager;
import cn.com.izj.validate.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;

/**
 * Created by GouBo on 2018/6/3 18:30.
 */
@Configuration
@EnableResourceServer
public class DefaultResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private DefaultFormAuthenticationConfig defaultFormAuthenticationConfig;

    @Autowired
    private OAuth2WebSecurityExpressionHandler expressionHandler;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.expressionHandler(expressionHandler);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        defaultFormAuthenticationConfig.configure(http);
        http.apply(validateCodeSecurityConfig).and().apply(smsCodeAuthenticationSecurityConfig).and().csrf().disable();
        authorizeConfigManager.config(http.authorizeRequests());
    }
}
