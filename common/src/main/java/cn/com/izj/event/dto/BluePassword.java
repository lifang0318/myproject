package cn.com.izj.event.dto;

/**
 * Created by GouBo on 2018/9/2 17:17.
 */
public class BluePassword {
    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 蓝牙密码
     */
    private String bluePassword;

    public BluePassword() {
    }

    public BluePassword(String deviceId, String bluePassword) {
        this.deviceId = deviceId;
        this.bluePassword = bluePassword;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getBluePassword() {
        return bluePassword;
    }

    public void setBluePassword(String bluePassword) {
        this.bluePassword = bluePassword;
    }
}
