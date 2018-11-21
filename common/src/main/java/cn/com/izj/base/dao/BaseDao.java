package cn.com.izj.base.dao;

import java.util.List;

/**
 * @author: lifang
 * @description: 基础dao
 * @date: Created in 2018/6/6 0:42
 */
public interface BaseDao<T,C> {

    int save(T t) throws Exception;

    int update(T t) throws Exception;

    int delete(Long id) throws Exception;

    T query(Long id) throws Exception;

    List<T> queryList(C condition) throws Exception;
}
