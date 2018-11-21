package cn.com.izj.dto;

/**
 * Created by GouBo on 2018/9/3 23:37.
 */
public class BluePasswordDto {
    /**
     * 车辆id
     */
    private Long carId;
    /**
     * 蓝牙密码
     */
    private String bluePassword;

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getBluePassword() {
        return bluePassword;
    }

    public void setBluePassword(String bluePassword) {
        this.bluePassword = bluePassword;
    }
}
