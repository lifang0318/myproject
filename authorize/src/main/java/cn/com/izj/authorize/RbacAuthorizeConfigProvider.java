package cn.com.izj.authorize;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * Created by GouBo on 2018/6/9 1:34.
 * Rbac意为基于角色的权限访问控制（Role-Based Access Control）
 */
@Component
@Order
public class RbacAuthorizeConfigProvider implements AuthorizeConfigProvider {

    private final static String[] PERMIT_URL = new String[]{
            "/system/user/register", "/system/user/check/**", "/partner/**",
            "/mobile/car/getReservationList/**",
            "/mobile/park/findRelativeParks",
            "/mobile/alipay/tripOrder/alipayNotify",
            "/mobile/alipay/rechargeOrder/alipayNotify",
            "/mobile/alipay/depositOrder/alipayNotify",
            "/mobile/wechatpay/tripOrder/wxPayNotify",
            "/mobile/wechatpay/rechargeOrder/wxPayNotify",
            "/mobile/wechatpay/depositOrder/wxPayNotify",
    };

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(PERMIT_URL).permitAll()
                .antMatchers(HttpMethod.GET, "/system/user/me", "/resource/**").authenticated()
                .anyRequest().access("@rbacService.hasPermission(request, authentication)");
        return true;
    }

}
