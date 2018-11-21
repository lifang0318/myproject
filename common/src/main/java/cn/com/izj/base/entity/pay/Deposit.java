package cn.com.izj.base.entity.pay;

import cn.com.izj.base.entity.BaseEntity;
import cn.com.izj.mybatis.annotation.Table;

import java.util.Date;

/**
 * 押金信息
 *
 * @author: 朱鸿平
 * @date: 2018/8/16 23:29
 */
@Table("deposit")
public class Deposit extends BaseEntity {

    /**
     * 未交纳
     */
    public static final Integer STATE_NOT = 0;
    /**
     * 待支付
     */
    public static final Integer STATE_WAITING_PAY = 1;
    /**
     * 已支付
     */
    public static final Integer STATE_PAYED = 2;

    /**
     * 退款中
     */
    public static final Integer STATE_REFUNDING = 3;

    /**
     * 已退款
     */
    public static final Integer STATE_REFUND = 4;

    /**
     * 支付宝支付
     */
    public static final Integer PAY_TYPE_ALIPY = 1;
    /**
     * 微信支付
     */
    public static final Integer PAY_TYEP_WECHAT = 2;

    /**
     * 现金
     */
    public static final Integer DEPOSIT_CASH = 0;
    /**
     * 花呗预授权
     */
    public static final Integer DEPOSIT_TOKIO = 1;
    /**
     * 芝麻免押金
     */
    public static final Integer DEPOSIT_ZM = 2;

    private Long userId;//用户id
    private Integer zmScore;//芝麻分数
    private Integer type;//押金类型
    private Integer amount;//押金金额
    private Integer payState;//押金状态
    private Integer payType;//支付类型
    private String orderNum;//押金单号
    private String refundNumber;//退款单号，押金退还后有值
    private Date payTime;//支付时间

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

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getRefundNumber() {
        return refundNumber;
    }

    public void setRefundNumber(String refundNumber) {
        this.refundNumber = refundNumber;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
}
