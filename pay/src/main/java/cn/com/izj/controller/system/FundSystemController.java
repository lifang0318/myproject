package cn.com.izj.controller.system;

import cn.com.izj.base.controller.BaseController;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.dto.AuditInfo;
import cn.com.izj.entity.User;
import cn.com.izj.service.UserFundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 朱鸿平
 * @date: 2018/7/1 19:22
 */
@RestController
@RequestMapping("/system/fund")
public class FundSystemController extends BaseController {

    @Autowired
    private UserFundService userFundService;

    /**
     * 提现申请列表
     */
    @Deprecated
    @GetMapping("/withdrawList")
    public ApiResult getWithdrawList() {
        return userFundService.getWithdrawList();
    }

    /**
     * 根据提现单号获取提现信息
     */
    @Deprecated
    @GetMapping("/getWithdrawInfo/{tradeNumber}")
    public ApiResult getWithdrawInfo(@PathVariable String tradeNumber) {
        return userFundService.getWithdrawInfo(tradeNumber);
    }

    /**
     * 提现审核
     *
     * @param auditInfo 审核实体
     */
    @Deprecated
    @PutMapping("/auditWithdraw")
    public ApiResult auditWithdraw(@RequestBody AuditInfo auditInfo) {
        User user = (User) getUser();
        auditInfo.setAuditorId(user.getId());
        return userFundService.auditWithdraw(auditInfo);
    }

}
