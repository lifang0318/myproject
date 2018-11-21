package cn.com.izj.service;

import cn.com.izj.base.entity.order.TripOrder;
import cn.com.izj.base.entity.pay.Deposit;
import cn.com.izj.base.entity.pay.UserDepositLog;
import cn.com.izj.base.enums.ResponseEnum;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.base.service.BaseService;
import cn.com.izj.condition.TripOrderFindCondition;
import cn.com.izj.config.AlipayConfig;
import cn.com.izj.config.WechatPayConfig;
import cn.com.izj.dao.DepositDao;
import cn.com.izj.dao.TripOrderDao;
import cn.com.izj.dao.UserDepositLogDao;
import cn.com.izj.dto.TripOrderResultDto;
import cn.com.izj.utils.WeChatUtil;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author: lifang
 * @description:退款-service
 * @date: Created in 2018/8/24 23:34
 * @version:
 */
@Service
public class RefundService extends BaseService {

    @Autowired
    private DepositDao depositDao;

    @Autowired
    private TripOrderDao tripOrderDao;

    @Autowired
    private UserDepositLogDao userDepositLogDao;

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
     * 押金退还（微信和支付宝通用接口）
     *
     * @param orderNum
     * @return
     */
    @Transactional
    public ApiResult depositRefund(String orderNum) throws Exception {
        Assert.notNull(orderNum, "orderNum can not be null");
        ApiResult apiResult = new ApiResult();
        Deposit deposit = this.depositDao.getDepositByOrderNum(orderNum);
        if (null != deposit) {
            JSONObject jsonObject = checkUserOrders(deposit.getUserId());
            if (!jsonObject.get("result").equals("SUCCESS")) {
                apiResult.setCode(ResponseEnum.ERROR.getValue());
                apiResult.setMessage(jsonObject.getString("msg"));
                return apiResult;
            }
            if (deposit.getPayState() == Deposit.STATE_PAYED) {
                if (Deposit.PAY_TYEP_WECHAT.equals(deposit.getPayType())) {//微信
                    apiResult = this.refundDepositWechat(deposit);
                } else {//支付宝
                    apiResult = this.refundDepositAlipay(deposit);
                }
            } else {
                apiResult.setCode(ResponseEnum.ERROR.getValue());
                apiResult.setMessage("单号为:" + deposit.getOrderNum() + "的押金订单尚未支付");
                log.info("单号为:" + deposit.getOrderNum() + "的押金订单尚未支付");
            }
        } else {
            apiResult.setCode(ResponseEnum.ORDER_NOT_FOUND.getValue());
            apiResult.setMessage("未获取到单号为:" + deposit.getOrderNum() + "的押金信息");
            log.info("未获取到单号为:" + deposit.getOrderNum() + "的押金信息");
        }
        return apiResult;
    }

