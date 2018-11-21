package cn.com.izj.dao;

import cn.com.izj.base.entity.pay.UserFund;
import cn.com.izj.dto.ApplicationCash;
import cn.com.izj.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: 朱鸿平
 * @date: 2018/6/25 22:01
 */
public interface UserFundDao extends BaseMapper<UserFund, Long> {

    /**
     * 获取用户余额 加锁
     *
     * @param userId 用户id
     */
    UserFund selectUserFundByXLock(Long userId);

    /**
     * 更新车主收益
     *
     * @param carOwnerId 车主id
     */
    void updateCarIncome(@Param("carOwnerId") Long carOwnerId, @Param("carIncome") Integer carIncome);

    /**
     * 获取用户余额 不加锁
     */
    UserFund selectUserFundByUserId(Long userId);

    /**
     * 查询用户可用优惠券数量
     *
     * @param userId 用户id
     */
    Integer getUserPreferentialAmount(Long userId);

    /**
     * 提现申请列表
     */
    List<ApplicationCash> getWithdrawList();

    /**
     * 根据提现单号查询提现信息
     *
     * @param tradeNumber 提现单号
     */
    ApplicationCash getWithdrawInfo(String tradeNumber);

    /**
     * 更新提现信息
     *
     * @param info 提现实体
     */
    void updateWithdraw(ApplicationCash info);
}
