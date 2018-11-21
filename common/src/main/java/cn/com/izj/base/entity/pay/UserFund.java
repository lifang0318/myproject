package cn.com.izj.base.entity.pay;

import cn.com.izj.base.entity.BaseEntity;
import cn.com.izj.mybatis.annotation.Table;

/**
 * 用户余额信息
 *
 * @author: 朱鸿平
 * @date: 2018/6/6 21:32
 */
@Table("user_fund")
public class UserFund extends BaseEntity {

    private Long userId;//用户id
    private Integer balance;//余额
    private Integer giveBalance;//赠送金额
    private Integer carIncome;//车主收益
    private String realName;//真实姓名
    private Long cardNumber;//银行卡号码

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getGiveBalance() {
        return giveBalance;
    }

    public void setGiveBalance(Integer giveBalance) {
        this.giveBalance = giveBalance;
    }

    public Integer getCarIncome() {
        return carIncome;
    }

    public void setCarIncome(Integer carIncome) {
        this.carIncome = carIncome;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }
}
