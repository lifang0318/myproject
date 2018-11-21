package cn.com.izj.service;

import cn.com.izj.base.entity.preferential.Preferential;
import cn.com.izj.base.entity.preferential.UserPreferential;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.dao.UserPreferentialDao;
import cn.com.izj.dto.UserPreferentialCondition;
import cn.com.izj.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: lifang
 * @description:用户优惠券-service
 * @date: Created in 2018/7/14 0:13
 * @version:
 */
@Service
public class UserPreferentialService {

    @Autowired
    private UserPreferentialDao userPreferentialDao;

    /**
     * 查询所有满足条件的用户优惠券
     *
     * @param condition
     * @return
     */
    public ApiResult findByCondition(UserPreferentialCondition condition) {
        ApiResult result = new ApiResult();
        List<Map<String, String>> preferentials = userPreferentialDao.findByCondition(condition);
        result.setData(CollectionUtils.isEmpty(preferentials) ? null : preferentials);
        return result;

    }

    /**
     * 获取用户所有可用优惠券
     *
     * @param condition
     * @return
     */
    public ApiResult findUnUsePreferential(UserPreferentialCondition condition) {
        ApiResult result = new ApiResult();
        List<Map<String, String>> preferentials = this.userPreferentialDao.findUnUsePreferential(condition);
        result.setData(CollectionUtils.isEmpty(preferentials) ? null : preferentials);
        return result;
    }

    /**
     * 赠送优惠券
     *
     * @param userId 用户Id
     * @param list   优惠券列表
     */
    @Transactional
    public void insertPreferential(Long userId, List<Preferential> list) {
        if (!CollectionUtils.isEmpty(list)) {
            for (Preferential preferential : list) {
                //10元优惠券Id列表
                List<Long> idList = new ArrayList<>();
                idList.add(8L);
                idList.add(9L);
                idList.add(10L);
                idList.add(11L);
                idList.add(12L);
                //TODO 此处当前版本直接写死，后续需要重构优惠券模块 发放12张5元无门槛优惠券
                if (preferential.getType() == 0 && preferential.getId() == 7
                        && preferential.getPreferentialAmount() == 500
                        && preferential.getForbiddenState() == 1) {
                    for (int i = 0; i < 12; i++) {
                        insertUserPreferential(userId, preferential);
                    }
                    //TODO 发放10张10元无门槛优惠券,每周两张
                } else if (preferential.getType() == 0 && idList.contains(preferential.getId())
                        && preferential.getForbiddenState() == 1
                        && preferential.getPreferentialAmount() == 1000) {
                    for (int i = 0; i < 2; i++) {
                        insertUserPreferential(userId, preferential);
                    }
                } else {
                    insertUserPreferential(userId, preferential);
                }
            }
        }
    }

    private void insertUserPreferential(Long userId, Preferential preferential) {
        UserPreferential userPreferential = new UserPreferential();
        userPreferential.setUserId(userId);
        userPreferential.setPreferentialId(preferential.getId());
        userPreferential.setState(UserPreferential.STATE_UNUSE);
        userPreferential.setCreateTime(new Date());
        String dateStr = DateUtil.formatShortDate(new Date());
        String date = DateUtil.shortDateAddDay(dateStr, preferential.getEffectiveDay()-1);
        userPreferential.setDistributeTime(DateUtil.parseShortDate(date));
        switch (preferential.getType()) {
            case 0:
                userPreferential.setDiscription("注册赠送优惠券");
                break;
            case 1:
                userPreferential.setDiscription("赠送活动优惠券");
                break;
            case 2:
                userPreferential.setDiscription("邀请好友赠送优惠券");
                break;
            default:
                userPreferential.setDiscription("其他优惠券");
                break;
        }
        userPreferentialDao.insert(userPreferential);
    }
}
