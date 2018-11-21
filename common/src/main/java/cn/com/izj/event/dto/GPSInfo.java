package cn.com.izj.event.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by GouBo on 2018/7/7 11:00.
 */
public class GPSInfo implements Serializable {
    /**
     * 设备id,最长12个数字,例如:201800698542
     */
    private String deviceId;
    /**
     * 纬度
     */
    private BigDecimal latitude;
    /**
     * 经度
     */
    private BigDecimal longitude;
    /**
     * 海拔高度，单位为米(m)
     */
    private int altitude;
    /**
     * 速度,读取的 OBD 速度（主要） 或 GPS 定位数据中的速度，注意单位 为km/hour
     */
    private int speed;
    /**
     * 方向, 0—359,正北为 0，顺时针
     */
    private int direction;
    /**
     * 时间
     */
    private Date time;

    /**
     * 剩余电量百分比，范围是 0〜100
     */
    private int batteryRemaining;

    /**
     * 剩余续航里程，单位千米
     */
    private int remainderRange;

    /**
     * 总里程,单位为：千米
     */
    private int totalMileage;

    /**
     * 当前车辆电压,单位为：V
     */
    private int voltage;

    /**
     * GPS信号强度
     */
    private int gpsSignalIntensity;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getBatteryRemaining() {
        return batteryRemaining;
    }

    public void setBatteryRemaining(int batteryRemaining) {
        this.batteryRemaining = batteryRemaining;
    }

    public int getRemainderRange() {
        return remainderRange;
    }

    public void setRemainderRange(int remainderRange) {
        this.remainderRange = remainderRange;
    }

    public int getTotalMileage() {
        return totalMileage;
    }

    public void setTotalMileage(int totalMileage) {
        this.totalMileage = totalMileage;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public int getGpsSignalIntensity() {
        return gpsSignalIntensity;
    }

    public void setGpsSignalIntensity(int gpsSignalIntensity) {
        this.gpsSignalIntensity = gpsSignalIntensity;
    }

    @Override
    public String toString() {
        return "GPSInfo{" +
                "deviceId='" + deviceId + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                ", speed=" + speed +
                ", direction=" + direction +
                ", time=" + time +
                ", batteryRemaining=" + batteryRemaining +
                ", remainderRange=" + remainderRange +
                ", totalMileage=" + totalMileage +
                ", voltage=" + voltage +
                ", gpsSignalIntensity=" + gpsSignalIntensity +
                '}';
    }
}
