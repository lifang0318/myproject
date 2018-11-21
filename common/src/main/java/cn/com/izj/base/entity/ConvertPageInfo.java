package cn.com.izj.base.entity;

import com.github.pagehelper.PageInfo;

/**
 * @author: 朱鸿平
 * @date: 2018/8/27 22:49
 */
public class ConvertPageInfo {

    public PageResult convertPageInfo(PageInfo pageInfo) {
        PageResult result = new PageResult();
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setPages(pageInfo.getPages());
        result.setTotal(pageInfo.getTotal());
        return result;
    }

}
