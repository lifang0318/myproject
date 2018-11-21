package cn.com.izj.dto;

/**
 * @author: lifang
 * @description: 优惠券查询条件
 * @date: Created in 2018/7/13 22:45
 * @version:
 */
public class PreferentialCondition {

    private String name;//优惠券名称
    private Integer type;//优惠券类型
    private Integer state;//优惠券状态
    private Integer forbiddenState;
    private String startTime;//创建时间最小值
    private String endTime;//创建时间最大值

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getForbiddenState() {
        return forbiddenState;
    }

    public void setForbiddenState(Integer forbiddenState) {
        this.forbiddenState = forbiddenState;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
