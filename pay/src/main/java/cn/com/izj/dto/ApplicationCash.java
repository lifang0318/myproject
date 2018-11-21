package cn.com.izj.dto;

import java.util.Date;

/**
 * 申请提现
 *
 * @author: 朱鸿平
 * @date: 2018/7/1 10:30
 */
public class ApplicationCash {

    private String tradeNumber;//提现单号
    private Long userId;//用户id
    private Integer drawAmount;//提现金额
    private Date createTime;//创建时间
    private String realName;//用户姓名
    private String cardNumber;//银行卡号码
    private Integer auditState;//提现状态

    public String getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(String tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getDrawAmount() {
        return drawAmount;
    }

    public void setDrawAmount(Integer drawAmount) {
        this.drawAmount = drawAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Integer getAuditState() {
        return auditState;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }
}
