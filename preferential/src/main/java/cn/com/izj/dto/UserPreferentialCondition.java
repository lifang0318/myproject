package cn.com.izj.dto;

/**
 * @author: lifang
 * @description: 用户优惠券查询条件
 * @date: Created in 2018/7/14 0:15
 * @version:
 */
public class UserPreferentialCondition {

    private Long id;//用户优惠券id
    private Long userId;//用户id
    private Integer state;//用户优惠券状态

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
