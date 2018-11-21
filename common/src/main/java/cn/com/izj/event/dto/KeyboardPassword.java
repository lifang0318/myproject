package cn.com.izj.event.dto;

/**
 * Created by GouBo on 2018/9/2 17:24.
 */
public class KeyboardPassword {
    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 键盘密码
     */
    private String password;

    public KeyboardPassword() {
    }

    public KeyboardPassword(String deviceId, String password) {
        this.deviceId = deviceId;
        this.password = password;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
