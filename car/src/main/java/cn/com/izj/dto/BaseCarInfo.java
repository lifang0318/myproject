package cn.com.izj.dto;

/**
 * @author: 朱鸿平
 * @date: 2018/7/7 17:31
 */
public class BaseCarInfo {

    private Long id;//车辆id
    private String plateNumber;//车牌号
    private String brand;//品牌
    private Integer power;//动力来源 0燃油 1电动
    private Integer seatNumber;//座位数
    private String carColor;//车辆颜色
    private Integer carType;//车辆类型
    private Integer carState;//车辆状态

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public Integer getCarType() {
        return carType;
    }

    public void setCarType(Integer carType) {
        this.carType = carType;
    }

    public Integer getCarState() {
        return carState;
    }

    public void setCarState(Integer carState) {
        this.carState = carState;
    }
}
