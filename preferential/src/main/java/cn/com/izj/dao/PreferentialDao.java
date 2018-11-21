package cn.com.izj.dao;

import cn.com.izj.base.entity.preferential.Preferential;
import cn.com.izj.dto.PreferentialCondition;
import cn.com.izj.mybatis.mapper.BaseMapper;

import java.util.List;

/**
 * @author: lifang
 * @description:优惠券-dao
 * @date: Created in 2018/7/11 23:05
 * @version:
 */
public interface PreferentialDao extends BaseMapper<Preferential,Long>{

    /**
     * 查询所有的优惠券
     *
     */
    List<Preferential> findByCondition(PreferentialCondition condition);

    /**
     * 查询是否有优惠券
     * @param type 优惠券类型
     */
    List<Preferential> findRegisterPreferential(Integer type);
}
