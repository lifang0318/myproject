package cn.com.izj.dto;

/**
 * @author: 朱鸿平
 * @date: 2018/7/7 17:43
 */
public class MyCarInfo extends ShowCarInfo {

    private Integer carGrade;//车辆等级
    private Integer sumAmount;//车辆收益总和

    public Integer getCarGrade() {
        return carGrade;
    }

    public void setCarGrade(Integer carGrade) {
        this.carGrade = carGrade;
    }

    public Integer getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(Integer sumAmount) {
        this.sumAmount = sumAmount;
    }
}
