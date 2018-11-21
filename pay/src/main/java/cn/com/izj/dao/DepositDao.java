package cn.com.izj.dao;

import cn.com.izj.base.entity.GlobalSetting;
import cn.com.izj.base.entity.pay.Deposit;
import cn.com.izj.mybatis.mapper.BaseMapper;

/**
 * @author: 朱鸿平
 * @date: 2018/8/16 23:40
 */
public interface DepositDao extends BaseMapper<Deposit, Long> {

    /**
     * 查询用户押金信息
     *
     * @param userId 用户id
     */
    Deposit getUserDeposit(Long userId);

    /**
     * 获取指定单号的押金信息
     * @param number
     * @return
     */
    Deposit getDepositByOrderNum(String number);

    /**
     * 获取押金金额配置信息
     */
    GlobalSetting getDepositSetting(Integer uniqueId);
}
