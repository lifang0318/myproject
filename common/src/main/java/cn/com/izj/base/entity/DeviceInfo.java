package cn.com.izj.base.entity;

import cn.com.izj.mybatis.annotation.Table;

/**
 * @author: 朱鸿平
 * @date: 2018/7/15 13:10
 */
@Table("device_info")
public class DeviceInfo extends BaseEntity {

    public static final Integer ONLINE = 1;
    public static final Integer OFFLINE = 0;

    private String deviceId;//设备id
    private String ccid;//设备卡id
    private Integer state;//设备状态 0下线 1上线

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCcid() {
        return ccid;
    }

    public void setCcid(String ccid) {
        this.ccid = ccid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
