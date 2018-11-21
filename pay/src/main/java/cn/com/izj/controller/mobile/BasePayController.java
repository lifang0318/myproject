package cn.com.izj.controller.mobile;

import cn.com.izj.base.entity.GlobalSetting;
import cn.com.izj.base.entity.car.CarCommonInfo;
import cn.com.izj.base.entity.order.TripOrder;
import cn.com.izj.base.entity.pay.Recharge;
import cn.com.izj.base.entity.pay.UserFundLog;
import cn.com.izj.base.enums.FundTypeEnum;
import cn.com.izj.base.enums.PayTypeEnum;
import cn.com.izj.base.enums.SettingTypeEnum;
import cn.com.izj.dao.TripOrderDao;
import cn.com.izj.dao.UserFundLogDao;
import cn.com.izj.utils.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

public class BasePayController {

    protected final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private TripOrderDao tripOrderDao;

    @Autowired
    private UserFundLogDao userFundLogDao;

    /**
     * 获取赠送金额
     *
     * @param order
     * @return
     */
    public int getGivenAmount(Recharge order) {
        int givenAmount = 0;
        GlobalSetting globalSetting = this.tripOrderDao.getSettingInfo(SettingTypeEnum.RECHARGE.getValue());
        if (globalSetting != null && StringUtil.isNotEmpty(globalSetting.getFieldValue())) {
            GlobalSetting globalSetting_ = this.tripOrderDao.getSettingInfo(SettingTypeEnum.RECHARGE_MAX.getValue());
            if (globalSetting_ != null && StringUtil.isNotEmpty(globalSetting_.getFieldValue())) {
                int maxGivenAmount = StringUtil.getInt(globalSetting_.getFieldValue());
                int mod = StringUtil.getInt(order.getAmount()) / 100;
                if (mod > 0) {
                    givenAmount = new BigDecimal(String.valueOf(100 * mod)).multiply(new BigDecimal(globalSetting.getFieldValue())).intValue();
                    if (givenAmount > maxGivenAmount) {
                        givenAmount = maxGivenAmount;
                    }
                }
            }
        }
        return givenAmount;
    }

    /**
     * 微信支付-记录日志，仅用于微信支付
     */
    public void insertWechatUserFundLog(CarCommonInfo carInfo, TripOrder order, Integer userBalance, Integer givenBalance,
                                        String transactionId, String ipAddr) {
        UserFundLog userFundLog = new UserFundLog();
        userFundLog.setTradeNumber(order.getNumber());
        userFundLog.setCarId(carInfo.getId());
        userFundLog.setUserId(order.getUserId());
        userFundLog.setTradeFund(order.getRealPayAmount());
        userFundLog.setPayNumber(transactionId);
        userFundLog.setBalance(userBalance);
        userFundLog.setGivenBalance(givenBalance);
        userFundLog.setClientIp(ipAddr);
        userFundLog.setPayType(PayTypeEnum.WECHAT.getValue());
        userFundLog.setFundType(FundTypeEnum.PAY.getValue());
        userFundLog.setCreateTime(new Date());
        userFundLogDao.insert(userFundLog);
    }

    /**
     * 支付宝支付-记录日志，仅用于支付宝支付
     */
    public void insertalipayUserFundLog(CarCommonInfo carInfo, TripOrder order, Integer userBalance, Integer givenBalance,
                                        String transactionId, String ipAddr) {
        UserFundLog userFundLog = new UserFundLog();
        userFundLog.setTradeNumber(order.getNumber());
        userFundLog.setCarId(carInfo.getId());
        userFundLog.setUserId(order.getUserId());
        userFundLog.setTradeFund(order.getRealPayAmount());
        userFundLog.setPayNumber(transactionId);
        userFundLog.setBalance(userBalance);
        userFundLog.setGivenBalance(givenBalance);
        userFundLog.setClientIp(ipAddr);
        userFundLog.setPayType(PayTypeEnum.ALIPAY.getValue());
        userFundLog.setFundType(FundTypeEnum.PAY.getValue());
        userFundLog.setCreateTime(new Date());
        userFundLogDao.insert(userFundLog);
    }
}
