package cn.com.izj.base.response;

import cn.com.izj.base.enums.ResponseEnum;

import java.io.Serializable;

/**
 * @author: 朱鸿平
 * @date: 2018/6/6 20:49
 */
public class ApiResult implements Serializable {

    private int code = ResponseEnum.SUCCESS.getValue();
    private String message = ResponseEnum.SUCCESS.getDesc();
    private Object data;

    public ApiResult() {
    }

    public ApiResult(int code, String message, Object data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResult(Object data) {
        super();
        this.data = data;
    }

    public ApiResult(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public ApiResult(ResponseEnum responseEnum) {
        super();
        this.code = responseEnum.getValue();
        this.message = responseEnum.getDesc();
    }

    public ApiResult(ResponseEnum responseEnum, Object data) {
        super();
        this.code = responseEnum.getValue();
        this.message = responseEnum.getDesc();
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 单数据操作验证结果(update,delete,insert)返回apiResult
     *
     * @param result 操作结果
     */
    public static ApiResult validateSingleData(int result) {
        if (1 == result) {
            return new ApiResult(ResponseEnum.SUCCESS.getValue(), ResponseEnum.SUCCESS.getDesc());
        } else {
            return new ApiResult(ResponseEnum.ERROR.getValue(), ResponseEnum.ERROR.getDesc());
        }
    }

    public static ApiResult successWithData(Object data) {
        return new ApiResult(ResponseEnum.SUCCESS.getValue(), ResponseEnum.SUCCESS.getDesc(), data);
    }

    public static ApiResult errorWithData(Object data) {
        return new ApiResult(ResponseEnum.ERROR.getValue(), ResponseEnum.ERROR.getDesc(), data);
    }

}
