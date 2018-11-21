package cn.com.izj.service;

import cn.com.izj.base.entity.GlobalSetting;
import cn.com.izj.base.entity.pay.Deposit;
import cn.com.izj.base.enums.SettingTypeEnum;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.dao.DepositDao;
import cn.com.izj.dao.TripOrderDao;
import cn.com.izj.dto.DepositInfo;
import cn.com.izj.utils.OrderUtil;
import cn.com.izj.utils.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author: 朱鸿平
 * @date: 2018/8/16 23:39
 */
@Service
public class DepositService {

    @Autowired
    private DepositDao depositDao;
    @Autowired
    private TripOrderDao tripOrderDao;

    /**
     * 查询用户押金信息
     *
     * @param userId 用户id
     */
    public ApiResult getUserDeposit(Long userId) {
        Deposit deposit = depositDao.getUserDeposit(userId);
        DepositInfo info = new DepositInfo();
        if (deposit != null) {
            BeanUtils.copyProperties(deposit, info);
        }
        return ApiResult.successWithData(info);
    }

    /**
     * 创建缴纳押金订单
     *
     * @param userId
     */
    @Transactional
    public ApiResult createDeposit(Long userId) {
        //获取配置押金金额
        GlobalSetting setting = depositDao.getDepositSetting(SettingTypeEnum.DEPOSIT_AMOUNT.getValue());
        if (setting == null) {
            return ApiResult.errorWithData("");
        }
        Deposit depositOrder = this.depositDao.getUserDeposit(userId);
        if (depositOrder == null) {
            depositOrder = new Deposit();
            depositOrder.setUserId(userId);
            depositOrder.setPayState(Deposit.STATE_WAITING_PAY);
            depositOrder.setType(Deposit.DEPOSIT_CASH);
            depositOrder.setOrderNum(OrderUtil.makeOrderNum());
            depositOrder.setAmount(StringUtil.getInt(setting.getFieldValue()));
            depositOrder.setCreateTime(new Date());
            this.depositDao.insert(depositOrder);
        } else {
            depositOrder.setOrderNum(OrderUtil.makeOrderNum());
            depositOrder.setAmount(StringUtil.getInt(setting.getFieldValue()));
            depositOrder.setCreateTime(new Date());
            depositOrder.setPayState(Deposit.STATE_WAITING_PAY);
            //第一版默认押金为现金
            depositOrder.setType(Deposit.DEPOSIT_CASH);
            this.depositDao.updateById(depositOrder);
        }
        return ApiResult.successWithData(depositOrder.getOrderNum());
    }

    /**
     * 申请退押金 无用接口
     */
    @Deprecated
    @Transactional
    public ApiResult refundDeposit(Long userId) {
        //查询用户是否缴纳押金
        Deposit depositOrder = this.depositDao.getUserDeposit(userId);
        String orderNum = "";
        if (depositOrder != null && depositOrder.getPayState().equals(Deposit.STATE_PAYED)) {
            //用户最近30天无订单才能退押金
            orderNum = tripOrderDao.queryUserOrder(userId);
            if (StringUtil.isNotEmpty(orderNum)) {
                return ApiResult.errorWithData(orderNum);
            }
            orderNum = tripOrderDao.queryUnfinishedOrder(userId);
            //有未完成订单,无法退押金
            if (StringUtil.isEmpty(orderNum)) {
                depositOrder.setType(Deposit.STATE_REFUNDING);
                depositOrder.setUpdateTime(new Date());
                depositDao.updateById(depositOrder);
                return ApiResult.successWithData(StringUtil.getStr(depositOrder.getOrderNum()));
            }
        }
        return ApiResult.errorWithData(orderNum);
    }

    /**
     * 获取押金金额
     */
    public ApiResult getDepositAmount() {
        GlobalSetting setting = tripOrderDao.getSettingInfo(SettingTypeEnum.DEPOSIT_AMOUNT.getValue());
        Integer depositAmount = 0;
        if (setting != null) {
            depositAmount = StringUtil.getInt(setting.getFieldValue());
        }
        return ApiResult.successWithData(depositAmount);
    }

    /**
     * 获取不计免赔金额
     */
    public ApiResult getDeductibles() {
        GlobalSetting setting = tripOrderDao.getSettingInfo(SettingTypeEnum.DEDUCTIBLES.getValue());
        Integer deductibles = 0;
        if (setting != null) {
            deductibles = StringUtil.getInt(setting.getFieldValue());
        }
        return ApiResult.successWithData(deductibles);
    }
}
