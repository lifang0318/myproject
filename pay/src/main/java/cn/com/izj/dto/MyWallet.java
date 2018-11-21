package cn.com.izj.dto;

/**
 * @author: 朱鸿平
 * @date: 2018/7/1 10:18
 */
public class MyWallet {
    private Long userId;//用户id
    private Integer userBalance;//余额
    private Integer giveBalance;//赠送余额
    private Integer preferentialAmount;//用户优惠券张数
    private Integer carIncome;//车主收益

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(Integer userBalance) {
        this.userBalance = userBalance;
    }

    public Integer getGiveBalance() {
        return giveBalance;
    }

    public void setGiveBalance(Integer giveBalance) {
        this.giveBalance = giveBalance;
    }

    public Integer getPreferentialAmount() {
        return preferentialAmount;
    }

    public void setPreferentialAmount(Integer preferentialAmount) {
        this.preferentialAmount = preferentialAmount;
    }

    public Integer getCarIncome() {
        return carIncome;
    }

    public void setCarIncome(Integer carIncome) {
        this.carIncome = carIncome;
    }
}
