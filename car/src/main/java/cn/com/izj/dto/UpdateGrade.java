package cn.com.izj.dto;

/**
 * @author: 朱鸿平
 * @date: 2018/6/10 18:30
 */
public class UpdateGrade {

    private Long carId;//车辆id
    private Integer carGrade;//车辆等级

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Integer getCarGrade() {
        return carGrade;
    }

    public void setCarGrade(Integer carGrade) {
        this.carGrade = carGrade;
    }
}
