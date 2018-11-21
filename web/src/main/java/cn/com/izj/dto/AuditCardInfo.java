package cn.com.izj.dto;

/**
 * @author: 朱鸿平
 * @date: 2018/8/14 21:42
 */
public class AuditCardInfo {

    private Long userId;//用户id
    private Integer state;//0不通过 1通过
    private Long auditorId;//审核者id
    private String remark;//审核不通过原因

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Long auditorId) {
        this.auditorId = auditorId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
