package cn.com.izj.controller.mobile;

import cn.com.izj.base.controller.BaseController;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.dto.ApplicationCash;
import cn.com.izj.dto.PayOrder;
import cn.com.izj.entity.User;
import cn.com.izj.service.UserFundService;
import cn.com.izj.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 朱鸿平
 * @date: 2018/7/1 19:08
 */
@RestController
@RequestMapping("/mobile/pay")
public class FundMobileController extends BaseController {

    @Autowired
    private UserFundService userFundService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 支付订单（余额支付）
     */
    @PostMapping("/payOrderByBalance")
    public ApiResult payOrderByBalance(@RequestBody PayOrder payOrder) {
        String ipAddr = StringUtil.getIpAddr(request);
        User user = (User) getUser();
        payOrder.setUserId(user.getId());
        return userFundService.payOrderByBalance(payOrder, ipAddr);
    }

    /**
     * 我的钱包
     */
    @GetMapping("/myWallet")
    public ApiResult getMyWallet() {
        User user = (User) getUser();
        return userFundService.getMyWallet(user.getId());
    }

    /**
     * 提现申请
     *
     * @param cash 提现实体类
     */
    @PostMapping("/withdrawDeposit")
    public ApiResult createWithdrawDeposit(@RequestBody ApplicationCash cash) {
        User user = (User) getUser();
        cash.setUserId(user.getId());
        String ipAddr = StringUtil.getIpAddr(request);
        return userFundService.createWithdrawDeposit(cash, ipAddr);
    }
}
