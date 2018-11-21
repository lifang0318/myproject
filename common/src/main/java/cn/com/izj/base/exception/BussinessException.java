package cn.com.izj.base.exception;

import cn.com.izj.base.enums.ErrorCodeEnum;

/**
 * @author: lifang
 * @description:
 * @date: Created in 2018/6/13 22:30
 * @version:
 */
public class BussinessException extends RuntimeException {
    /**
     * 错误编码
     */
    private Integer errorCode;

    public BussinessException(ErrorCodeEnum codeEnum) {
        super(codeEnum.getDesc());
        this.errorCode = codeEnum.getValue();
    }

    public BussinessException(ErrorCodeEnum codeEnum,String message) {
        super(message);
        this.errorCode = codeEnum.getValue();
    }

    public BussinessException(Integer code, String message) {
        super(message);
        this.errorCode = code;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
