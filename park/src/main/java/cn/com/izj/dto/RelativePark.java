package cn.com.izj.dto;

import java.math.BigDecimal;

/**
 * @author: lifang
 * @description: 停车场相关信息（停车场地址、经纬度、可用车辆数、剩余停车位数量）
 * @date: Created in 2018/7/7 15:32
 * @version:
 */
public class RelativePark {
    private Long id;//停车场id
    private String address;//停车场地址
    private BigDecimal longitude;//所在位置经度
    private BigDecimal latitude;//所在位置纬度
    private Integer parkCountBalance;//可用车位数
    private Integer validCarCount;//可用车辆数
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Integer getParkCountBalance() {
        return parkCountBalance;
    }

    public void setParkCountBalance(Integer parkCountBalance) {
        this.parkCountBalance = parkCountBalance;
    }

    public Integer getValidCarCount() {
        return validCarCount;
    }

    public void setValidCarCount(Integer validCarCount) {
        this.validCarCount = validCarCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
