package cn.com.izj.base.entity.pay;

import cn.com.izj.mybatis.annotation.Id;
import cn.com.izj.mybatis.annotation.Table;
import cn.com.izj.mybatis.keygen.KeyGeneratorType;

import java.util.Date;

/**
 * 车主提现
 *
 * @author: 朱鸿平
 * @date: 2018/6/6 21:57
 */
@Table("withdraw_deposit")
public class WithdrawDeposit {
    @Id(KeyGeneratorType.AUTO)
    private Long id;
    private String tradeNumber;//提现订单号
    private Long userId;//用户id
    private Integer drawlAmount;//提现金额
    private Integer auditState;//审核状态 0审核中 1审核成功 2审核失败
    private Long auditorId;//审核者id
    private Date createTime;//创建时间
    private Date auditTime;//审核时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getDrawlAmount() {
        return drawlAmount;
    }

    public void setDrawlAmount(Integer drawlAmount) {
        this.drawlAmount = drawlAmount;
    }

    public Integer getAuditState() {
        return auditState;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }

    public Long getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Long auditorId) {
        this.auditorId = auditorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }
}
