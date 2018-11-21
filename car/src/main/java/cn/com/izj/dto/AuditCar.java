package cn.com.izj.dto;

/**
 * 审核车辆
 *
 * @author: 朱鸿平
 * @date: 2018/6/20 20:33
 */
public class AuditCar {

    public static final Integer STATE_AUDIT_FAILURE = 0;//审核不通过
    public static final Integer STATE_AUDIT_PASS = 1;//审核通过

    private Long carId;//车辆id
    private Integer carGrade;//车辆等级
    private String deviceId;//车载设备id
    private String remarks;//备注
    private Integer state;//0不通过 1通过
    private Long auditorId;//审核者id

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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Long auditorId) {
        this.auditorId = auditorId;
    }
}
