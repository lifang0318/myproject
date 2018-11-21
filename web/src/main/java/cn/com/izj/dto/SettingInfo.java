package cn.com.izj.dto;

/**
 * @author: 朱鸿平
 * @date: 2018/7/15 12:08
 */
public class SettingInfo {

    private Long id;//变量id
    private String fieldName;//变量名
    private String fieldValue;//变量值
    private Integer fieldType;//变量类型
    private Integer uniqueId;//变量唯一标识
    private String fieldDescribe;//变量描述
    private Integer fieldState;//变量状态 0禁用 1启用

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getFieldType() {
        return fieldType;
    }

    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
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

    public Integer getFieldState() {
        return fieldState;
    }

    public void setFieldState(Integer fieldState) {
        this.fieldState = fieldState;
    }
}
