package cn.com.izj.dto;

import java.util.Date;

/**
 * @author: 朱鸿平
 * @date: 2018/8/23 0:04
 */
public class QueryCondition {

    private String username;//用户账号
    private String realName;//用户真实姓名
    private String cardNumber;//用户身份证号码
    private Integer depositState;//0 未交押金 1已交押金
    private Integer cardState;//身份认证状态 0未认证，1审核中 2已审核 3认证失败
    private Integer driverState;//驾驶证认证状态 0未认证，1审核中 2已审核 3认证失败
    private Date beginDate;//时间起点
    private Date endDate;//时间结束点
    private Integer pageSize;//每页数量
    private Integer pageNumber;//第几页
    private Integer halfUser;//是否5折用户

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getDepositState() {
        return depositState;
    }

    public void setDepositState(Integer depositState) {
        this.depositState = depositState;
    }

    public Integer getCardState() {
        return cardState;
    }

    public void setCardState(Integer cardState) {
        this.cardState = cardState;
    }

    public Integer getDriverState() {
        return driverState;
    }

    public void setDriverState(Integer driverState) {
        this.driverState = driverState;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getHalfUser() {
        return halfUser;
    }

    public void setHalfUser(Integer halfUser) {
        this.halfUser = halfUser;
    }
}
