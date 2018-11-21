package cn.com.izj.service;

import cn.com.izj.base.CONSTANT;
import cn.com.izj.base.RESPONSE;
import cn.com.izj.base.entity.order.TripOrder;
import cn.com.izj.base.entity.pay.UserFund;
import cn.com.izj.base.entity.pay.UserFundLog;
import cn.com.izj.base.entity.pay.WithdrawDeposit;
import cn.com.izj.base.enums.FundTypeEnum;
import cn.com.izj.base.enums.PayTypeEnum;
import cn.com.izj.base.enums.ResponseEnum;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.base.service.BaseService;
import cn.com.izj.dao.*;
import cn.com.izj.dto.*;
import cn.com.izj.utils.JsonUtil;
import cn.com.izj.utils.OrderUtil;
import cn.com.izj.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: 朱鸿平
 * @date: 2018/6/25 21:53
 */
@Service
public class UserFundService extends BaseService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private UserFundDao userFundDao;
    @Autowired
    private UserFundLogDao userFundLogDao;
    @Autowired
    private TripOrderDao tripOrderDao;
    @Autowired
    private WithdrawDepositDao withdrawDao;
    @Autowired
    private UserPreferentialDao userPreferentialDao;

    /**
     * 支付订单(余额支付)
     *
     * @param payOrder 余额支付类
     */
    @Transactional
    public ApiResult payOrderByBalance(PayOrder payOrder, String ipAddr) {
        ApiResult result = new ApiResult();
        TripOrderResultDto order = tripOrderDao.getTripOrderByNum(payOrder.getOrderNum());
        if (order == null) {
            log.error("订单不存在，payOrder: " + JsonUtil.beanToJson(payOrder));
            result.setCode(ResponseEnum.ERROR.getValue());
            result.setMessage(ResponseEnum.ERROR.getDesc());
            return result;
        }
        //设置缓存锁避免重复提交
        String key = CONSTANT.PAY_KEY + payOrder.getOrderNum();
        String value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            log.error("订单支付中，payOrder: " + JsonUtil.beanToJson(payOrder));
            result.setCode(ResponseEnum.ERROR.getValue());
            result.setMessage(ResponseEnum.ERROR.getDesc());
            return result;
        }
        redisTemplate.opsForValue().set(key, payOrder.getUserId().toString());
        //设置5s过期时间
        redisTemplate.expire(key,5,TimeUnit.SECONDS);
        if (order.getRealPayAmount() < 0) {
            log.error("支付金额不正确，payOrder: " + JsonUtil.beanToJson(payOrder));
            result.setCode(ResponseEnum.FUND_ERROR.getValue());
            result.setMessage(ResponseEnum.FUND_ERROR.getDesc());
            return result;
        }
        //资金的流转
        UserFund userFund = userFundDao.selectUserFundByXLock(payOrder.getUserId());
        int balance = StringUtil.getInt(userFund.getBalance());//余额
        int giveBalance = StringUtil.getInt(userFund.getGiveBalance());//充值赠送金额

        int payFund = StringUtil.getInt(order.getRealPayAmount());//支付金额
        int userBalance = balance + giveBalance;//用户余额
        //余额支付优先使用用户余额，再使用充值赠送余额
        if (payFund <= balance) {//余额支付
            userFund.setBalance(balance - payFund);
        } else if (payFund <= userBalance) {//充值余额+赠送余额支付
            userFund.setBalance(0);
            userFund.setGiveBalance(userBalance - payFund);
        } else {
            log.error("用户余额不足，userFund: " + JsonUtil.beanToJson(userFund));
            result.setCode(ResponseEnum.BALANCE_NOT_ENOUGH.getValue());
            result.setMessage(ResponseEnum.BALANCE_NOT_ENOUGH.getDesc());
            return result;
        }
        userFund.setUpdateTime(new Date());
        //减少用户钱包余额
        userFundDao.updateById(userFund);
        TripOrder tripOrder = new TripOrder();
        //更新订单支付状态
        tripOrder.setId(order.getId());
        tripOrder.setStatus(TripOrder.STATUS_COMPLETE);
        tripOrder.setPayTime(new Date());
        tripOrder.setPayType(TripOrder.PAY_TYPE_FUND);
        tripOrderDao.updateById(tripOrder);
        //更新优惠券状态
        userPreferentialDao.updatePreferentialState(order.getPreferentialId());
        //记录支付日志
        insertBalanceUserFundLog(tripOrder, userFund.getBalance(), userFund.getGiveBalance(), ipAddr);
        //支付完成，删除缓存
        redisTemplate.delete(key);
        return result;
    }

    /**
     * 余额支付-记录日志，仅用于余额支付
     */
    private void insertBalanceUserFundLog(TripOrder order, Integer userBalance, Integer givenBalance, String ipAddr) {
        UserFundLog userFundLog = new UserFundLog();
        userFundLog.setTradeNumber(order.getNumber());
        userFundLog.setCarId(order.getCarId());
        userFundLog.setUserId(order.getUserId());
        userFundLog.setTradeFund(order.getRealPayAmount());
        userFundLog.setBalance(userBalance);
        userFundLog.setGivenBalance(givenBalance);
        userFundLog.setClientIp(ipAddr);
        userFundLog.setPayType(PayTypeEnum.BALANCE.getValue());
        userFundLog.setFundType(FundTypeEnum.PAY.getValue());
        userFundLog.setCreateTime(new Date());
        userFundLogDao.insert(userFundLog);
    }

    /**
     * 获取我的钱包信息
     *
     * @param userId 用户id
     */
    public ApiResult getMyWallet(Long userId) {
        UserFund userFund = userFundDao.selectUserFundByUserId(userId);
        Integer count = userFundDao.getUserPreferentialAmount(userId);
        MyWallet wallet = new MyWallet();
        wallet.setUserId(userId);
        if (userFund != null) {
            wallet.setUserBalance(userFund.getBalance());
            wallet.setGiveBalance(userFund.getGiveBalance());
        }
        wallet.setPreferentialAmount(StringUtil.getInt(count));
        return ApiResult.successWithData(wallet);
    }

    /**
     * 用户提现申请
     *
     * @param cash 提现实体
     */
    @Deprecated
    @Transactional
    public ApiResult createWithdrawDeposit(ApplicationCash cash, String ipAddr) {
        if (cash.getDrawAmount() <= 0) {//提现金额必须大于0
            log.error("用户提现金额非法，applicationCash: " + JsonUtil.beanToJson(cash));
            return new ApiResult(ResponseEnum.ERROR.getValue(), ResponseEnum.ERROR.getDesc());
        }
        Long userId = cash.getUserId();
        UserFund userFund = userFundDao.selectUserFundByXLock(userId);
        Integer oldIncome = userFund.getCarIncome();
        if (cash.getDrawAmount() < CONSTANT.WITHDRAW_DEPOSIT_MIN) {
            log.error("提现金额不足10元 userid=[" + userId + "]");
            return new ApiResult(RESPONSE.ERROR, "提现金额不足10元");
        }
        if (oldIncome < cash.getDrawAmount()) {// 判断余额是否足够
            log.error("提现余额不足 userId=" + "[" + userId + "]");
            return new ApiResult(RESPONSE.ERROR, "用户余额不足,无法提现");
        }
        String tradeNumber = CONSTANT.PREFIX_ORDER_WITHDRAW + OrderUtil.makeOrderNum();
        //添加提现流水明细
        WithdrawDeposit withdraw = new WithdrawDeposit();
        withdraw.setTradeNumber(tradeNumber);
        withdraw.setUserId(userId);
        withdraw.setDrawlAmount(cash.getDrawAmount());
        withdraw.setAuditState(CONSTANT.AUDIT_APPLICATION);
        withdraw.setCreateTime(new Date());
        withdrawDao.insert(withdraw);
        //更新车主收益余额
        userFundDao.updateCarIncome(userId, userFund.getCarIncome() - cash.getDrawAmount());
        //添加提现日志记录
        convertWithdrawLog(cash, userFund, ipAddr, tradeNumber);
        return new ApiResult();
    }

    /**
     * 车主收益提现日志转换类
     *
     * @param cash     提现实体
     * @param userFund 用户资金
     * @param ipAddr   客户端ip
     */
    @Deprecated
    public void convertWithdrawLog(ApplicationCash cash, UserFund userFund, String ipAddr, String tradeNumber) {
        UserFundLog userFundLog = new UserFundLog();
        userFundLog.setUserId(userFund.getUserId());
        userFundLog.setTradeFund(cash.getDrawAmount());
        userFundLog.setTradeNumber(tradeNumber);
        userFundLog.setFundType(FundTypeEnum.WITHDRAW.getValue());
        userFundLog.setClientIp(ipAddr);
        userFundLog.setAuditState(CONSTANT.AUDIT_APPLICATION);
        userFundLog.setBalance(userFund.getCarIncome() - cash.getDrawAmount());//提现日志余额为车主收益
        userFundLog.setCreateTime(new Date());
        insertCarIncomeFundLog(userFundLog);
    }

    /**
     * 记录车主收益日志
     */
    private void insertCarIncomeFundLog(UserFundLog userFundLog) {
        userFundLogDao.insert(userFundLog);
    }

    /**
     * 提现申请列表
     */
    @Deprecated
    public ApiResult getWithdrawList() {
        List<ApplicationCash> list = userFundDao.getWithdrawList();
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }
        return ApiResult.successWithData(list);
    }

    /**
     * 提现审核
     *
     * @param auditInfo 审核实体
     */
    @Deprecated
    @Transactional
    public ApiResult auditWithdraw(AuditInfo auditInfo) {
        ApplicationCash info = userFundDao.getWithdrawInfo(auditInfo.getTradeNumber());
        if (info == null || !info.getCardNumber().equals(auditInfo.getCardNumber())) {
            log.error("用户信息有误，请核对！" + JsonUtil.beanToJson(info));
            return new ApiResult(ResponseEnum.ERROR.getValue(), ResponseEnum.ERROR.getDesc());
        }
        //增加提现审核日志记录
        UserFundLog userFundLog = new UserFundLog();
        userFundLog.setUserId(info.getUserId());
        userFundLog.setTradeNumber(auditInfo.getTradeNumber());
        userFundLog.setFundType(FundTypeEnum.WITHDRAW.getValue());
        userFundLog.setTradeFund(info.getDrawAmount());//提现金额
        userFundLog.setCreateTime(new Date());
        //审核通过
        if (auditInfo.getAuditState().equals(CONSTANT.AUDIT_PASS)) {
            //修改提现表状态
            info.setAuditState(CONSTANT.AUDIT_PASS);
            //设置提现成功状态
            userFundLog.setAuditState(CONSTANT.AUDIT_PASS);
        } else {//审核不通过
            //修改提现表状态
            info.setAuditState(CONSTANT.AUDIT_FAIL);
            //将用户资金返回到用户车主收益余额
            UserFund userFund = userFundDao.selectUserFundByXLock(info.getUserId());
            userFundDao.updateCarIncome(info.getUserId(), userFund.getCarIncome() + info.getDrawAmount());
            //设置提现失败状态
            userFundLog.setAuditState(CONSTANT.AUDIT_FAIL);
        }
        userFundDao.updateWithdraw(info);
        insertCarIncomeFundLog(userFundLog);
        return new ApiResult();
    }

    /**
     * 根据提现单号查看提现信息
     *
     * @param tradeNumber 提现单号
     */
    @Deprecated
    public ApiResult getWithdrawInfo(String tradeNumber) {
        ApplicationCash info = userFundDao.getWithdrawInfo(tradeNumber);
        if (info == null) {
            info = new ApplicationCash();
        }
        return ApiResult.successWithData(info);
    }
}
