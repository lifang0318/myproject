package cn.com.izj.controller;

import cn.com.izj.base.controller.BaseController;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.dto.AuditCardInfo;
import cn.com.izj.dto.QueryCondition;
import cn.com.izj.entity.User;
import cn.com.izj.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 朱鸿平
 * @date: 2018/8/14 21:34
 */
@RestController
@RequestMapping("/system/card")
public class UserAuthSystemController extends BaseController {

    @Autowired
    private UserAuthService userAuthService;


    /**
     * 审核身份证信息
     *
     * @param cardInfo
     */
    @PutMapping("/auditIdCard")
    public ApiResult auditIdCard(@RequestBody AuditCardInfo cardInfo) {
        User user = (User) getUser();
        cardInfo.setAuditorId(user.getId());
        Integer result = userAuthService.auditIdCard(cardInfo);
        return ApiResult.validateSingleData(result);
    }

    /**
     * 审核驾驶证信息
     *
     * @param cardInfo
     */
    @PutMapping("/auditDriverCard")
    public ApiResult auditDriverCard(@RequestBody AuditCardInfo cardInfo) {
        User user = (User) getUser();
        cardInfo.setAuditorId(user.getId());
        Integer result = userAuthService.auditDriverCard(cardInfo);
        return ApiResult.validateSingleData(result);
    }

    /**
     * 查询所有用户
     *
     * @param queryCondition
     * @return
     */
    @PostMapping("/getUserList")
    public ApiResult getUserList(@RequestBody QueryCondition queryCondition) {
        return userAuthService.getUserList(queryCondition);
    }

    /**
     * 根据id获取用户信息
     */
    @GetMapping("/userInfo/{userId}")
    public ApiResult getUserInfo(@PathVariable Long userId) {
        return userAuthService.getUserInfo(userId);
    }
}
