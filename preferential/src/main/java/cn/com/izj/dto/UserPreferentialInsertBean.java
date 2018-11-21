package cn.com.izj.dto;

import java.util.List;

/**
 * @author: lifang
 * @description: 发放优惠券参数
 * @date: Created in 2018/7/13 22:22
 * @version:
 */
public class UserPreferentialInsertBean {

    private String discription;//描述信息

    private List<Long> userIds;//所有发放的用户id

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }
}
