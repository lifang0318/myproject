package cn.com.izj.base.entity.park;

import cn.com.izj.base.entity.BaseEntity;
import cn.com.izj.mybatis.annotation.Table;

import java.math.BigDecimal;

/**
 * @author: lifang
 * @description: 停车场
 * @date: Created in 2018/6/6 0:03
 */
@Table("park")
public class Park extends BaseEntity {

    public static final Integer STATUS_DISABLED = 0;//禁用
    public static final Integer STATUS_ENABLE = 1;//启用
    public static final Integer STATUS_DELETE = 2;//删除

    public static final Integer TYPE_COUNT = 0;//按次计费
    public static final Integer TYPE_TIME = 1;//按时计费

    private Long userId;//用户id
    private String name;//停车场名称
    private Integer type;//类型 0：地上 1：地面
    private BigDecimal longitude;//所在位置经度
    private BigDecimal latitude;//所在位置纬度
    private String manager;//负责人姓名
    private String phone;//负责人电话
    private Integer costType;//计费类型 0：按次计费 1：按时计费
    private Integer price;//计费单价
    private String address;//详细地址
    private Integer status; //状态 0：禁用 1：启用 2：删除
    private Integer parkCount;//停车位总数量
    private Integer parkCountBalance;//剩余停车位数量


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCostType() {
        return costType;
    }

    public void setCostType(Integer costType) {
        this.costType = costType;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getParkCount() {
        return parkCount;
    }

    public void setParkCount(Integer parkCount) {
        this.parkCount = parkCount;
    }

    public Integer getParkCountBalance() {
        return parkCountBalance;
    }

    public void setParkCountBalance(Integer parkCountBalance) {
        this.parkCountBalance = parkCountBalance;
    }
}
