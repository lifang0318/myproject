package cn.com.izj.controller.system;

import cn.com.izj.base.controller.BaseController;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.dto.RechargeFindCondition;
import cn.com.izj.service.RechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lifang
 * @description: 用户充值
 * @date: Created in 2018/7/21 7:37
 * @version:
 */
@RestController
@RequestMapping("system/recharge")
public class RechargeSystemController extends BaseController {

    @Autowired
    private RechargeService rechargeService;

    /**
     * 获取充值记录
     *
     * @param condition
     * @return
     * @throws Exception
     */
    @GetMapping("/findRechargeRecords")
    public ApiResult findRecharges(RechargeFindCondition condition) throws Exception {
        return this.rechargeService.findRecharges(condition);
    }

}
