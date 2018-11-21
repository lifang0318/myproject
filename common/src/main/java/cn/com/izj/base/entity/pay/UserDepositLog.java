package cn.com.izj.base.entity.pay;

import cn.com.izj.mybatis.annotation.Id;
import cn.com.izj.mybatis.annotation.Table;
import cn.com.izj.mybatis.keygen.KeyGeneratorType;

import java.util.Date;

/**
 * @author: lifang
 * @description:
 * @date: Created in 2018/8/20 22:44
 * @version:
 */
@Table("user_deposit_log")
public class UserDepositLog {

    public static final Integer PAY_TYPE_FUND = 0;//余额支付（暂不支持）
    public static final Integer PAY_TYPE_ALIPY = 1;//支付宝支付
    public static final Integer PAY_TYEP_WECHAT = 2;//微信支付

    public static final Integer TYPE_PAY = 1;//押金缴纳
    public static final Integer TYEP_REFUND = 2;//押金退还

    private Integer amount;//金额 单位（分）
    private Date createTime;
    private Long userId;
    private Integer payType;//'支付类型 0：余额（暂不支持） 1：微信  2：支付宝',
    private String payNumber;//押金单号
    private String tradeNumber;//交易流水号
    private Integer type;//1.押金缴纳 2.押金退还

    @Id(KeyGeneratorType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPayNumber() {
        return payNumber;
    }

    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber;
    }

    public String getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(String tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
