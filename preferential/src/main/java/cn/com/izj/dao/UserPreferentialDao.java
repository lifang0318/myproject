package cn.com.izj.dao;

import cn.com.izj.base.entity.preferential.UserPreferential;
import cn.com.izj.dto.UserPreferentialCondition;
import cn.com.izj.entity.User;
import cn.com.izj.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author: lifang
 * @description: 用户优惠券
 * @date: Created in 2018/7/13 21:27
 * @version:
 */
@Transactional(rollbackFor = Exception.class)
public interface UserPreferentialDao extends BaseMapper<UserPreferential, Long> {

    /**
     * 批量插入用户优惠券
     *
     * @param userPreferentials
     */
    void batchInsertUserPreferential(@Param("userPreferentials") List<UserPreferential> userPreferentials);

    /**
     * 按条件查询用户优惠券
     *
     * @param condition
     * @return
     */
    List<Map<String,String>> findByCondition(UserPreferentialCondition condition);

    /**
     * 获取用户可使用的优惠券
     *
     * @param condition
     * @return
     */
    List<Map<String,String>> findUnUsePreferential(UserPreferentialCondition condition);

    /**
     * 获取用户优惠券价值 单位分
     *
     * @param condition
     */
    Integer getUserPreferential(UserPreferentialCondition condition);

    /**
     * 添加用户认证信息
     */
    void insertUserAuth(Long userId);

    /**
     * 添加用户余额信息
     * @param id  用户id
     */
    void insertUserFund(Long id);

    /**
     * 获取用户推荐人
     * @param userId
     * @return
     */
    User getUserReferral(Long userId);

    /**
     * 更新优惠券状态为已使用
     */
    void updatePreferentialState(Long id);
}
