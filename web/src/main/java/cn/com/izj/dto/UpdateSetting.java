package cn.com.izj.dto;

/**
 * @author: 朱鸿平
 * @date: 2018/7/15 12:12
 */
public class UpdateSetting {

    private Long id;//变量id
    private String filedValue;//变量值
    private String fieldDescribe;//变量描述
    private Long modifyUser;//操作人id

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFiledValue() {
        return filedValue;
    }

    public void setFiledValue(String filedValue) {
        this.filedValue = filedValue;
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
}
