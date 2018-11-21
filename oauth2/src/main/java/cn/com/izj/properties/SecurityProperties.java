package cn.com.izj.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by GouBo on 2018/6/3 18:30.
 */
@ConfigurationProperties(prefix = "app.security")
public class SecurityProperties {

    private ValidateCodeProperties code = new ValidateCodeProperties();

    private OAuth2Properties oauth2 = new OAuth2Properties();

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }

    public OAuth2Properties getOauth2() {
        return oauth2;
    }

    public void setOauth2(OAuth2Properties oauth2) {
        this.oauth2 = oauth2;
    }
}
