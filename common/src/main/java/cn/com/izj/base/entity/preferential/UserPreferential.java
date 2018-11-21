package cn.com.izj.base.entity.preferential;

import cn.com.izj.mybatis.annotation.Id;
import cn.com.izj.mybatis.annotation.Table;
import cn.com.izj.mybatis.keygen.KeyGeneratorType;

import java.util.Date;

/**
 * @author: lifang
 * @description:用户优惠券
 * @date: Created in 2018/7/11 23:18
 * @version:
 */
@Table("user_preferential")
public class UserPreferential {

    /**
     * 未使用
     */
    public static final Integer STATE_UNUSE = 0;
    /**
     * 已使用
     */
    public static final Integer STATE_USED = 1;

    @Id(KeyGeneratorType.AUTO)
    private Long id;
    private Long userId;//用户Id
    private Long preferentialId;//优惠券Id
    private Date distributeTime;//发放时间
    private Integer state;//状态
    private String discription;//描述
    private Date createTime;

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

    public Long getPreferentialId() {
        return preferentialId;
    }

    public void setPreferentialId(Long preferentialId) {
        this.preferentialId = preferentialId;
    }

    public Date getDistributeTime() {
        return distributeTime;
    }

    public void setDistributeTime(Date distributeTime) {
        this.distributeTime = distributeTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
