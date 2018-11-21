package cn.com.izj.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GouBo on 2018/6/9 1:34.
 */
public class ResourceInfo {

    /**
     * 资源ID
     */
    private Long id;

    private Long parentId;
    /**
     * 资源名
     */
    private String name;

    /**
     * 资源链接
     */
    private String link;

    /**
     * 子节点
     */
    private List<ResourceInfo> children = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

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

    public List<ResourceInfo> getChildren() {
        return children;
    }

    public void setChildren(List<ResourceInfo> children) {
        this.children = children;
    }
}
