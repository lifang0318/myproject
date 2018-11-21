package cn.com.izj.service;

import cn.com.izj.base.CONSTANT;
import cn.com.izj.base.entity.ConvertPageInfo;
import cn.com.izj.base.entity.PageResult;
import cn.com.izj.base.entity.UserAuth;
import cn.com.izj.base.entity.UserAuthInfo;
import cn.com.izj.base.enums.ResponseEnum;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.base.service.BaseService;
import cn.com.izj.dao.UserAuthDao;
import cn.com.izj.dto.AuditCardInfo;
import cn.com.izj.dto.DriverCardInfo;
import cn.com.izj.dto.IdCardInfo;
import cn.com.izj.dto.QueryCondition;
import cn.com.izj.repository.UserRepository;
import cn.com.izj.utils.JsonUtil;
import cn.com.izj.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: 朱鸿平
 * @date: 2018/8/12 17:50
 */
@Service
public class UserAuthService extends BaseService {

    @Autowired
    private UserAuthDao userAuthDao;
    @Autowired
    private AliSmsService aliSmsService;
    @Autowired
    private UserRepository userRepository;

    /**
     * 认证身份信息
     *
     * @param info 身份证信息
     */
    @Transactional
    public ApiResult addIdCardInfo(IdCardInfo info) {
        //判断身份证号码是否存在
        UserAuth userAuth = userAuthDao.getUserInfoByCardId(info.getIdentityCardNumber());
        if (userAuth != null) {
            return new ApiResult(ResponseEnum.USER_EXIST.getValue(), ResponseEnum.USER_EXIST.getDesc());
        }
        info.setRealNameAuthStatus(UserAuth.AUDIT_IN);
        if (!StringUtils.startsWithIgnoreCase(info.getHandCardPhoto(), "http")) {
            info.setHandCardPhoto(CONSTANT.PREFIX_URL.concat(info.getHandCardPhoto()));
        }
        if (!StringUtils.startsWithIgnoreCase(info.getIdentityCardPhotoFront(), "http")) {
            info.setIdentityCardPhotoFront(CONSTANT.PREFIX_URL.concat(info.getIdentityCardPhotoFront()));
        }
        if (!StringUtils.startsWithIgnoreCase(info.getIdentityCardPhotoBehind(), "http")) {
            info.setIdentityCardPhotoBehind(CONSTANT.PREFIX_URL.concat(info.getIdentityCardPhotoBehind()));
        }
        Integer result = userAuthDao.updateIdCardInfo(info);
        if (StringUtil.getInt(result) == 0) {
            log.error("更新用户认证信息失败，info:" + JsonUtil.beanToJson(info));
        }
        return ApiResult.validateSingleData(result);
    }

    /**
     * 认证驾驶信息
     *
     * @param info 驾驶证信息
     */
    @Transactional
    public ApiResult addDriverCardInfo(DriverCardInfo info) {
        if (!StringUtils.startsWithIgnoreCase(info.getDriverLicencePhotoMaster(), "http")) {
            info.setDriverLicencePhotoMaster(CONSTANT.PREFIX_URL.concat(info.getDriverLicencePhotoMaster()));
        }
        if (!StringUtils.startsWithIgnoreCase(info.getDriverLicencePhotoSlave(), "http")) {
            info.setDriverLicencePhotoSlave(CONSTANT.PREFIX_URL.concat(info.getDriverLicencePhotoSlave()));
        }
        Integer result = userAuthDao.updateDriverCardInfo(info);
        if (StringUtil.getInt(result) == 0) {
            log.error("更新用户驾驶认证信息失败，info:" + JsonUtil.beanToJson(info));
        }
        return ApiResult.validateSingleData(result);
    }

    /**
     * 判断用户认证信息是否为空
     *
     * @param userAuth
     * @param userId
     */
    private void validateUserAuth(UserAuth userAuth, Long userId) {
        if (userAuth == null) {
            userAuth = new UserAuth();
            userAuth.setUserId(userId);
            userAuth.setRealNameAuthStatus(UserAuth.AUDIT_NOT);
            userAuth.setDriverLicenceAuthStatus(UserAuth.AUDIT_NOT);
            userAuth.setCreateTime(new Date());
            userAuthDao.insert(userAuth);
        }
    }

