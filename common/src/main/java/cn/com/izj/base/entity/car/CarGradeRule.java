package cn.com.izj.base.entity.car;

import cn.com.izj.base.entity.BaseEntity;
import cn.com.izj.mybatis.annotation.Table;

/**
 * 车辆价格规则
 *
 * @author: 朱鸿平
 * @date: 2018/6/6 20:30
 */
@Table("car_grade_role")
public class CarGradeRule extends BaseEntity {

    private Integer carGrade;//车辆等级
    private Integer mileagePrice;//里程价格 分/公里
    private Integer timeFee;//计时价格 分/分钟

    public Integer getCarGrade() {
        return carGrade;
    }

    public void setCarGrade(Integer carGrade) {
        this.carGrade = carGrade;
    }

    public Integer getMileagePrice() {
        return mileagePrice;
    }

    public void setMileagePrice(Integer mileagePrice) {
        this.mileagePrice = mileagePrice;
    }

    public Integer getTimeFee() {
        return timeFee;
    }

    public void settimeFee(Integer timeFee) {
        this.timeFee = timeFee;
    }
}
