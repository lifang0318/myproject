package cn.com.izj.base.entity.preferential;

import cn.com.izj.base.entity.BaseEntity;
import cn.com.izj.mybatis.annotation.Table;

/**
 * @author: lifang
 * @description: 优惠券
 * @date: Created in 2018/7/11 23:17
 * @version:
 */
@Table("preferential")
public class Preferential extends BaseEntity {

     /** 注册优惠券 */
    public static final Integer TYPE_REGISTER = 0;
     /** 活动优惠券 */
    public static final Integer TYPE_ACTIVITY = 1;

    public static final Integer TYPE_INVITE = 2;

    /** 其他优惠券 */
    public static final Integer TYPE_OTHER = 9;

    /** 未发放 */
    public static Integer STATE_UNDISTRIBUTE = 0;
    /** 已发放 */
    public static Integer STATE_DISTRIBUTE = 1;

    private String name;//优惠券名称
    private Integer type;//优惠券类型
    private Integer preferentialAmount;//优惠金额
    private Integer validity;//有效日期（单位天）
    private Integer state;//优惠券状态（默认初始状态为未发放，当执行过一次发放后，状态变为已发放，该条优惠券信息将不能再编辑）
    private Integer forbiddenState;//0禁用 1启用
    private String creator;//创建人
    private String updator;//修改人
    private Integer effectiveDay;//距当前多少天生效

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

    public Integer getPreferentialAmount() {
        return preferentialAmount;
    }

    public void setPreferentialAmount(Integer preferentialAmount) {
        this.preferentialAmount = preferentialAmount;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public Integer getEffectiveDay() {
        return effectiveDay;
    }

    public void setEffectiveDay(Integer effectiveDay) {
        this.effectiveDay = effectiveDay;
    }
}
