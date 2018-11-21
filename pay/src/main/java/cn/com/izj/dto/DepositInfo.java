package cn.com.izj.dto;

import java.util.Date;

/**
 * @author: 朱鸿平
 * @date: 2018/8/16 23:42
 */
public class DepositInfo {

    private Long userId;//用户id
    private Integer zmScore;//芝麻分数
    private Integer type;//押金类型
    private Integer amount;//押金金额
    private Integer payState;//支付状态
    private Integer payType;//支付类型
    private Date createTime;//缴纳时间
    private String orderNum;//押金单号

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getZmScore() {
        return zmScore;
    }

    public void setZmScore(Integer zmScore) {
        this.zmScore = zmScore;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
