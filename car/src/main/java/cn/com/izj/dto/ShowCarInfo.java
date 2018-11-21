package cn.com.izj.dto;

/**
 * 车辆展示信息
 *
 * @author: 朱鸿平
 * @date: 2018/6/9 16:35
 */
public class ShowCarInfo extends BaseCarInfo {

    private Integer mileagePrice;//里程价格 分/公里
    private Integer timeFee;//计时价格 分/分钟
    private Integer maxDistance;//可行驶最大距离
    private Long parkId;//停车场id
    private String parkName;//停车场名称
    private String parkAddress;//停车场地址
    private String deviceId;//设备Id

    public Integer getMileagePrice() {
        return mileagePrice;
    }

    public void setMileagePrice(Integer mileagePrice) {
        this.mileagePrice = mileagePrice;
    }

    public Integer getTimeFee() {
        return timeFee;
    }

    public void setTimeFee(Integer timeFee) {
        this.timeFee = timeFee;
    }

    public Integer getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(Integer maxDistance) {
        this.maxDistance = maxDistance;
    }

    public Long getParkId() {
        return parkId;
    }

    public void setParkId(Long parkId) {
        this.parkId = parkId;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getParkAddress() {
        return parkAddress;
    }

    public void setParkAddress(String parkAddress) {
        this.parkAddress = parkAddress;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
