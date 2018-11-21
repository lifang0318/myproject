package cn.com.izj.entity;

import cn.com.izj.mybatis.annotation.Id;
import cn.com.izj.mybatis.annotation.Table;
import cn.com.izj.mybatis.keygen.KeyGeneratorType;

/**
 * Created by GouBo on 2018/9/2 17:39.
 */
@Table("device_password")
public class DevicePassword {
    @Id(KeyGeneratorType.AUTO)
    private Long id;
    private String deviceId;
    private String keyboardPassword;
    private String bluePassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getKeyboardPassword() {
        return keyboardPassword;
    }

    public void setKeyboardPassword(String keyboardPassword) {
        this.keyboardPassword = keyboardPassword;
    }

    public String getBluePassword() {
        return bluePassword;
    }

    public void setBluePassword(String bluePassword) {
        this.bluePassword = bluePassword;
    }
}
