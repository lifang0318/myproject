package cn.com.izj.controller.mobile;

import cn.com.izj.base.controller.BaseController;
import cn.com.izj.base.entity.preferential.UserPreferential;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.dto.UserPreferentialCondition;
import cn.com.izj.service.UserPreferentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lifang
 * @description:优惠券-controller(APP端调用)
 * @date: Created in 2018/7/14 0:01
 * @version:
 */
@RestController
@RequestMapping("/mobile/preferential")
public class PrefentialMobileController extends BaseController{

    @Autowired
    private UserPreferentialService userPreferentialService;

    /**
     * 获取用户所有的优惠券
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/findAllPreferential")
    public ApiResult findAllPreferential(@RequestParam("userId") Long userId) throws Exception {
        UserPreferentialCondition condition = new UserPreferentialCondition();
        condition.setUserId(userId);
        return this.userPreferentialService.findByCondition(condition);
    }

    /**
     * 获取用户所有可用的优惠券
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/findUnUsePreferential")
    public ApiResult findUnUsePreferential(@RequestParam("userId") Long userId) throws Exception {
        UserPreferentialCondition condition = new UserPreferentialCondition();
        condition.setState(UserPreferential.STATE_UNUSE);
        condition.setUserId(userId);
        return this.userPreferentialService.findUnUsePreferential(condition);
    }

    /**
     * 获取用户所有已使用的优惠券
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @GetMapping("/findUsedPreferential")
    public ApiResult findUsedPreferential(@RequestParam("userId") Long userId) throws Exception {
        UserPreferentialCondition condition = new UserPreferentialCondition();
        condition.setUserId(userId);
        condition.setState(UserPreferential.STATE_USED);
        return this.userPreferentialService.findByCondition(condition);
    }
}
