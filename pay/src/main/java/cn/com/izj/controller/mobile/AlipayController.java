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
import cn.com.izj.config.AlipayConfig;
import cn.com.izj.dao.*;
import cn.com.izj.dto.TripOrderResultDto;
import cn.com.izj.entity.User;
import cn.com.izj.service.UserPreferentialService;
import cn.com.izj.utils.DateTimeUtils;
import cn.com.izj.utils.HttpClientService;
import cn.com.izj.utils.RedisLock;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
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
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 支付宝支付-controller
 *
 * @author lifang
 */
@RequestMapping("/mobile/alipay")
@RestController
public class AlipayController extends BasePayController {

    @Autowired
    private RechargeDao rechargeDao;

    @Autowired
    private TripOrderDao tripOrderDao;

    @Autowired
    private DepositDao depositDao;

    @Autowired
    private UserDepositLogDao userDepositLogDao;

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
    private RedisLock redisLock;

    @Autowired
    WebApplicationContext webApplicationContext;

    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    /**
     * 支付宝客户端
     */
    private static AlipayClient alipayClient;

    static {
        //实例化客户端
        alipayClient = new DefaultAlipayClient(
                AlipayConfig.GATEWAY_URL,
                AlipayConfig.APP_ID,
                AlipayConfig.MERCHANT_PRIVATE_KEY,
                "json",
                AlipayConfig.CHARSET,
                AlipayConfig.ALIPAY_PUBLIC_KEY,
                AlipayConfig.SIGN_TYPE);
    }

