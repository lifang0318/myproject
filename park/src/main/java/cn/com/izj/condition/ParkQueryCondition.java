package cn.com.izj.condition;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: lifang
 * @description:停车场查询条件
 * @date: Created in 2018/6/6 22:51
 * @version:
 */
public class ParkQueryCondition {
    private Long id;
    private Long userId;//负责人id
    private String name;//停车场名称
    private Integer type;//类型 0：地上 1：地面
    private BigDecimal longitude;//所在位置经度
    private BigDecimal latitude;//所在位置纬度
    private String manager;//负责人姓名
    private String phone;//负责人电话
    private Integer costType;//计费类型 0：按次计费 1：按时计费
    private Integer price;//计费单价
    private String address;//详细地址
    private Integer status;//0：禁用 1：启用 2：删除
    private Date createTime;
    private Date updateTime;
    private Integer pageSize;//每页数量
    private Integer pageNumber;//第几页

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPageSize() {
        if(this.pageSize == null){
            return -1;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        if(this.pageNumber == null ){
            return 1;
        }
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
