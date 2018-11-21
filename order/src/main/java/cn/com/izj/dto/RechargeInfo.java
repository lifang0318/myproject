package cn.com.izj.dto;

/**
 * @author: 朱鸿平
 * @date: 2018/8/22 0:53
 */
public class RechargeInfo {

    private Double rechargeRate;//赠送比例
    private Integer maxPresent;//最大赠送额度

    public Double getRechargeRate() {
        return rechargeRate;
    }

    public void setRechargeRate(Double rechargeRate) {
        this.rechargeRate = rechargeRate;
    }

    public Integer getMaxPresent() {
        return maxPresent;
    }

    public void setMaxPresent(Integer maxPresent) {
        this.maxPresent = maxPresent;
    }
}
