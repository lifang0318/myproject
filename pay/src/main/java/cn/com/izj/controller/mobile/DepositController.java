package cn.com.izj.controller.mobile;

import cn.com.izj.base.controller.BaseController;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.dto.ApplicationCash;
import cn.com.izj.dto.DepositInfo;
import cn.com.izj.entity.User;
import cn.com.izj.service.DepositService;
import cn.com.izj.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 朱鸿平
 * @date: 2018/8/16 23:27
 */
@RestController
@RequestMapping("/mobile/deposit")
public class DepositController extends BaseController {

    @Autowired
    private DepositService depositService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 查询用户押金信息
     */
    @GetMapping("/userDeposit")
    public ApiResult getUserDeposit() {
        User user = (User) getUser();
        return depositService.getUserDeposit(user.getId());
    }

    /**
     * 创建缴纳押金订单
     */
    @GetMapping("/createDeposit")
    public ApiResult createDeposit() {
        User user = (User) getUser();
        return depositService.createDeposit(user.getId());
    }

    /**
     * 用户退押金 无用接口
     */
    @Deprecated
    @GetMapping("/refundDeposit")
    public ApiResult refundDeposit() {
        User user = (User) getUser();
        return depositService.refundDeposit(user.getId());
    }

    /**
     * 获取押金金额
     */
    @GetMapping("/getDepositAmount")
    public ApiResult getDepositAmount() {
        return depositService.getDepositAmount();
    }

    /**
     * 获取不计免赔额度
     */
    @GetMapping("/getDeductibles")
    public ApiResult getDeductibles() {
        return depositService.getDeductibles();
    }
}
