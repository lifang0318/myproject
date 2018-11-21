package cn.com.izj.service;

import cn.com.izj.base.entity.pay.Recharge;
import cn.com.izj.base.entity.pay.UserFund;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.base.service.BaseService;
import cn.com.izj.dao.RechargeDao;
import cn.com.izj.dao.UserFundDao;
import cn.com.izj.dto.RechargeFindCondition;
import cn.com.izj.utils.OrderUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户充值
 *
 * @author lifang
 */
@Service
public class RechargeService extends BaseService {

    @Autowired
    private RechargeDao rechargeDao;

    @Autowired
    private UserFundDao userFundDao;

    /**
     * 创建充值记录
     *
     * @param recharge
     * @return
     */
    @Transactional
    public ApiResult createRecharge(Recharge recharge) {
        ApiResult apiResult = new ApiResult();
        recharge.setCreateTime(new Date());
        recharge.setUpdateTime(new Date());
        recharge.setState(Recharge.STATE_WAITINT_PAY);
        recharge.setNumber(OrderUtil.makeOrderNum());
        //获取userFundId
        UserFund userFund = this.userFundDao.selectUserFundByUserId(recharge.getUserId());
        recharge.setUserFundId(userFund.getId());
        this.rechargeDao.insert(recharge);
        Recharge rOrder = this.rechargeDao.findById(recharge.getId());
        apiResult.setData(rOrder);
        return apiResult;
    }

    /**
     * 获取用户充值记录
     *
     * @param userId
     * @return
     */
    public ApiResult getRechargesByUserId(Long userId) {
        ApiResult apiResult = new ApiResult();
        List<Recharge> recharges = this.rechargeDao.getRechargesByUserId(userId);
        apiResult.setData(recharges);
        return apiResult;
    }

    /**
     * 获取充值记录
     *
     * @param condition
     * @return
     */
    public ApiResult findRecharges(RechargeFindCondition condition) {
        ApiResult apiResult = new ApiResult();
        List<Recharge> recharges = this.rechargeDao.findRechargesByCondition(condition);
        apiResult.setData(CollectionUtils.isEmpty(recharges) ? new ArrayList<Recharge>() : recharges);
        return apiResult;
    }
}
