package cn.com.izj.event.dto;

/**
 * Created by GouBo on 2018/7/16 23:14.
 */
public class DeviceOnline {
    /**
     * 设备id
     */
    private String deviceId;
    /**
     * 车辆类型,128为燃油,127为电动车
     */
    private int carType;
    /**
     * 手机卡ccid
     */
    private String ccid;

    public DeviceOnline(String deviceId, int carType, String ccid) {
        this.deviceId = deviceId;
        this.carType = carType;
        this.ccid = ccid;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getCarType() {
        return carType;
    }

    public void setCarType(int carType) {
        this.carType = carType;
    }

    public String getCcid() {
        return ccid;
    }

    public void setCcid(String ccid) {
        this.ccid = ccid;
    }
}