    /**
     * 指定行程订单订单号，获取支付宝支付订单信息
     *
     * @param orderNum
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/getPayOrder/tripOrder/{orderNum}")
    public ApiResult aliypayTripOrder(@PathVariable("orderNum") String orderNum, HttpServletRequest request) throws Exception {
        log.info("prepare alipay tripOrder，orderNum is " + orderNum + ",time is " + DateTimeUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        Assert.notNull(orderNum, "orderNo can not be null");
        ApiResult apiResult = new ApiResult();
        String clientIp = HttpClientService.getIpAddr(request);
        //获取订单
        TripOrderResultDto tripOrderReDto = tripOrderDao.getTripOrderByNum(orderNum);
        TripOrder tripOrder = new TripOrder();
        BeanUtils.copyProperties(tripOrderReDto, tripOrder);
        if (null != tripOrder) {
            Assert.state(tripOrder.getStatus().equals(TripOrder.STATUS_WAITTING_PAY), "tripOrder state is not waitting_pay");
            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest alipayTradeAppPayRequest = new AlipayTradeAppPayRequest();
            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            //model.setBody("可选，对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。");
            model.setSubject(CONSTANT.TRIP_ORDER_PAYINFO_PREFIX + "-" + tripOrder.getNumber());
            model.setOutTradeNo(tripOrder.getNumber());
            model.setTimeoutExpress(AlipayConfig.TIMEOUT_EXPRESS);
            //订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
            model.setTotalAmount(new BigDecimal(tripOrder.getRealPayAmount()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            model.setProductCode(AlipayConfig.PRODUCT_CODE);
            //公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝会在异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝
            model.setPassbackParams(URLEncoder.encode(clientIp, "UTF-8"));
            alipayTradeAppPayRequest.setBizModel(model);
            alipayTradeAppPayRequest.setNotifyUrl(AlipayConfig.TRIP_ORDER_NOTIFY_URL);
            //request.setReturnUrl();//支付成功后跳转的页面
            try {
                //这里和普通的接口调用不同，使用的是sdkExecute
                AlipayTradeAppPayResponse response = alipayClient.sdkExecute(alipayTradeAppPayRequest);
                log.info("行程订单号：" + tripOrder.getNumber() + "，返回的支付宝订单参数是：" + response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
                apiResult.setCode(ResponseEnum.SUCCESS.getValue());
                apiResult.setMessage(ResponseEnum.SUCCESS.getDesc());
                apiResult.setData(response.getBody());
            } catch (AlipayApiException e) {
                log.error("行程订单号：" + tripOrder.getNumber() + "，获取支付宝订单参数时发生错误,错误码：" + e.getErrCode() + "错误消息：" + e.getErrMsg());
                e.printStackTrace();
                apiResult.setCode(ResponseEnum.ERROR.getValue());
                apiResult.setMessage(ResponseEnum.ERROR.getDesc());
            }
        } else {
            log.error("行程订单号：" + orderNum + "获取支付宝订单参数时发生错误,未获取该行程订单相关信息");
            apiResult.setCode(ResponseEnum.ORDER_NOT_FOUND.getValue());
            apiResult.setMessage(ResponseEnum.ORDER_NOT_FOUND.getDesc());
        }
        return apiResult;
    }


    /**
     * 行程订单支付宝支付回调
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping("/tripOrder/alipayNotify")
    @Transactional(rollbackFor = Exception.class)
    public String tripOrderAlipayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("----tripOrder aliPayNotify-----pay back-----");
        //获取支付宝POST过来反馈信息
        Map<String, String> params = convertRequestParamsToMap(request.getParameterMap());
        String paramsJson = JSON.toJSONString(params);
        log.info("支付宝回调:" + paramsJson);
        try {
            //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGN_TYPE);
            if (signVerified) {
                log.info("支付宝回调签名认证成功");
                // 按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure
                final TripOrder tripOrder = this.checkTripOrder(params);
                // 另起线程处理业务
                executorService.execute(() -> {
                    String trade_status = params.get("trade_status");
                    // 支付成功
                    if (trade_status.equalsIgnoreCase("TRADE_SUCCESS")
                            || trade_status.equalsIgnoreCase("TRADE_FINISHED")) {
                        // 处理支付成功逻辑
                        String orderNum = tripOrder.getNumber();
                        boolean lock = redisLock.lock(orderNum);
                        if (lock) {
                            //spring无法处理thread的事务，声明式事务无效
                            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                            PlatformTransactionManager txManager = webApplicationContext.getBean(PlatformTransactionManager.class);
                            TransactionStatus status = txManager.getTransaction(def);
                            try {
                                UserFund userFund = userFundDao.selectUserFundByXLock(tripOrder.getUserId());
                                Integer orderStatus = tripOrder.getStatus();
                                if (orderStatus == TripOrder.STATUS_WAITTING_PAY) {
                                    log.info("alipay pay back , tripOrder [" + orderNum + "] status is OK.....");
                                    //1.修改订单状态，设置支付时间及支付类型
                                    tripOrder.setStatus(TripOrder.STATUS_COMPLETE);
                                    tripOrder.setPayType(TripOrder.PAY_TYPE_ALIPY);
                                    tripOrder.setPayTime(new Date());
                                    tripOrderDao.updateByOrderNum(tripOrder);
                                    //2.车主加收益
                                    CarCommonInfo carInfo = carCommonDao.findById(tripOrder.getCarId());
//                                    this.userFundDao.updateCarIncome(carInfo.getOwnerId(), tripOrder.getOwnerEarnAmount());
                                    //3.支付日志记录
                                    insertalipayUserFundLog(carInfo, tripOrder, userFund.getBalance(), userFund.getGiveBalance(), params.get("trade_no"), params.get("passback_params"));
                                    //4.修改优惠券状态为已使用
                                    Long preferentialId = tripOrder.getPreferentialId();
                                    if (null != preferentialId) {
                                        UserPreferential userPreferential = this.userPreferentialDao.findById(preferentialId);
                                        userPreferential.setState(UserPreferential.STATE_USED);
                                        this.userPreferentialDao.updateById(userPreferential);
                                    }
                                    txManager.commit(status); // 提交事务
                                    //释放锁
                                    redisLock.unlock(orderNum);
                                } else if (orderStatus == TripOrder.STATUS_COMPLETE) {
                                    log.info("alipay pay back , status is " + orderStatus + " ,tripOrderNum is " + orderNum);
                                } else {
                                    //订单状态异常
                                    log.info("alipay pay back , tripOrder [" + orderNum + "] status is abnormal.....");
                                }
                            } catch (Exception e) {
                                log.error("支付宝回调业务处理报错,params:" + paramsJson, e);
                                txManager.rollback(status); // 回滚事务
                            }
                        }
                    } else {
                        log.error("没有处理支付宝回调业务，支付宝交易状态：" + trade_status + ",params:" + paramsJson);
                    }
                });
                // 如果签名验证正确，立即返回success，后续业务另起线程单独处理
                // 业务处理失败，可查看日志进行补偿，跟支付宝已经没多大关系。
                return "success";
            } else {
                log.error("支付宝回调签名认证失败，signVerified=false, paramsJson:" + paramsJson);
                return "failure";
            }
        } catch (AlipayApiException e) {
            log.error("支付宝回调签名认证失败,paramsJson:" + paramsJson + "errorMsg:" + e.getMessage());
            return "failure";
        }
    }

    /**
     * 指定充值订单号，获取支付宝支付订单信息
     *
     * @param orderNum
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/getPrepayId/rechargeOrder/{orderNum}")
    public ApiResult alipayRechargeOrder(@PathVariable("orderNum") String orderNum, HttpServletRequest request) throws Exception {
        log.info("prepare alipay rechargeOrder，orderNum is " + orderNum + ",time is " + DateTimeUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        Assert.notNull(orderNum, "orderNo can not be null");
        ApiResult apiResult = new ApiResult();
        String clientIp = HttpClientService.getIpAddr(request);
        //获取订单
        Recharge order = this.rechargeDao.getRechargeByNum(orderNum);
        if (null != order) {
            Assert.state(order.getState().equals(Recharge.STATE_WAITINT_PAY), "rechargeOrder state is not waitting_pay");
            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest alipayTradeAppPayRequest = new AlipayTradeAppPayRequest();
            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            //model.setBody("可选，对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。");
            model.setSubject(CONSTANT.RECHARGE_ORDER_PAYINFO_PREFIX + "-" + order.getNumber());
            model.setOutTradeNo(order.getNumber());
            model.setTimeoutExpress(AlipayConfig.TIMEOUT_EXPRESS);
            //订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
            model.setTotalAmount(new BigDecimal(order.getAmount()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            model.setProductCode(AlipayConfig.PRODUCT_CODE);
            //公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝会在异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝
            model.setPassbackParams(URLEncoder.encode(clientIp, "UTF-8"));
            alipayTradeAppPayRequest.setBizModel(model);
            alipayTradeAppPayRequest.setNotifyUrl(AlipayConfig.RECHARGE_ORDER_NOTIFY_URL);
            //request.setReturnUrl();//支付成功后跳转的页面
            try {
                //这里和普通的接口调用不同，使用的是sdkExecute
                AlipayTradeAppPayResponse response = alipayClient.sdkExecute(alipayTradeAppPayRequest);
                log.info("充值单号：" + order.getNumber() + "，返回的支付宝订单参数是：" + response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
                apiResult.setCode(ResponseEnum.SUCCESS.getValue());
                apiResult.setMessage(ResponseEnum.SUCCESS.getDesc());
                apiResult.setData(response.getBody());
            } catch (AlipayApiException e) {
                log.error("充值单号：" + order.getNumber() + "，获取支付宝订单参数时发生错误,错误码：" + e.getErrCode() + "错误消息：" + e.getErrMsg());
                e.printStackTrace();
                apiResult.setCode(ResponseEnum.ERROR.getValue());
                apiResult.setMessage(ResponseEnum.ERROR.getDesc());
            }
        } else {
            log.error("充值单号：" + orderNum + "获取支付宝订单参数时发生错误,未获取该充值订单相关信息");
            apiResult.setCode(ResponseEnum.ORDER_NOT_FOUND.getValue());
            apiResult.setMessage(ResponseEnum.ORDER_NOT_FOUND.getDesc());
        }
        return apiResult;
    }


    /**
     * 充值订单支付宝支付回调
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping("/rechargeOrder/alipayNotify")
    @Transactional(rollbackFor = Exception.class)
    public String rechargeAlipayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("----rechargeOrder aliPayNotify-----pay back-----");
        //获取支付宝POST过来反馈信息
        Map<String, String> params = convertRequestParamsToMap(request.getParameterMap());
        String paramsJson = JSON.toJSONString(params);
        log.info("支付宝回调:" + paramsJson);
        try {
            //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGN_TYPE);
            if (signVerified) {
                log.info("支付宝回调签名认证成功");
                // 按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure
                final Recharge order = this.checkRechargeOrder(params);
                // 另起线程处理业务
                executorService.execute(() -> {
                    String trade_status = params.get("trade_status");
                    // 支付成功
                    if (trade_status.equalsIgnoreCase("TRADE_SUCCESS")
                            || trade_status.equalsIgnoreCase("TRADE_FINISHED")) {
                        // 处理支付成功逻辑
                        String orderNum = order.getNumber();
                        boolean lock = redisLock.lock(orderNum);
                        if (lock) {
                            //spring无法处理thread的事务，声明式事务无效
                            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                            PlatformTransactionManager txManager = webApplicationContext.getBean(PlatformTransactionManager.class);
                            TransactionStatus status = txManager.getTransaction(def);

                            try {
                                //修改支付订单状态，设置支付时间及支付类型
                                order.setState(Recharge.STATE_PAYED);
                                order.setPayType(Recharge.PAY_TYPE_ALIPY);
                                order.setPayTime(new Date());
                                this.rechargeDao.updateById(order);
                                //2.增加用户余额
                                UserFund userFund = userFundDao.selectUserFundByXLock(order.getUserId());
                                userFund.setBalance(userFund.getBalance() + order.getAmount());//余额
                                int givenAmount = this.getGivenAmount(order);
                                userFund.setGiveBalance(userFund.getGiveBalance() + givenAmount);//赠送金额
                                this.userFundDao.updateById(userFund);
                                //3.支付日志记录
                                UserFundLog userFundLog = new UserFundLog();
                                userFundLog.setTradeNumber(orderNum);
                                userFundLog.setFundType(FundTypeEnum.RECHARGE.getValue());
                                userFundLog.setPayType(Recharge.PAY_TYPE_ALIPY);
                                userFundLog.setPayNumber(params.get("trade_no"));
                                userFundLog.setUserId(order.getUserId());
                                userFundLog.setTradeFund(order.getAmount());
                                userFundLog.setClientIp(params.get("passback_params"));
                                userFundLog.setBalance(userFund.getBalance() + order.getAmount());
                                userFundLog.setGivenBalance(userFund.getGiveBalance() + givenAmount);
                                userFundLog.setCreateTime(new Date());
                                this.userFundLogDao.insert(userFundLog);
                                txManager.commit(status); // 提交事务
                                //释放锁
                                redisLock.unlock(orderNum);
                            } catch (Exception e) {
                                log.error("alipay pay back handle failed");
                                txManager.rollback(status);
                            }
                        }
                    } else {
                        log.error("没有处理支付宝回调业务，支付宝交易状态：" + trade_status + ",params:" + paramsJson);
                    }
                });
                // 如果签名验证正确，立即返回success，后续业务另起线程单独处理
                // 业务处理失败，可查看日志进行补偿，跟支付宝已经没多大关系。
                return "success";
            } else {
                log.error("支付宝回调签名认证失败，signVerified=false, paramsJson:" + paramsJson);
                return "failure";
            }
        } catch (AlipayApiException e) {
            log.error("支付宝回调签名认证失败,paramsJson:" + paramsJson + "errorMsg:" + e.getMessage());
            return "failure";
        }
    }


    /**
     * request请求参数转化为map
     *
     * @param reqParamsMap
     * @return
     * @throws UnsupportedEncodingException
     */
    private Map<String, String> convertRequestParamsToMap(Map reqParamsMap) throws UnsupportedEncodingException {
        Map<String, String> paramsMap = new HashMap<>();
        for (Iterator iter = reqParamsMap.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) reqParamsMap.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            paramsMap.put(name, valueStr);
        }
        return paramsMap;
    }

    /**
     * 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
     * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
     * 3、验证app_id是否为该商户本身。上述1、2、3有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。
     * 在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。
     * 在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
     *
     * @param params
     * @throws AlipayApiException
     */
    private TripOrder checkTripOrder(Map<String, String> params) throws AlipayApiException {
        String outTradeNo = params.get("out_trade_no");

        // 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
        TripOrderResultDto tripOrderReDto = tripOrderDao.getTripOrderByNum(outTradeNo);// 这个方法自己实现
        TripOrder order = new TripOrder();
        BeanUtils.copyProperties(tripOrderReDto, order);
        if (order == null) {
            throw new AlipayApiException("out_trade_no错误");
        }

        // 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
        long total_amount = new BigDecimal(params.get("total_amount")).multiply(new BigDecimal(100)).longValue();
        if (total_amount != order.getRealPayAmount().longValue()) {
            throw new AlipayApiException("error total_amount");
        }
        // 4、验证app_id是否为该商户本身。
        if (!params.get("app_id").equals(AlipayConfig.APP_ID)) {
            throw new AlipayApiException("app_id不一致");
        }
        return order;
    }


    private Recharge checkRechargeOrder(Map<String, String> params) throws AlipayApiException {
        String outTradeNo = params.get("out_trade_no");

        // 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
        Recharge order = this.rechargeDao.getRechargeByNum(outTradeNo); // 这个方法自己实现
        if (order == null) {
            throw new AlipayApiException("out_trade_no错误");
        }

        // 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
        long total_amount = new BigDecimal(params.get("total_amount")).multiply(new BigDecimal(100)).longValue();
        if (total_amount != order.getAmount().longValue()) {
            throw new AlipayApiException("error total_amount");
        }
        // 4、验证app_id是否为该商户本身。
        if (!params.get("app_id").equals(AlipayConfig.APP_ID)) {
            throw new AlipayApiException("app_id不一致");
        }
        return order;
    }

    private Deposit checkDepositOrder(Map<String, String> params) throws AlipayApiException {
        String outTradeNo = params.get("out_trade_no");

        // 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
        Deposit deposit = this.depositDao.getDepositByOrderNum(outTradeNo); // 这个方法自己实现
        if (deposit == null) {
            throw new AlipayApiException("out_trade_no错误");
        }

        // 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
        long total_amount = new BigDecimal(params.get("total_amount")).multiply(new BigDecimal(100)).longValue();
        if (total_amount != deposit.getAmount().longValue()) {
            throw new AlipayApiException("error total_amount");
        }
        // 4、验证app_id是否为该商户本身。
        if (!params.get("app_id").equals(AlipayConfig.APP_ID)) {
            throw new AlipayApiException("app_id不一致");
        }
        return deposit;
    }

    /**
     * 指定押金付款单号，获取支付宝支付订单信息
     *
     * @param orderNum
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/getPrepayId/depositOrder/{orderNum}")
    public ApiResult alipayDepositOrder(@PathVariable("orderNum") String orderNum, HttpServletRequest request) throws Exception {
        log.info("prepare alipay depositOrder，orderNum is " + orderNum + ",time is " + DateTimeUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        Assert.notNull(orderNum, "orderNo can not be null");
        ApiResult apiResult = new ApiResult();
        String clientIp = HttpClientService.getIpAddr(request);
        //获取订单
        Deposit deposit = this.depositDao.getDepositByOrderNum(orderNum);
        if (null != deposit) {
            Assert.state(deposit.getPayState().equals(Deposit.STATE_WAITING_PAY), "depositOrder state is not waitting_pay");
            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest alipayTradeAppPayRequest = new AlipayTradeAppPayRequest();
            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            //model.setBody("可选，对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。");
            model.setSubject(CONSTANT.DEPOSIT_ORDER_PAYINFO_PREFIX + "-" + deposit.getOrderNum());
            model.setOutTradeNo(deposit.getOrderNum());
            model.setTimeoutExpress(AlipayConfig.TIMEOUT_EXPRESS);
            //订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
            model.setTotalAmount(new BigDecimal(deposit.getAmount()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            model.setProductCode(AlipayConfig.PRODUCT_CODE);
            //公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝会在异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝
            model.setPassbackParams(URLEncoder.encode(clientIp, "UTF-8"));
            alipayTradeAppPayRequest.setBizModel(model);
            alipayTradeAppPayRequest.setNotifyUrl(AlipayConfig.DEPOSIT_ORDER_NOTIFY_URL);
            //request.setReturnUrl();//支付成功后跳转的页面
            try {
                //这里和普通的接口调用不同，使用的是sdkExecute
                AlipayTradeAppPayResponse response = alipayClient.sdkExecute(alipayTradeAppPayRequest);
                log.info("押金支付单号：" + deposit.getOrderNum() + "，返回的支付宝订单参数是：" + response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
                apiResult.setCode(ResponseEnum.SUCCESS.getValue());
                apiResult.setMessage(ResponseEnum.SUCCESS.getDesc());
                apiResult.setData(response.getBody());
            } catch (AlipayApiException e) {
                log.error("押金支付单号：" + deposit.getOrderNum() + "，获取支付宝订单参数时发生错误,错误码：" + e.getErrCode() + "错误消息：" + e.getErrMsg());
                e.printStackTrace();
                apiResult.setCode(ResponseEnum.ERROR.getValue());
                apiResult.setMessage(ResponseEnum.ERROR.getDesc());
            }
        } else {
            log.error("押金支付单号：" + orderNum + "获取支付宝订单参数时发生错误,未获取该充值订单相关信息");
            apiResult.setCode(ResponseEnum.ORDER_NOT_FOUND.getValue());
            apiResult.setMessage(ResponseEnum.ORDER_NOT_FOUND.getDesc());
        }
        return apiResult;
    }

    /**
     * 押金缴纳支付宝支付回调
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping("/depositOrder/alipayNotify")
    @Transactional(rollbackFor = Exception.class)
    public String depositAlipayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("----depositOrder aliPayNotify-----pay back-----");
        //获取支付宝POST过来反馈信息
        Map<String, String> params = convertRequestParamsToMap(request.getParameterMap());
        String paramsJson = JSON.toJSONString(params);
        log.info("支付宝回调:" + paramsJson);
        try {
            //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGN_TYPE);
            if (signVerified) {
                log.info("支付宝回调签名认证成功");
                // 按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure
                final Deposit deposit = this.checkDepositOrder(params);
                // 另起线程处理业务
                executorService.execute(() -> {
                    String trade_status = params.get("trade_status");
                    // 支付成功
                    if (trade_status.equalsIgnoreCase("TRADE_SUCCESS")
                            || trade_status.equalsIgnoreCase("TRADE_FINISHED")) {
                        // 处理支付成功逻辑
                        String orderNum = deposit.getOrderNum();
                        boolean lock = redisLock.lock(orderNum);
                        if (lock) {
                            //spring无法处理thread的事务，声明式事务无效
                            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                            PlatformTransactionManager txManager = webApplicationContext.getBean(PlatformTransactionManager.class);
                            TransactionStatus status = txManager.getTransaction(def);

                            try {
                                //修改支付订单状态，设置支付时间及支付类型
                                deposit.setType(Deposit.DEPOSIT_CASH);
                                deposit.setPayState(Deposit.STATE_PAYED);
                                deposit.setPayType(Deposit.PAY_TYPE_ALIPY);
                                deposit.setPayTime(new Date());
                                deposit.setUpdateTime(new Date());
                                this.depositDao.updateById(deposit);
                                //2.押金支付日志记录
                                UserDepositLog userDepositLog = new UserDepositLog();
                                userDepositLog.setUserId(deposit.getUserId());
                                userDepositLog.setPayType(UserDepositLog.PAY_TYPE_ALIPY);
                                userDepositLog.setPayNumber(orderNum);
                                userDepositLog.setTradeNumber(params.get("trade_no"));
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
                                    preferentialService.insertPreferential(user.getId(), list);
                                }
                                txManager.commit(status); // 提交事务
                                //释放锁
                                redisLock.unlock(orderNum);
                            } catch (Exception e) {
                                log.error("alipay pay back handle failed");
                                txManager.rollback(status);
                            }
                        }
                    } else {
                        log.error("没有处理支付宝回调业务，支付宝交易状态：" + trade_status + ",params:" + paramsJson);
                    }
                });
                // 如果签名验证正确，立即返回success，后续业务另起线程单独处理
                // 业务处理失败，可查看日志进行补偿，跟支付宝已经没多大关系。
                return "success";
            } else {
                log.error("支付宝回调签名认证失败，signVerified=false, paramsJson:" + paramsJson);
                return "failure";
            }
        } catch (AlipayApiException e) {
            log.error("支付宝回调签名认证失败,paramsJson:" + paramsJson + "errorMsg:" + e.getMessage());
            return "failure";
        }
    }
}
