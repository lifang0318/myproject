package cn.com.izj.dto;

/**
 * Created by GouBo on 2018/8/26 21:03.
 */
public class QueryCarCondition {

    private String plateNumber;//车牌号
    private Integer powerType;//0燃油 1电动
    private Integer carGrade;//车辆等级
    private Integer carState;//车辆状态 0审核中 1审核失败 2空闲 3审核通过 4使用中 5已下线 6已预约
    private Integer pageNumber;//第几页
    private Integer pageSize;//每页多少条

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Integer getPowerType() {
        return powerType;
    }

    public void setPowerType(Integer powerType) {
        this.powerType = powerType;
    }

    public Integer getCarGrade() {
        return carGrade;
    }

    public void setCarGrade(Integer carGrade) {
        this.carGrade = carGrade;
    }

    public Integer getCarState() {
        return carState;
    }

    public void setCarState(Integer carState) {
        this.carState = carState;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
