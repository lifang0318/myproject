package cn.com.izj.dao;

import cn.com.izj.base.entity.GlobalSetting;
import cn.com.izj.base.entity.UserAuth;
import cn.com.izj.base.entity.UserAuthInfo;
import cn.com.izj.condition.TripOrderFindCondition;
import cn.com.izj.dto.DestinationPark;
import cn.com.izj.base.entity.order.TripOrder;
import cn.com.izj.dto.TripOrderResultDto;
import cn.com.izj.dto.UserInfo;
import cn.com.izj.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author: lifang
 * @description:行程订单-dao
 * @date: Created in 2018/6/10 16:33
 * @version:
 */
public interface TripOrderDao extends BaseMapper<TripOrder, Long> {

    /**
     * 根据条件查询订单集合
     *
     * @param condition 查询条件
     * @return
     */
    List<TripOrderResultDto> findTripOrdersByCondition(TripOrderFindCondition condition);

    /**
     * 根据用户id查询未完成订单id
     *
     * @param userId
     */
    String queryUnfinishedOrder(@Param("userId") Long userId);

    /**
     * 根据订单号查询订单详情
     *
     * @param orderNum 订单号
     * @return
     */
    TripOrderResultDto getTripOrderByNum(@Param("orderNum") String orderNum);

    /**
     * 更新终点停车场
     *
     * @param park 更新信息
     */
    Integer updateDestinationPark(DestinationPark park);

    /**
     * 根据订单号，更新订单信息
     *
     * @param tripOrder
     * @return
     */
    Integer updateByOrderNum(TripOrder tripOrder);

    /**
     * 获取配置信息
     *
     * @param uniqueId 配置标识
     */
    GlobalSetting getSettingInfo(Integer uniqueId);

    /**
     * 根据id获取折扣信息
     *
     * @param discountId
     */
    GlobalSetting getDiscountInfoById(Long discountId);

    /**
     * 根据时间区间查询订单
     *
     * @param userId
     * @param start
     * @param end
     * @return
     */
    List<TripOrderResultDto> queryOrdersByTimeRange(@Param("userId") Long userId, @Param("start") Date start, @Param("end") Date end);

    /**
     * 根据车牌号获取订单（模糊匹配）
     *
     * @param plateNumber
     * @return
     */
    List<TripOrderResultDto> queryOrdersByPlateNum(String plateNumber);

    /**
     * 获取用户信息
     *
     * @param userId 用户Id
     */
    UserAuthInfo getUserInfo(Long userId);

    /**
     * 用户30内最近的订单号
     *
     * @param userId 用户id
     */
    String queryUserOrder(Long userId);
}
