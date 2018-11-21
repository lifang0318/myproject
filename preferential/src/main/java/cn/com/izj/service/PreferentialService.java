package cn.com.izj.service;

import cn.com.izj.base.entity.preferential.Preferential;
import cn.com.izj.base.entity.preferential.UserPreferential;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.dao.PreferentialDao;
import cn.com.izj.dao.UserPreferentialDao;
import cn.com.izj.dto.PreferentialCondition;
import cn.com.izj.dto.UserPreferentialInsertBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: lifang
 * @description:优惠券-service
 * @date: Created in 2018/7/11 23:06
 * @version:
 */
@Service
public class PreferentialService {

    @Autowired
    private PreferentialDao preferentialDao;

    @Autowired
    private UserPreferentialDao userPreferentialDao;

    /**
     * 录入优惠券
     *
     * @param preferential
     * @return
     */
    @Transactional
    public int addPreferential(Preferential preferential) {
        return this.preferentialDao.insert(preferential);
    }

    /**
     * 修改优惠券
     *
     * @param preferential
     * @return
     */
    @Transactional
    public int updatePreferential(Preferential preferential) {
        return this.preferentialDao.updateById(preferential);
    }

    /**
     * 更新优惠券前，验证是否已被发放过
     *
     * @param preferentialId
     * @return
     */
    public Boolean isDistribute(Long preferentialId) {
        Preferential preferential = this.preferentialDao.findById(preferentialId);
        if (preferential.getState() == Preferential.STATE_DISTRIBUTE) {
            return false;
        }
        return true;
    }

    /**
     * 发放优惠券
     *
     * @param preferentialId
     * @param userPreferentialInsertBean
     * @return
     */
    @Transactional
    public ApiResult distributePreferential(Long preferentialId, UserPreferentialInsertBean userPreferentialInsertBean) {
        List<UserPreferential> userPreferentials = new ArrayList<>();
        for (Long userId : userPreferentialInsertBean.getUserIds()) {
            UserPreferential userPreferential = new UserPreferential();
            userPreferential.setUserId(userId);
            userPreferential.setPreferentialId(preferentialId);
            userPreferential.setState(UserPreferential.STATE_UNUSE);
            userPreferential.setDiscription(userPreferentialInsertBean.getDiscription());
            userPreferential.setCreateTime(new Date());
            userPreferentials.add(userPreferential);
        }
        this.userPreferentialDao.batchInsertUserPreferential(userPreferentials);
        Preferential preferential = this.preferentialDao.findById(preferentialId);
        if (preferential.getState() == Preferential.STATE_UNDISTRIBUTE) {
            //修改对应优惠券状态
            preferential.setState(Preferential.STATE_DISTRIBUTE);
            this.preferentialDao.updateById(preferential);
        }
        return new ApiResult();
    }

    /**
     * 获取满足查询条件的优惠券信息
     *
     * @param preferentialCondition
     * @return
     */
    public ApiResult findByCondition(PreferentialCondition preferentialCondition) {
        ApiResult apiResult = new ApiResult();
        List<Preferential> preferentials = this.preferentialDao.findByCondition(preferentialCondition);
        apiResult.setData(CollectionUtils.isEmpty(preferentials) ? new ArrayList<>() : preferentials);
        return apiResult;
    }
}
