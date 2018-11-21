package cn.com.izj.base.entity.pay;


import cn.com.izj.mybatis.annotation.Id;
import cn.com.izj.mybatis.annotation.Table;
import cn.com.izj.mybatis.keygen.KeyGeneratorType;

import java.util.Date;

/**
 * @author: 朱鸿平
 * @date: 2018/6/30 11:43
 */
@Table("user_fund_log")
public class UserFundLog {

    @Id(KeyGeneratorType.AUTO)
    private Long id;

    /**
     * '审核 1审核中2审核成功3审核失败',
     */
    private Integer auditState;

    /**
     * 0充值/1支付/2提现/3车主收益
     */
    private Integer fundType;
    /**
     * 支付人/提现人id
     */
    private Long userId;
    /**
     * 用户收支金额，单位分
     */
    private Integer tradeFund;
    /**
     * 支付本地订单号
     */
    private String tradeNumber;
    /**
     * 第三方支付订单号
     */
    private String payNumber;
    /**
     * 客户端ip
     */
    private String clientIp;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 0余额1支付宝2微信
     */
    private Integer payType;
    /**
     * 余额 单位分
     */
    private Integer balance;

    /**
     * 赠送金额余额 单位 分
     */
    private Integer givenBalance;

    /**
     * 车辆id
     */
    private Long carId;

    /**
     * 备注
     */
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAuditState() {
        return auditState;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }

    public Integer getFundType() {
        return fundType;
    }

    public void setFundType(Integer fundType) {
        this.fundType = fundType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getTradeFund() {
        return tradeFund;
    }

    public void setTradeFund(Integer tradeFund) {
        this.tradeFund = tradeFund;
    }

    public String getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(String tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    public String getPayNumber() {
        return payNumber;
    }

    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getGivenBalance() {
        return givenBalance;
    }

    public void setGivenBalance(Integer givenBalance) {
        this.givenBalance = givenBalance;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
