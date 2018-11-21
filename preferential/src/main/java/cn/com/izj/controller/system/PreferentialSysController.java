package cn.com.izj.controller.system;

import cn.com.izj.base.controller.BaseController;
import cn.com.izj.base.entity.preferential.Preferential;
import cn.com.izj.base.enums.ResponseEnum;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.dto.PreferentialCondition;
import cn.com.izj.dto.UserPreferentialInsertBean;
import cn.com.izj.service.PreferentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author: lifang
 * @description: 优惠券-controller
 * @date: Created in 2018/7/11 23:05
 * @version:
 */
@RestController
@RequestMapping("/system/preferential")
public class PreferentialSysController extends BaseController {

    @Autowired
    private PreferentialService preferentialService;

    /**
     * 录入优惠券
     *
     * @param preferential
     * @return
     * @throws Exception
     */
    @PostMapping("/add")
    public ApiResult addPreferential(@RequestBody Preferential preferential) throws Exception {
        Assert.notNull(preferential, "preferential can not be null");
        ApiResult result = new ApiResult();
//        User user = (User) getUser();
//        preferential.setCreator(user.getUsername());
        preferential.setCreateTime(new Date());
        preferential.setState(Preferential.STATE_UNDISTRIBUTE);
        int i = this.preferentialService.addPreferential(preferential);
        if (i != 1) {
            log.error("save preferential failed");
            result.setCode(ResponseEnum.ERROR.getValue());
            result.setMessage(ResponseEnum.ERROR.getDesc());
        }
        return result;
    }

    /**
     * 修改优惠券前，验证是否已被发放过
     *
     * @param preferentialId
     * @return
     * @throws Exception
     */
    @GetMapping("/isDistribute")
    public Boolean isDistribute(@RequestParam Long preferentialId) throws Exception {
        Assert.notNull(preferentialId, "preferentialId can not be null");
        return this.preferentialService.isDistribute(preferentialId);
    }


    /**
     * 修改优惠券
     *
     * @param preferentialId
     * @param preferential
     * @return
     * @throws Exception
     */
    @PutMapping("/update/{preferentialId}")
    public ApiResult updatePreferential(@PathVariable Long preferentialId, @RequestBody Preferential preferential) throws Exception {
        ApiResult result = new ApiResult();
        preferential.setId(preferentialId);
        int i = this.preferentialService.updatePreferential(preferential);
        if (i != 1) {
            log.error("update preferential failed");
            result.setCode(ResponseEnum.ERROR.getValue());
            result.setMessage(ResponseEnum.ERROR.getDesc());
        }
        return result;
    }

    /**
     * 发放优惠券
     *
     * @param preferentialId
     * @param userPreferentialInsertBean
     * @return
     * @throws Exception
     */
    @PostMapping("/distribute/{preferentialId}")
    public ApiResult distributePreferential(@PathVariable Long preferentialId, @RequestBody UserPreferentialInsertBean userPreferentialInsertBean) throws Exception {
        Assert.notEmpty(userPreferentialInsertBean.getUserIds(), "userIds can not be null");
        return this.preferentialService.distributePreferential(preferentialId, userPreferentialInsertBean);
    }

    /**
     * 获取满足查询条件的优惠券信息
     *
     * @param preferentialCondition
     * @return
     * @throws Exception
     */
    @GetMapping("/findByCondition")
    public ApiResult findByCondition(PreferentialCondition preferentialCondition) throws Exception {
        return this.preferentialService.findByCondition(preferentialCondition);
    }

}
