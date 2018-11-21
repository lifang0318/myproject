package cn.com.izj.entity;

import cn.com.izj.dto.ResourceInfo;
import cn.com.izj.properties.AuthorizeConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 需要控制权限的资源，以业务人员能看懂的name呈现.实际关联到一个或多个url上。
 * 树形结构。
 * Created by GouBo on 2018/6/9 1:34.
 */
@Entity
public class Resource extends BaseEntity {

    /**
     * 资源名称，如xx菜单，xx按钮
     */
    private String name;

    /**
     * 资源链接
     */
    private String link;

    /**
     * 实际需要控制权限的url
     */
    private String url;
    /**
     * 父资源
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private Resource parent;
    /**
     * 子资源
     */
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    @OrderBy("sort ASC")
    private List<Resource> childs = new ArrayList<>();

    public ResourceInfo toTree(User user) {
        ResourceInfo result = new ResourceInfo();
        BeanUtils.copyProperties(this, result);
        result.setParentId(this.parent != null ? this.parent.getId() : null);

        Set<Long> resourceIds = user.getAllResourceIds();

        List<ResourceInfo> children = new ArrayList<>();
        for (Resource child : getChilds()) {
            if ((StringUtils.equals(user.getUsername(), AuthorizeConstants.SUPER_ADMIN) ||
                    resourceIds.contains(child.getId()))) {
                children.add(child.toTree(user));
            }
        }
        result.setChildren(children);
        return result;
    }

    public void addChild(Resource child) {
        childs.add(child);
        child.setParent(this);
    }

    /**
     * 序号
     */
    private int sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Resource getParent() {
        return parent;
    }

    public void setParent(Resource parent) {
        this.parent = parent;
    }

    public List<Resource> getChilds() {
        return childs;
    }

    public void setChilds(List<Resource> childs) {
        this.childs = childs;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
