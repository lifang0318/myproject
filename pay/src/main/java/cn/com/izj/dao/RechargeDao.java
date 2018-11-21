package cn.com.izj.dao;

import cn.com.izj.base.entity.order.TripOrder;
import cn.com.izj.base.entity.pay.Recharge;
import cn.com.izj.dto.RechargeFindCondition;
import cn.com.izj.mybatis.mapper.BaseMapper;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 用户充值
 *
 * @author lifang
 */
public interface RechargeDao extends BaseMapper<Recharge, Long> {

    /**
     * 获取用户充值记录
     *
     * @param userId
     * @return
     */
    List<Recharge> getRechargesByUserId(@RequestParam("userId") Long userId);

    /**
     * 根据查询条件，获取充值记录
     *
     * @param condition
     * @return
     */
    List<Recharge> findRechargesByCondition(RechargeFindCondition condition);

    /**
     * 根据充值单号获取充值订单信息
     *
     * @param orderNum
     * @return
     */
    Recharge getRechargeByNum(@RequestParam("orderNum") String orderNum);
}
