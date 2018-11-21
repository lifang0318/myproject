package cn.com.izj.service;

/**
 * tcp响应
 * Created by GouBo on 2018/7/16 20:40.
 */
public class TcpResponse {
    private boolean status;
    private String msg;

    public TcpResponse(boolean status) {
        this.status = status;
    }

    public TcpResponse(boolean status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
