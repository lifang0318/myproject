package cn.com.izj.controller.mobile;

import cn.com.izj.base.controller.BaseController;
import cn.com.izj.base.entity.pay.Recharge;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.entity.User;
import cn.com.izj.service.RechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @author: lifang
 * @description: 用户充值
 * @date: Created in 2018/7/21 7:36
 * @version:
 */
@RestController
@RequestMapping("/mobile/recharge")
public class RechargeMobileController extends BaseController {

    @Autowired
    private RechargeService rechargeService;

    /**
     * 创建充值订单
     *
     * @param recharge
     * @return
     * @throws Exception
     */
    @PostMapping("/create")
    public ApiResult createRecharge(@RequestBody Recharge recharge) throws Exception {
        User user = (User) getUser();
        recharge.setUserId(user.getId());
        return this.rechargeService.createRecharge(recharge);
    }

    /**
     * 获取用户充值记录
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/getRechargesByUserId")
    public ApiResult getRechargesByUserId() throws Exception {
        User user = (User) getUser();
        return this.rechargeService.getRechargesByUserId(user.getId());
    }
}
