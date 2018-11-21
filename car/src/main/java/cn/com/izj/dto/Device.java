package cn.com.izj.dto;

/**
 * Created by GouBo on 2018/8/19 17:35.
 */
public class Device {
    private Double latitude;//纬度
    private Double longitude;//经度
    private int speed;//速度 单位 为km/hour
    private int batteryRemaining;//剩余电量百分比，范围是 0〜100
    private int remainderRange;//剩余续航里程，单位千米
    private int totalMileage;//总里程,单位为：千米
    private int gpsSignalIntensity;//GPS信号强度

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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

    public int getGpsSignalIntensity() {
        return gpsSignalIntensity;
    }

    public void setGpsSignalIntensity(int gpsSignalIntensity) {
        this.gpsSignalIntensity = gpsSignalIntensity;
    }
}
