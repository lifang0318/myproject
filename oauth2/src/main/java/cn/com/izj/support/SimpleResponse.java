package cn.com.izj.support;

/**
 * Created by GouBo on 2018/6/3 18:30.
 */
public class SimpleResponse {

    private Object result;

    public SimpleResponse(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