    /**
     * 微信押金退还
     *
     * @param deposit
     * @return
     * @throws Exception
     */
    private ApiResult refundDepositWechat(Deposit deposit) throws Exception {
        ApiResult apiResult = new ApiResult();
        //组装退款请求参数
        String now = System.currentTimeMillis() + "" + deposit.getOrderNum().subSequence(deposit.getOrderNum().length() - 6, deposit.getOrderNum().length());
        SortedMap<String, String> paramap = new TreeMap<>();
        paramap.put("appid", WechatPayConfig.APPID);
        paramap.put("mch_id", WechatPayConfig.MCH_ID);
        paramap.put("nonce_str", WeChatUtil.getRandomString(7));
        paramap.put("out_trade_no", deposit.getOrderNum());
        paramap.put("out_refund_no", now);
        paramap.put("total_fee", deposit.getAmount().toString());
        paramap.put("refund_fee", deposit.getAmount().toString());
        paramap.put("refund_desc", "用户押金退还");
        paramap.put("sign", WeChatUtil.createSign(WechatPayConfig.CLIENT_SECRET, "UTF-8", paramap));
        String reqStr = WeChatUtil.creatXml(paramap);
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        String certFilePath = WechatPayConfig.CERTIFICATE_File_Path;//退款证书文件路径
        File certFile = new File(certFilePath);
        if (!certFile.exists()) {
            apiResult.setCode(ResponseEnum.ERROR.getValue());
            apiResult.setMessage("商户证书文件不存在");
            log.info(certFilePath + "路径下不存在指定的商户证书文件");
        } else {
            FileInputStream instream = null;
            try {
                instream = new FileInputStream(new File(certFilePath));
                keyStore.load(instream, WechatPayConfig.MCH_ID.toCharArray());
                SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, WechatPayConfig.MCH_ID.toCharArray()).build();
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                        sslcontext,
                        new String[]{"TLSv1"},
                        null,
                        SSLConnectionSocketFactory.getDefaultHostnameVerifier());
                CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
                try {
                    HttpPost httpPost = new HttpPost(WechatPayConfig.WECHAT_ORDER_REFUND_URL);//退款接口
                    StringEntity reqEntity = new StringEntity(reqStr, ContentType.APPLICATION_JSON);
                    // 设置类型
//                    reqEntity.setContentType("application/x-www-form-urlencoded");
                    httpPost.setEntity(reqEntity);
                    CloseableHttpResponse response = httpclient.execute(httpPost);
                    try {
                        HttpEntity entity = response.getEntity();
                        if (entity != null) {
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
                            String line;
                            String resultMessage = "";
                            for (line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                                resultMessage = resultMessage + line;
                            }
                            SortedMap xmlToSortedMap = WeChatUtil.xmlToSortedMap(resultMessage);
                            SortedMap<String, String> dataMap = (SortedMap<String, String>) xmlToSortedMap.get("xml");
                            if (null != dataMap.get("return_code") && dataMap.get("return_code").equalsIgnoreCase("SUCCESS")
                                    && null != dataMap.get("result_code") && dataMap.get("result_code").equalsIgnoreCase("SUCCESS")) {
                                //成功后，更改押金状态
                                deposit.setPayState(Deposit.STATE_REFUND);
                                deposit.setUpdateTime(new Date());
                                deposit.setRefundNumber(dataMap.get("out_refund_no"));
                                this.depositDao.updateById(deposit);
                                //记录押金退还日志
                                UserDepositLog depositLog = new UserDepositLog();
                                depositLog.setTradeNumber(dataMap.get("refund_id"));//微信系统生成的退款单号
                                depositLog.setPayNumber(dataMap.get("out_refund_no"));//商户系统内部退款单号
                                depositLog.setPayType(Deposit.PAY_TYPE_ALIPY);
                                depositLog.setAmount(deposit.getAmount());
                                depositLog.setCreateTime(new Date());
                                depositLog.setUserId(deposit.getUserId());
                                depositLog.setType(Deposit.STATE_REFUND);
                                this.userDepositLogDao.insert(depositLog);
                                apiResult.setCode(ResponseEnum.SUCCESS.getValue());
                                apiResult.setMessage("退款成功");
                            }
                        }
                        EntityUtils.consume(entity);
                    } finally {
                        response.close();
                    }
                } finally {
                    httpclient.close();
                }
            } catch (Exception ex) {
                apiResult.setCode(ResponseEnum.ERROR.getValue());
                apiResult.setMessage(ex.getMessage());
                log.info(ex.getMessage());
            } finally {
                if (null != instream) {
                    try {
                        instream.close();
                    } catch (IOException ex) {
                        apiResult.setCode(ResponseEnum.ERROR.getValue());
                        apiResult.setMessage(ex.getMessage());
                        log.info(ex.getMessage());
                    }
                }
            }
        }
        return apiResult;
    }

    /**
     * 支付宝押金退还
     *
     * @param deposit
     * @return
     * @throws Exception
     */
    private ApiResult refundDepositAlipay(Deposit deposit) throws Exception {
        ApiResult apiResult = new ApiResult();
        AlipayTradeRefundModel refundModel = new AlipayTradeRefundModel();
        refundModel.setOutTradeNo(deposit.getOrderNum());
        refundModel.setRefundAmount(new BigDecimal(deposit.getAmount()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        refundModel.setRefundReason("用户押金退还");
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizModel(refundModel);
        try {
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            if ("10000".equals(response.getCode())) {
                //成功后，更改押金状态
                deposit.setPayState(Deposit.STATE_REFUND);
                deposit.setRefundNumber(response.getTradeNo());
                deposit.setUpdateTime(new Date());
                this.depositDao.updateById(deposit);
                //记录押金退还日志
                UserDepositLog depositLog = new UserDepositLog();
                depositLog.setTradeNumber(response.getTradeNo());
                depositLog.setPayNumber(response.getOutTradeNo());
                depositLog.setPayType(Deposit.PAY_TYPE_ALIPY);
                depositLog.setAmount(deposit.getAmount());
                depositLog.setCreateTime(new Date());
                depositLog.setUserId(deposit.getUserId());
                depositLog.setType(Deposit.STATE_REFUND);
                this.userDepositLogDao.insert(depositLog);
                apiResult.setCode(ResponseEnum.SUCCESS.getValue());
                apiResult.setMessage("退款成功");
            } else {
                apiResult.setCode(ResponseEnum.ERROR.getValue());
                apiResult.setMessage(response.getSubMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("支付宝退款错误！" + e.getMessage());
            return new ApiResult(ResponseEnum.ERROR.getValue(), ResponseEnum.ERROR.getDesc());
        }

        return apiResult;
    }

    /**
     * 检查是否存在未完成或未满一个月的订单
     *
     * @param userId
     * @return
     * @throws Exception
     */
    private JSONObject checkUserOrders(Long userId) throws Exception {
        JSONObject jo = new JSONObject();
        jo.put("result", "SUCCESS");
        TripOrderFindCondition condition = new TripOrderFindCondition();
        condition.setUserId(userId);
        List<TripOrderResultDto> orders = this.tripOrderDao.findTripOrdersByCondition(condition);
        if (CollectionUtils.isNotEmpty(orders)) {
            //检查是否有未完成的订单
            List<TripOrderResultDto> unFinishedOrders = orders.stream().filter(order -> !order.getStatus().equals(TripOrder.STATUS_COMPLETE)).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(unFinishedOrders)) {
                jo.put("result", "FAIL");
                jo.put("msg", "存在未完成订单,押金退还失败！");
            }
            //检查已完成订单是否有结束时间未满1个月的订单
            List<TripOrderResultDto> finishedOrders = orders.stream().filter(order -> order.getStatus().equals(TripOrder.STATUS_COMPLETE)).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(finishedOrders)) {
                finishedOrders.sort((order1, order2) -> {
                    long time1 = order1.getEndTime().getTime();
                    long time2 = order2.getEndTime().getTime();
                    if (time1 < time2) {
                        return 1;
                    } else if (time1 > time2) {
                        return -1;
                    }
                    return 0;
                });
                TripOrderResultDto order = finishedOrders.get(0);
                long time = order.getEndTime().getTime();
                long now = System.currentTimeMillis();
                if ((now - time) <= 1000L * 3600 * 24 * 30) {
                    jo.put("result", "FAIL");
                    jo.put("msg", "存在未满一个月的订单，押金退还失败！");
                }
            }
        }
        return jo;
    }
}
