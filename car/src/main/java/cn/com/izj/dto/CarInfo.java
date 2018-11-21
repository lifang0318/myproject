package cn.com.izj.dto;

/**
 * Created by GouBo on 2018/8/19 16:52.
 */
public class CarInfo {

    private ShowCarInfo carInfo;//车辆信息
    private Device device;//设备信息

    public ShowCarInfo getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(ShowCarInfo carInfo) {
        this.carInfo = carInfo;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
