package cn.com.izj.controller.mobile;

import cn.com.izj.base.CONSTANT;
import cn.com.izj.base.entity.car.CarCommonInfo;
import cn.com.izj.base.entity.order.TripOrder;
import cn.com.izj.base.entity.pay.*;
import cn.com.izj.base.entity.preferential.Preferential;
import cn.com.izj.base.entity.preferential.UserPreferential;
import cn.com.izj.base.enums.FundTypeEnum;
import cn.com.izj.base.enums.ResponseEnum;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.config.WechatPayConfig;
import cn.com.izj.dao.*;
import cn.com.izj.dto.PrePayBackBean;
import cn.com.izj.dto.PrePayBackResult;
import cn.com.izj.dto.TripOrderResultDto;
import cn.com.izj.entity.User;
import cn.com.izj.service.UserPreferentialService;
import cn.com.izj.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;


/**
 * @author: lifang
 * @description:微信支付处理-controller
 * @date: Created in 2018/6/27 23:25
 * @version:
 */
@RequestMapping("/mobile/wechatpay")
@RestController
public class WechatpayController extends BasePayController {

    @Autowired
    private TripOrderDao tripOrderDao;

    @Autowired
    private RechargeDao rechargeDao;

    @Autowired
    private DepositDao depositDao;

    @Autowired
    private UserDepositLogDao userDepositLogDao;

    @Autowired
    private RedisLock redisLock;

    @Autowired
    private UserFundDao userFundDao;

    @Autowired
    private UserFundLogDao userFundLogDao;

    @Autowired
    private CarCommonDao carCommonDao;

    @Autowired
    private UserPreferentialDao userPreferentialDao;

    @Autowired
    private PreferentialDao preferentialDao;

    @Autowired
    private UserPreferentialService preferentialService;

