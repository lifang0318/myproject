package cn.com.izj.base.entity.car;

import cn.com.izj.base.entity.BaseEntity;
import cn.com.izj.mybatis.annotation.Table;

/**
 * 车辆动态信息
 *
 * @author: 朱鸿平
 * @date: 2018/6/9 16:39
 */
@Table("car_dynamic_info")
public class CarDynamicInfo extends BaseEntity {

    public static final Integer POWER_FUEL = 0;//燃油
    public static final Integer POWER_CHARGE = 1;//电动

    private Integer power;//动力类型 0燃油 1电动
    private Integer fuel;//剩余燃油量
    private Integer electric;//剩余电量
    private Integer maxDistance;//可行驶最大距离
    private Long parkId;//停车场id
    private String parkName;//停车场名称
    private String parkAddress;//停车场位置
    private String stall;//车辆所在车位
    private Integer driveRange;//行车范围
    private Long targetParkId;//车主设置终点停车场Id
    private String targetParkName;//终点停车场名称
    private String deviceId;//车载设备id

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getFuel() {
        return fuel;
    }

    public void setFuel(Integer fuel) {
        this.fuel = fuel;
    }

    public Integer getElectric() {
        return electric;
    }

    public void setElectric(Integer electric) {
        this.electric = electric;
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

    public String getStall() {
        return stall;
    }

    public void setStall(String stall) {
        this.stall = stall;
    }

    public Integer getDriveRange() {
        return driveRange;
    }

    public void setDriveRange(Integer driveRange) {
        this.driveRange = driveRange;
    }

    public Long getTargetParkId() {
        return targetParkId;
    }

    public void setTargetParkId(Long targetParkId) {
        this.targetParkId = targetParkId;
    }

    public String getTargetParkName() {
        return targetParkName;
    }

    public void setTargetParkName(String targetParkName) {
        this.targetParkName = targetParkName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
