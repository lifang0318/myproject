package cn.com.izj.base.entity;

import cn.com.izj.mybatis.annotation.Table;

/**
 * @author: 朱鸿平
 * @date: 2018/6/6 22:03
 */
@Table("global_setting")
public class GlobalSetting extends BaseEntity {

    public static final Integer OPEN_STATE = 1;
    public static final Integer CLOSE_STATE = 0;

    private String fieldName;//变量名
    private String fieldValue;//变量值
    private Integer valueType;//变量值类型 0按比例计算 1按数值计算
    private Integer uniqueId;//变量类型 SettingTypeEnum
    private String fieldDescribe;//变量描述
    private Long modifyUser;//变更人id
    private Integer fieldState;//变量状态 0禁用 1启用

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Integer getValueType() {
        return valueType;
    }

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }

    public Integer getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Integer uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getFieldDescribe() {
        return fieldDescribe;
    }

    public void setFieldDescribe(String fieldDescribe) {
        this.fieldDescribe = fieldDescribe;
    }

    public Long getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Long modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Integer getFieldState() {
        return fieldState;
    }

    public void setFieldState(Integer fieldState) {
        this.fieldState = fieldState;
    }
}
