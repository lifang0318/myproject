package cn.com.izj.dao;

import cn.com.izj.base.entity.UserAuth;
import cn.com.izj.dto.DriverCardInfo;
import cn.com.izj.dto.IdCardInfo;
import cn.com.izj.base.entity.UserAuthInfo;
import cn.com.izj.dto.QueryCondition;
import cn.com.izj.mybatis.mapper.BaseMapper;

import java.util.List;

/**
 * @author: 朱鸿平
 * @date: 2018/8/12 17:48
 */
public interface UserAuthDao extends BaseMapper<UserAuth, Long> {

    /**
     * 上传身份证信息
     *
     * @param info 身份证信息
     */
    Integer updateIdCardInfo(IdCardInfo info);

    /**
     * 上传驾驶证信息
     *
     * @param info 驾驶证信息
     */
    Integer updateDriverCardInfo(DriverCardInfo info);

    /**
     * 获取身份证信息
     *
     * @param userId
     */
    IdCardInfo getIdCardInfo(Long userId);

    /**
     * 获取驾驶证信息
     *
     * @param userId
     */
    DriverCardInfo getDriverCardInfo(Long userId);

    /**
     * 审核证件信息
     */
    Integer updateCardInfo(UserAuth userAuth);

    /**
     * 根据userId获取用户认证信息
     *
     * @param userId
     * @return
     */
    UserAuth getUserAuthByUserId(Long userId);

    /**
     * 获取用户信息
     *
     * @param userId 用户Id
     */
    UserAuthInfo getUserInfo(Long userId);

    /**
     * 根据身份证号码查询用户
     */
    UserAuth getUserInfoByCardId(String cardId);

    /**
     * 获取用户列表
     * @param queryCondition
     * @return
     */
    List<UserAuthInfo> getUserList(QueryCondition queryCondition);
}
