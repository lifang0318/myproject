package cn.com.izj.base.entity;

/**
 * @author: 朱鸿平
 * @date: 2018/8/22 23:46
 */
public class PageResult {

    private int pageNum;//当前第几页
    private int pageSize;//每页数量
    private long total;//总共多少条
    private int pages;//总共多少页
    private Object list;//返回实体

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Object getList() {
        return list;
    }

    public void setList(Object list) {
        this.list = list;
    }
}
