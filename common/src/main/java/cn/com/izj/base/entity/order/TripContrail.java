package cn.com.izj.base.entity.order;

import cn.com.izj.base.entity.BaseEntity;
import cn.com.izj.mybatis.annotation.Id;
import cn.com.izj.mybatis.annotation.Table;
import cn.com.izj.mybatis.keygen.KeyGeneratorType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 轨迹数据
 *
 * @author: 朱鸿平
 * @date: 2018/6/6 21:41
 */
@Table("trip_contrail")
public class TripContrail {
    @Id(KeyGeneratorType.AUTO)
    private Long id;
    private String orderId;//订单号
    private String contrailPoint;//轨迹点数据
    private String contrailUrl;//轨迹图url
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getContrailPoint() {
        return contrailPoint;
    }

    public void setContrailPoint(String contrailPoint) {
        this.contrailPoint = contrailPoint;
    }

    public String getContrailUrl() {
        return contrailUrl;
    }

    public void setContrailUrl(String contrailUrl) {
        this.contrailUrl = contrailUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