    /**
     * 获取身份证信息
     *
     * @param userId 用户id
     */
    @Transactional
    public ApiResult getIdCardInfo(Long userId) {
        IdCardInfo info = userAuthDao.getIdCardInfo(userId);
        if (info == null) {
            info = new IdCardInfo();
        }
        return ApiResult.successWithData(info);
    }

    /**
     * 获取驾驶证信息
     *
     * @param userId 用户Id
     */
    @Transactional
    public ApiResult getDriverCardInfo(Long userId) {
        DriverCardInfo info = userAuthDao.getDriverCardInfo(userId);
        if (info == null) {
            info = new DriverCardInfo();
        }
        return ApiResult.successWithData(info);
    }

    /**
     * 审核身份证
     *
     * @param cardInfo
     */
    @Transactional
    public Integer auditIdCard(AuditCardInfo cardInfo) {
        UserAuth userAuth = new UserAuth();
        userAuth.setUserId(cardInfo.getUserId());
        userAuth.setAuditorId(cardInfo.getAuditorId());
        String username = userRepository.findUsernameById(cardInfo.getUserId());
        if (0 == StringUtil.getInt(cardInfo.getState())) {
            userAuth.setRealNameAuthStatus(UserAuth.AUDIT_FAIL);
            userAuth.setCardRemark(cardInfo.getRemark());
            aliSmsService.sendReviewIdentityFailed(StringUtil.getStr(username));
        } else {
            userAuth.setRealNameAuthStatus(UserAuth.AUDIT_PASS);
            userAuth.setCardRemark("");
        }
        return userAuthDao.updateCardInfo(userAuth);
    }

    /**
     * 审核驾驶证
     *
     * @param cardInfo
     */
    @Transactional
    public Integer auditDriverCard(AuditCardInfo cardInfo) {
        UserAuth userAuth = new UserAuth();
        userAuth.setUserId(cardInfo.getUserId());
        userAuth.setAuditorId(cardInfo.getAuditorId());
        String username = userRepository.findUsernameById(cardInfo.getUserId());
        if (0 == StringUtil.getInt(cardInfo.getState())) {//审核不通过
            userAuth.setDriverLicenceAuthStatus(UserAuth.AUDIT_FAIL);
            userAuth.setCardRemark(cardInfo.getRemark());
            aliSmsService.sendReviewDriversLicenseFailed(username);
        } else {
            userAuth.setDriverLicenceAuthStatus(UserAuth.AUDIT_PASS);
            userAuth.setDriverRemark("");
            aliSmsService.sendReviewSuccess(StringUtil.getStr(username));
        }
        return userAuthDao.updateCardInfo(userAuth);
    }

    /**
     * 获取用户信息（钱包，押金,认证）
     *
     * @param userId
     */
    @Transactional
    public ApiResult getUserInfo(Long userId) {
        UserAuth userAuth = userAuthDao.getUserAuthByUserId(userId);
        validateUserAuth(userAuth, userId);
        UserAuthInfo info = userAuthDao.getUserInfo(userId);
        if (info == null) {
            info = new UserAuthInfo();
        }
        return ApiResult.successWithData(info);
    }

    /**
     * 查询用户
     *
     * @param queryCondition
     * @return
     */
    public ApiResult getUserList(QueryCondition queryCondition) {
        PageHelper.startPage(queryCondition.getPageNumber(), queryCondition.getPageSize());
        List<UserAuthInfo> list = userAuthDao.getUserList(queryCondition);
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }
        PageInfo<UserAuthInfo> pageInfo = new PageInfo<>(list);
        ConvertPageInfo convert = new ConvertPageInfo();
        PageResult userInfo = convert.convertPageInfo(pageInfo);
        userInfo.setList(list);
        return ApiResult.successWithData(userInfo);
    }
}