    @Autowired
    WebApplicationContext webApplicationContext;

    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    /**
     * 行程订单微信支付统一下单获取预支付ID
     *
     * @param orderNum
     * @param request
     * @return
     */
    @PostMapping("/getPrepayId/tripOrder/{orderNum}")
    public ApiResult wechatTripOrderPay(@PathVariable("orderNum") String orderNum, HttpServletRequest request) throws Exception {
        log.info("---------getPrepayId---------，tripOrderNum is " + orderNum + ",time is " + DateTimeUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        ApiResult apiResult = new ApiResult();
        String clientIp = HttpClientService.getIpAddr(request);
        //获取订单
        TripOrderResultDto tripOrderReDto = tripOrderDao.getTripOrderByNum(orderNum);
        TripOrder tripOrder = new TripOrder();
        BeanUtils.copyProperties(tripOrderReDto, tripOrder);
        if (null != tripOrder) {
            Assert.state(tripOrder.getStatus() == TripOrder.STATUS_WAITTING_PAY, "tripOrder state is not waitting_pay");
            //组装参数
            SortedMap<String, String> paramap = new TreeMap<>();
            paramap.put("appid", WechatPayConfig.APPID);//应用ID
            paramap.put("mch_id", WechatPayConfig.MCH_ID);//商户号
            paramap.put("nonce_str", WeChatUtil.getRandomString(7));//随机字符串,主要保证签名不可预测
            paramap.put("body", CONSTANT.TRIP_ORDER_PAYINFO_PREFIX + "-" + tripOrder.getNumber());//商品描述
            paramap.put("attach", tripOrder.getNumber() + "," + clientIp);//附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
            paramap.put("out_trade_no", tripOrder.getNumber());//订单号
            paramap.put("total_fee", tripOrder.getRealPayAmount().toString());//总金额,单位分
            paramap.put("spbill_create_ip", clientIp);//用户端实际ip
            paramap.put("notify_url", WechatPayConfig.TRIP_ORDER_NOTIFY_URL);//接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
            paramap.put("trade_type", "APP");//交易类型
            paramap.put("sign", WeChatUtil.createSign(WechatPayConfig.CLIENT_SECRET, "UTF-8", paramap));
            String reqStr = WeChatUtil.creatXml(paramap);
//            System.out.println("请求参数："+ reqStr);
            URL url = new URL(WechatPayConfig.REQUEST_URL);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Pragma", "no-cache");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            OutputStreamWriter out = new OutputStreamWriter(con
                    .getOutputStream());
            out.write(reqStr);
            out.flush();
            out.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(con
                    .getInputStream()));
            String line;
            String resultMessage = "";
            for (line = br.readLine(); line != null; line = br.readLine()) {
                resultMessage = resultMessage + line;
            }
            String json = WeChatUtil.xml2JSON(resultMessage);
//            System.out.println("返回的参数："+ json);
            PrePayBackResult prePayBackResult = JsonUtil.jsonToBean(json, PrePayBackResult.class);
            PrePayBackBean prePayBack = prePayBackResult.getXml();
//            log.info(prePayBack.getCode_url());
            SortedMap<String, String> paymap = new TreeMap<>();
            if (null != prePayBack.getResult_code() && prePayBack.getResult_code().equalsIgnoreCase("SUCCESS")) {
                paymap.put("appid", prePayBack.getAppid());
                paymap.put("partnerid", prePayBack.getMch_id());
                paymap.put("prepayid", prePayBack.getPrepay_id());
                paymap.put("package", "Sign=WXPay");
                paymap.put("noncestr", prePayBack.getNonce_str());
                paymap.put("timestamp", FormatTimeUtil.currentTimeSeconds());
                paymap.put("sign", WeChatUtil.createSign(WechatPayConfig.CLIENT_SECRET, "UTF-8", paymap));
                apiResult.setCode(ResponseEnum.GET_PREPAYID_SUCCESS.getValue());
                apiResult.setMessage(ResponseEnum.GET_PREPAYID_SUCCESS.getDesc());
            } else {
                apiResult.setCode(ResponseEnum.GET_PREPAYID_FAILED.getValue());
                apiResult.setMessage(ResponseEnum.GET_PREPAYID_FAILED.getDesc());
            }
            apiResult.setData(paymap);
        } else {
            apiResult.setCode(ResponseEnum.ORDER_NOT_FOUND.getValue());
            apiResult.setMessage(ResponseEnum.ORDER_NOT_FOUND.getDesc());
        }
        return apiResult;
    }

    /**
     * 行程订单微信支付回调接口
     *
     * @param request
     * @param response
     */
    @PostMapping("/tripOrder/wxPayNotify")
    @Transactional(rollbackFor = Exception.class)
    public void tripOrderNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("----tripOrderWXPayNotify-----pay back-----");
        BufferedReader reader = null;
        PrintWriter out = null;

        try {
            reader = request.getReader();
            out = response.getWriter();
            String line;
            String xmlString;
            StringBuffer inputString = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                inputString.append(line);
            }
            xmlString = inputString.toString();
            log.info("返回的数据是:" + xmlString);
            SortedMap xmlToSortedMap = WeChatUtil.xmlToSortedMap(xmlString);
            SortedMap<String, String> dataMap = (SortedMap<String, String>) xmlToSortedMap.get("xml");

            String returnMsg = "<xml>";
            String orderNum = dataMap.get("out_trade_no");
            Date payTime = WeChatUtil.convertByDateStr(dataMap.get("time_end"));
            String[] params = dataMap.get("attach").split(",");
            if (null != dataMap.get("return_code") && dataMap.get("return_code").equalsIgnoreCase("SUCCESS")) {
                if (null != dataMap.get("result_code") && dataMap.get("result_code").equalsIgnoreCase("SUCCESS")) {
                    if (WeChatUtil.verifySign(dataMap, WechatPayConfig.CLIENT_SECRET)) {//签名校验，防止数据泄漏导致出现“假通知”，造成资金损失。
                        //相同请求加锁
                        boolean lock = redisLock.lock(orderNum);
                        if (lock) {
                            //验证订单号是否被改动
                            if (!orderNum.equals(params[0])) {
                                log.error("orderNum have been changed");
                                returnMsg = returnMsg + "<return_code>FAIL</return_code>";
                                out.write(returnMsg + "</xml>");
                                return;
                            }

                            TripOrderResultDto tripOrderReDto = tripOrderDao.getTripOrderByNum(orderNum);
                            TripOrder tripOrder = new TripOrder();
                            BeanUtils.copyProperties(tripOrderReDto, tripOrder);

                            //验证支付金额是否等于订单实付金额
                            if (tripOrder.getRealPayAmount() != Integer.parseInt(dataMap.get("total_fee"))) {
                                log.error("pay amount error,orderNum is " + orderNum);
                                returnMsg = returnMsg + "<return_code>FAIL</return_code>";
                                out.write(returnMsg + "</xml>");
                                return;
                            }
                            Integer orderStatus = tripOrder.getStatus();
                            if (orderStatus == TripOrder.STATUS_WAITTING_PAY) {
                                log.info("wechat pay back , tripOrder [" + dataMap.get("out_trade_no") + "] status is OK.....");
                                returnMsg = returnMsg + "<return_code>SUCCESS</return_code>";
                                out.write(returnMsg + "</xml>");//回复微信
                                executorService.execute(() -> {
                                    //spring无法处理thread的事务，声明式事务无效
                                    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                                    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                                    PlatformTransactionManager txManager = webApplicationContext.getBean(PlatformTransactionManager.class);
                                    TransactionStatus status = txManager.getTransaction(def);

                                    try {
                                        UserFund userFund = userFundDao.selectUserFundByXLock(tripOrder.getUserId());
                                        //修改订单状态，设置支付时间及支付类型
                                        tripOrder.setStatus(TripOrder.STATUS_COMPLETE);
                                        tripOrder.setPayType(TripOrder.PAY_TYEP_WECHAT);
                                        tripOrder.setPayTime(payTime);
                                        this.tripOrderDao.updateByOrderNum(tripOrder);
                                        //2.车主加收益
                                        CarCommonInfo carInfo = carCommonDao.findById(tripOrder.getCarId());
                                        //this.userFundDao.updateCarIncome(carInfo.getOwnerId(), tripOrder.getOwnerEarnAmount());
                                        //记录支付日志
                                        String transactionId = dataMap.get("transaction_id");
                                        insertWechatUserFundLog(carInfo, tripOrder, userFund.getBalance(), userFund.getGiveBalance(), transactionId, params[1]);
                                        //4.修改优惠券状态为已使用
                                        Long preferentialId = tripOrder.getPreferentialId();
                                        if(null != preferentialId){
                                            UserPreferential userPreferential = this.userPreferentialDao.findById(preferentialId);
                                            userPreferential.setState(UserPreferential.STATE_USED);
                                            this.userPreferentialDao.updateById(userPreferential);
                                        }
                                        txManager.commit(status); // 提交事务
                                        //释放锁
                                        redisLock.unlock(orderNum);
                                    } catch (Exception e) {
                                        log.error("wechat pay back handle failed");
                                        txManager.rollback(status);
                                    }
                                });
                            } else if (orderStatus == TripOrder.STATUS_COMPLETE) {
                                log.info("wechat pay back , status is " + orderStatus + " ,tripOrderNum is " + orderNum);
                                returnMsg = returnMsg + "<return_code>SUCCESS</return_code>";
                                out.write(returnMsg + "</xml>");
                            } else {
                                //订单状态异常
                                log.info("wechat pay back , tripOrder [" + orderNum + "] status is abnormal.....");
                                returnMsg = returnMsg + "<return_code>FAIL</return_code>";
                                out.write(returnMsg + "</xml>");
                            }
                        }
                    }
                } else {
                    log.info("wechat pay back , result_code is " + dataMap.get("result_code") + " ,tripOrderNo is " + orderNum);
                    returnMsg = returnMsg + "<return_code>FAIL</return_code>";
                    response.getWriter().write(returnMsg + "</xml>");
                }
            } else {
                log.info("wechat pay back ,  return_code is " + dataMap.get("return_code") + " ,tripOrderNo is " + orderNum);
                returnMsg = returnMsg + "<return_code>FAIL</return_code>";
                out.write(returnMsg + "</xml>");
            }
        } catch (Exception e) {
            log.info("wechat pay back handle failed");
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }


    /**
     * 充值订单微信支付统一下单获取预支付ID
     *
     * @param orderNum
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/getPrepayId/rechargeOrder/{orderNum}")
    public ApiResult wechatRechargeOrderPay(@PathVariable("orderNum") String orderNum, HttpServletRequest request) throws Exception {
        log.info("---------getPrepayId---------，rechargeOrderNum is " + orderNum + ",time is " + DateTimeUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        ApiResult apiResult = new ApiResult();
        String clientIp = HttpClientService.getIpAddr(request);
        //获取订单
        Recharge order = this.rechargeDao.getRechargeByNum(orderNum);
        if (null != order) {
            Assert.state(order.getState() == Recharge.STATE_WAITINT_PAY, "rechargeOrder state is not waitting_pay");
            //组装参数
            SortedMap<String, String> paramap = new TreeMap<>();
            paramap.put("appid", WechatPayConfig.APPID);//应用ID
            paramap.put("mch_id", WechatPayConfig.MCH_ID);//商户号
            paramap.put("nonce_str", WeChatUtil.getRandomString(7));//随机字符串,主要保证签名不可预测
            paramap.put("body", CONSTANT.RECHARGE_ORDER_PAYINFO_PREFIX + "-" + order.getNumber());//商品描述
            paramap.put("attach", order.getNumber() + "," + clientIp);//附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
            paramap.put("out_trade_no", order.getNumber());//订单号
            paramap.put("total_fee", order.getAmount().toString());//总金额,单位分
            paramap.put("spbill_create_ip", clientIp);//用户端实际ip
            //接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
            paramap.put("notify_url", WechatPayConfig.RECHARGE_ORDER_NOTIFY_URL);//接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
            paramap.put("trade_type", "APP");//交易类型
            paramap.put("sign", WeChatUtil.createSign(WechatPayConfig.CLIENT_SECRET, "UTF-8", paramap));
            String reqStr = WeChatUtil.creatXml(paramap);
            URL url = new URL(WechatPayConfig.REQUEST_URL);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Pragma", "no-cache");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            OutputStreamWriter out = new OutputStreamWriter(con
                    .getOutputStream());
            out.write(reqStr);
            out.flush();
            out.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(con
                    .getInputStream()));
            String line;
            String resultMessage = "";
            for (line = br.readLine(); line != null; line = br.readLine()) {
                resultMessage = resultMessage + line;
            }
            String json = WeChatUtil.xml2JSON(resultMessage);

            PrePayBackResult prePayBackResult = JsonUtil.jsonToBean(json, PrePayBackResult.class);
            PrePayBackBean prePayBack = prePayBackResult.getXml();
//            log.info(prePayBack.getCode_url());
            SortedMap<String, String> paymap = new TreeMap<>();
            if (null != prePayBack.getResult_code() && prePayBack.getResult_code().equalsIgnoreCase("SUCCESS")) {
                paymap.put("appid", prePayBack.getAppid());
                paymap.put("partnerid", prePayBack.getMch_id());
                paymap.put("prepayid", prePayBack.getPrepay_id());
                paymap.put("package", "Sign=WXPay");
                paymap.put("noncestr", prePayBack.getNonce_str());
                paymap.put("timestamp", FormatTimeUtil.currentTimeSeconds());
                paymap.put("sign", WeChatUtil.createSign(WechatPayConfig.CLIENT_SECRET, "UTF-8", paymap));
                apiResult.setCode(ResponseEnum.GET_PREPAYID_SUCCESS.getValue());
                apiResult.setMessage(ResponseEnum.GET_PREPAYID_SUCCESS.getDesc());
            } else {
                apiResult.setCode(ResponseEnum.GET_PREPAYID_FAILED.getValue());
                apiResult.setMessage(ResponseEnum.GET_PREPAYID_FAILED.getDesc());
            }
            apiResult.setData(paymap);
        } else {
            apiResult.setCode(ResponseEnum.ORDER_NOT_FOUND.getValue());
            apiResult.setMessage(ResponseEnum.ORDER_NOT_FOUND.getDesc());
        }
        return apiResult;
    }

    /**
     * 充值订单微信支付回调接口
     *
     * @param request
     * @param response
     */
    @PostMapping("/rechargeOrder/wxPayNotify")
    @Transactional(rollbackFor = Exception.class)
    public void rechargeOrderNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("----rechargeOrderWXPayNotify-----pay back-----");
        BufferedReader reader = null;
        PrintWriter out = null;

        try {
            reader = request.getReader();
            out = response.getWriter();
            String line;
            String xmlString;
            StringBuffer inputString = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                inputString.append(line);
            }
            xmlString = inputString.toString();
            log.info("返回的数据是:" + xmlString);
            SortedMap xmlToSortedMap = WeChatUtil.xmlToSortedMap(xmlString);
            SortedMap<String, String> dataMap = (SortedMap<String, String>) xmlToSortedMap.get("xml");

            String returnMsg = "<xml>";
            String orderNum = dataMap.get("out_trade_no");
            Date payTime = WeChatUtil.convertByDateStr(dataMap.get("time_end"));
            String[] params = dataMap.get("attach").split(",");
            if (null != dataMap.get("return_code") && dataMap.get("return_code").equalsIgnoreCase("SUCCESS")) {
                if (null != dataMap.get("result_code") && dataMap.get("result_code").equalsIgnoreCase("SUCCESS")) {
                    if (WeChatUtil.verifySign(dataMap, WechatPayConfig.CLIENT_SECRET)) {//签名校验，防止数据泄漏导致出现“假通知”，造成资金损失。
                        //相同请求加锁
                        boolean lock = redisLock.lock(orderNum);
                        if (lock) {
                            //验证订单号是否被改动
                            if (!orderNum.equals(params[0])) {
                                log.error("orderNum have been changed");
                                returnMsg = returnMsg + "<return_code>FAIL</return_code>";
                                out.write(returnMsg + "</xml>");
                                return;
                            }

                            Recharge order = this.rechargeDao.getRechargeByNum(orderNum);

                            //验证支付金额是否等于订单实付金额
                            if (order.getAmount() != Integer.parseInt(dataMap.get("total_fee"))) {
                                log.error("pay amount error,orderNum is " + orderNum);
                                returnMsg = returnMsg + "<return_code>FAIL</return_code>";
                                out.write(returnMsg + "</xml>");
                                return;
                            }
                            Integer orderStatus = order.getState();
                            if (orderStatus == Recharge.STATE_WAITINT_PAY) {
                                log.info("wechat pay back , rechargeOrder [" + dataMap.get("out_trade_no") + "] status is OK.....");
                                returnMsg = returnMsg + "<return_code>SUCCESS</return_code>";
                                out.write(returnMsg + "</xml>");//回复微信
                                executorService.execute(() -> {
                                    //spring无法处理thread的事务，声明式事务无效
                                    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                                    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                                    PlatformTransactionManager txManager = webApplicationContext.getBean(PlatformTransactionManager.class);
                                    TransactionStatus status = txManager.getTransaction(def);

                                    try {
                                        //修改支付订单状态，设置支付时间及支付类型
                                        order.setState(Recharge.STATE_PAYED);
                                        order.setPayType(Recharge.PAY_TYEP_WECHAT);
                                        order.setPayTime(payTime);
                                        this.rechargeDao.updateById(order);
                                        //2.增加用户余额
                                        UserFund userFund = userFundDao.selectUserFundByXLock(order.getUserId());
                                        userFund.setBalance(userFund.getBalance() + order.getAmount());//余额
                                        int givenAmount = getGivenAmount(order);
                                        userFund.setGiveBalance(userFund.getGiveBalance() + givenAmount);//赠送金额
                                        this.userFundDao.updateById(userFund);
                                        //3.支付日志记录
                                        UserFundLog userFundLog = new UserFundLog();
                                        userFundLog.setTradeNumber(orderNum);
                                        userFundLog.setFundType(FundTypeEnum.RECHARGE.getValue());
                                        userFundLog.setPayType(Recharge.PAY_TYEP_WECHAT);
                                        userFundLog.setPayNumber(dataMap.get("transaction_id"));
                                        userFundLog.setUserId(order.getUserId());
                                        userFundLog.setTradeFund(order.getAmount());
                                        userFundLog.setClientIp(params[1]);
                                        userFundLog.setBalance(userFund.getBalance());
                                        userFundLog.setGivenBalance(userFund.getGiveBalance());
                                        userFundLog.setCreateTime(new Date());
                                        this.userFundLogDao.insert(userFundLog);
                                        txManager.commit(status); // 提交事务
                                        //释放锁
                                        redisLock.unlock(orderNum);
                                    } catch (Exception e) {
                                        log.error("wechat pay back handle failed");
                                        txManager.rollback(status);
                                    }
                                });
                            } else if (orderStatus == Recharge.STATE_PAYED) {
                                log.info("wechat pay back , status is " + orderStatus + " ,rechargeOrderNum is " + orderNum);
                                returnMsg = returnMsg + "<return_code>SUCCESS</return_code>";
                                out.write(returnMsg + "</xml>");
                            } else {
                                //订单状态异常
                                log.info("wechat pay back , rechargeOrder [" + dataMap.get("out_trade_no") + "] status is abnormal.....");
                                returnMsg = returnMsg + "<return_code>FAIL</return_code>";
                                out.write(returnMsg + "</xml>");
                            }
                        }
                    }
                } else {
                    log.info("wechat pay back , result_code is " + dataMap.get("result_code") + " ,rechargeOrderNum is " + orderNum);
                    returnMsg = returnMsg + "<return_code>FAIL</return_code>";
                    response.getWriter().write(returnMsg + "</xml>");
                }
            } else {
                log.info("wechat pay back ,  return_code is " + dataMap.get("return_code") + " ,rechargeOrderNum is " + orderNum);
                returnMsg = returnMsg + "<return_code>FAIL</return_code>";
                out.write(returnMsg + "</xml>");
            }
        } catch (Exception e) {
            log.info("wechat pay back handle failed");
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }


    /**
     * 押金缴纳微信支付统一下单获取预支付ID
     *
     * @param orderNum
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/getPrepayId/depositOrder/{orderNum}")
    public ApiResult wechatDepositOrderPay(@PathVariable("orderNum") String orderNum, HttpServletRequest request) throws Exception {
        log.info("---------getPrepayId---------，depositOrderNum is " + orderNum + ",time is " + DateTimeUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        ApiResult apiResult = new ApiResult();
        String clientIp = HttpClientService.getIpAddr(request);
        //获取订单
        Deposit order = this.depositDao.getDepositByOrderNum(orderNum);
        if (null != order) {
            Assert.state(order.getPayState() == Deposit.STATE_WAITING_PAY, "depositOrder state is not waitting_pay");
            //组装参数
            SortedMap<String, String> paramap = new TreeMap<>();
            paramap.put("appid", WechatPayConfig.APPID);//应用ID
            paramap.put("mch_id", WechatPayConfig.MCH_ID);//商户号
            paramap.put("nonce_str", WeChatUtil.getRandomString(7));//随机字符串,主要保证签名不可预测
            paramap.put("body", CONSTANT.DEPOSIT_ORDER_PAYINFO_PREFIX + "-" + order.getOrderNum());//商品描述
            paramap.put("attach", order.getOrderNum() + "," + clientIp);//附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
            paramap.put("out_trade_no", order.getOrderNum());//订单号
            paramap.put("total_fee", order.getAmount().toString());//总金额,单位分
            paramap.put("spbill_create_ip", clientIp);//用户端实际ip
            //接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
            paramap.put("notify_url", WechatPayConfig.DEPOSIT_ORDER_NOTIFY_URL);//接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
            paramap.put("trade_type", "NATIVE");//交易类型
            paramap.put("sign", WeChatUtil.createSign(WechatPayConfig.CLIENT_SECRET, "UTF-8", paramap));
            String reqStr = WeChatUtil.creatXml(paramap);
            URL url = new URL(WechatPayConfig.REQUEST_URL);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Pragma", "no-cache");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            OutputStreamWriter out = new OutputStreamWriter(con
                    .getOutputStream());
            out.write(reqStr);
            out.flush();
            out.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(con
                    .getInputStream()));
            String line;
            String resultMessage = "";
            for (line = br.readLine(); line != null; line = br.readLine()) {
                resultMessage = resultMessage + line;
            }
            String json = WeChatUtil.xml2JSON(resultMessage);

            PrePayBackResult prePayBackResult = JsonUtil.jsonToBean(json, PrePayBackResult.class);
            PrePayBackBean prePayBack = prePayBackResult.getXml();
//            log.info(prePayBack.getCode_url());
            SortedMap<String, String> paymap = new TreeMap<>();
            if (null != prePayBack.getResult_code() && prePayBack.getResult_code().equalsIgnoreCase("SUCCESS")) {
                paymap.put("appid", prePayBack.getAppid());
                paymap.put("partnerid", prePayBack.getMch_id());
                paymap.put("prepayid", prePayBack.getPrepay_id());
                paymap.put("package", "Sign=WXPay");
                paymap.put("noncestr", prePayBack.getNonce_str());
                paymap.put("timestamp", FormatTimeUtil.currentTimeSeconds());
                paymap.put("sign", WeChatUtil.createSign(WechatPayConfig.CLIENT_SECRET, "UTF-8", paymap));
                apiResult.setCode(ResponseEnum.GET_PREPAYID_SUCCESS.getValue());
                apiResult.setMessage(ResponseEnum.GET_PREPAYID_SUCCESS.getDesc());
            } else {
                apiResult.setCode(ResponseEnum.GET_PREPAYID_FAILED.getValue());
                apiResult.setMessage(ResponseEnum.GET_PREPAYID_FAILED.getDesc());
            }
            apiResult.setData(paymap);
        } else {
            apiResult.setCode(ResponseEnum.ORDER_NOT_FOUND.getValue());
            apiResult.setMessage(ResponseEnum.ORDER_NOT_FOUND.getDesc());
        }
        return apiResult;
    }


    /**
     * 押金缴纳微信支付回调接口
     *
     * @param request
     * @param response
     */
    @PostMapping("/depositOrder/wxPayNotify")
    @Transactional(rollbackFor = Exception.class)
    public void depositOrderNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("----depositOrderWXPayNotify-----pay back-----");
        BufferedReader reader = null;
        PrintWriter out = null;
        try {
            reader = request.getReader();
            out = response.getWriter();
            String line;
            String xmlString;
            StringBuffer inputString = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                inputString.append(line);
            }
            xmlString = inputString.toString();
            log.info("返回的数据是:" + xmlString);
            SortedMap xmlToSortedMap = WeChatUtil.xmlToSortedMap(xmlString);
            SortedMap<String, String> dataMap = (SortedMap<String, String>) xmlToSortedMap.get("xml");

            String returnMsg = "<xml>";
            String orderNum = dataMap.get("out_trade_no");
            Date payTime = WeChatUtil.convertByDateStr(dataMap.get("time_end"));
            String[] params = dataMap.get("attach").split(",");
            if (null != dataMap.get("return_code") && dataMap.get("return_code").equalsIgnoreCase("SUCCESS")) {
                if (null != dataMap.get("result_code") && dataMap.get("result_code").equalsIgnoreCase("SUCCESS")) {
                    if (WeChatUtil.verifySign(dataMap, WechatPayConfig.CLIENT_SECRET)) {//签名校验，防止数据泄漏导致出现“假通知”，造成资金损失。
                        //相同请求加锁
                        boolean lock = redisLock.lock(orderNum);
                        if (lock) {
                            //验证订单号是否被改动
                            if (!orderNum.equals(params[0])) {
                                log.error("orderNum have been changed");
                                returnMsg = returnMsg + "<return_code>FAIL</return_code>";
                                out.write(returnMsg + "</xml>");
                                return;
                            }

                            Deposit deposit = this.depositDao.getDepositByOrderNum(orderNum);

                            //验证支付金额是否等于订单实付金额
                            if (deposit.getAmount() != Integer.parseInt(dataMap.get("total_fee"))) {
                                log.error("pay amount error,orderNum is " + orderNum);
                                returnMsg = returnMsg + "<return_code>FAIL</return_code>";
                                out.write(returnMsg + "</xml>");
                                return;
                            }
                            Integer payState = deposit.getPayState();
                            if (payState == Deposit.STATE_WAITING_PAY) {
                                log.info("wechat pay back , depositOrder [" + dataMap.get("out_trade_no") + "] status is OK.....");
                                returnMsg = returnMsg + "<return_code>SUCCESS</return_code>";
                                out.write(returnMsg + "</xml>");//回复微信
                                executorService.execute(() -> {
                                    //spring无法处理thread的事务，声明式事务无效
                                    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                                    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                                    PlatformTransactionManager txManager = webApplicationContext.getBean(PlatformTransactionManager.class);
                                    TransactionStatus status = txManager.getTransaction(def);

                                    try {
                                        //修改支付订单状态，设置支付时间及支付类型
                                        deposit.setType(Deposit.DEPOSIT_CASH);
                                        deposit.setPayState(Deposit.STATE_PAYED);
                                        deposit.setPayType(Deposit.PAY_TYEP_WECHAT);
                                        deposit.setPayTime(payTime);
                                        deposit.setUpdateTime(new Date());
                                        this.depositDao.updateById(deposit);
                                        //2.押金支付日志记录
                                        UserDepositLog userDepositLog = new UserDepositLog();
                                        userDepositLog.setUserId(deposit.getUserId());
                                        userDepositLog.setPayType(UserDepositLog.PAY_TYEP_WECHAT);
                                        userDepositLog.setPayNumber(orderNum);
                                        userDepositLog.setTradeNumber(dataMap.get("transaction_id"));
                                        userDepositLog.setAmount(deposit.getAmount());
                                        userDepositLog.setCreateTime(new Date());
                                        userDepositLog.setType(Deposit.STATE_PAYED);
                                        this.userDepositLogDao.insert(userDepositLog);
                                        //3.缴纳押金后,有邀请人则赠送邀请券
                                        User user = userPreferentialDao.getUserReferral(deposit.getUserId());
                                        String regExp = "^[1][3-9][0-9]{9}$";
                                        Pattern p = Pattern.compile(regExp);
                                        if (user != null && !p.matcher(user.getUsername()).matches()) {
                                            List<Preferential> list = preferentialDao.findRegisterPreferential(Preferential.TYPE_INVITE);
                                            preferentialService.insertPreferential(deposit.getUserId(), list);
                                            preferentialService.insertPreferential(user.getId(),list);
                                        }
                                        txManager.commit(status); // 提交事务
                                        //释放锁
                                        redisLock.unlock(orderNum);
                                    } catch (Exception e) {
                                        log.error("wechat pay back handle failed");
                                        txManager.rollback(status);
                                    }
                                });
                            } else if (payState == Deposit.STATE_PAYED) {
                                log.info("wechat pay back , status is " + payState + " ,depositOrderNum is " + orderNum);
                                returnMsg = returnMsg + "<return_code>SUCCESS</return_code>";
                                out.write(returnMsg + "</xml>");
                            } else {
                                //订单状态异常
                                log.info("wechat pay back , depositOrder [" + dataMap.get("out_trade_no") + "] status is abnormal.....");
                                returnMsg = returnMsg + "<return_code>FAIL</return_code>";
                                out.write(returnMsg + "</xml>");
                            }
                        }
                    }
                } else {
                    log.info("wechat pay back , result_code is " + dataMap.get("result_code") + " , depositOrderNum is " + orderNum);
                    returnMsg = returnMsg + "<return_code>FAIL</return_code>";
                    response.getWriter().write(returnMsg + "</xml>");
                }
            } else {
                log.info("wechat pay back ,  return_code is " + dataMap.get("return_code") + " , depositOrderNum is " + orderNum);
                returnMsg = returnMsg + "<return_code>FAIL</return_code>";
                out.write(returnMsg + "</xml>");
            }
        } catch (Exception e) {
            log.info("wechat pay back handle failed");
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }
}