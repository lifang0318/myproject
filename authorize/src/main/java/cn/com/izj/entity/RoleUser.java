package cn.com.izj.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 角色用户关系表
 * Created by GouBo on 2018/6/9 1:34.
 */
@Entity
public class RoleUser extends BaseEntity {

    /**
     * 角色
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;
    /**
     * 用户
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
