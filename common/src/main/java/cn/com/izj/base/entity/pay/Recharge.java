package cn.com.izj.base.entity.pay;

import cn.com.izj.base.entity.BaseEntity;
import cn.com.izj.mybatis.annotation.Table;

import java.util.Date;

/**
 * @author: lifang
 * @description: 充值订单
 * @date: Created in 2018/7/21 7:26
 * @version:
 */
@Table("recharge")
public class Recharge extends BaseEntity {
    /**
     * 待支付
     */
    public static final Integer STATE_WAITINT_PAY = 0;
    /**
     * 已支付
     */
    public static final Integer STATE_PAYED = 1;
    /**
     * 支付宝支付
     */
    public static final Integer PAY_TYPE_ALIPY = 1;
    /**
     * 微信支付
     */
    public static final Integer PAY_TYEP_WECHAT = 2;

    private String number;//充值单号
    private Long userId;//用户id
    private Long userFundId;//用户钱包id
    private Integer amount;//充值金额
    private Integer state;//状态 0 待支付 1 已支付
    private Integer payType;//支付类型 1支付宝2微信
    private Date payTime;//支付时间

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserFundId() {
        return userFundId;
    }

    public void setUserFundId(Long userFundId) {
        this.userFundId = userFundId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
}
