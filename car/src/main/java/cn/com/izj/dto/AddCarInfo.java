package cn.com.izj.dto;

/**
 * 添加车辆信息
 *
 * @author: 朱鸿平
 * @date: 2018/6/9 17:10
 */
public class AddCarInfo extends BaseCarInfo {

    private String remarks;//备注
    private Integer carGrade;//车辆等级
    private String deviceId;//车载设备id

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getCarGrade() {
        return carGrade;
    }

    public void setCarGrade(Integer carGrade) {
        this.carGrade = carGrade;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
