package cn.com.izj.support;

/**
 * Created by GouBo on 2018/6/3 18:30.
 */
public class ErrorResponse {
    private String errorCode;
    private String errorMsg;

    public ErrorResponse(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public static ErrorResponse UNAUTHORIZED() {
        return new ErrorResponse("10001", "访问的服务需要身份认证，请引导用户到登录页!");
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
