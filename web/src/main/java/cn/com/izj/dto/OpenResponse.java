package cn.com.izj.dto;

/**
 * @author GouBo
 * @date 2018/8/26
 */
public class OpenResponse {

    public static final String SUCCESS = "ok";

    public static final String ERROR = "error";


    public OpenResponse(String code) {
        this.code = code;
    }

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
