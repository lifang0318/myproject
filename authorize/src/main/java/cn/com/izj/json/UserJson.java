package cn.com.izj.json;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by GouBo on 2018/7/7 15:41.
 */
public class UserJson {

    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 拥有的角色id
     */
    private Set<Long> rolesId = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Set<Long> getRolesId() {
        return rolesId;
    }

    public void setRolesId(Set<Long> rolesId) {
        this.rolesId = rolesId;
    }
}
