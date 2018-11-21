package cn.com.izj.controller.support;

/**
 * Created by GouBo on 2018/6/9 1:34.
 */
public class SimpleResponse {

    public SimpleResponse(Object result) {
        this.result = result;
    }

    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
