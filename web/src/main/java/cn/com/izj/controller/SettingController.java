package cn.com.izj.controller;

import cn.com.izj.base.controller.BaseController;
import cn.com.izj.base.entity.GlobalSetting;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.dto.UpdateSetting;
import cn.com.izj.entity.User;
import cn.com.izj.service.SettingService;
import cn.com.izj.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author: 朱鸿平
 * @date: 2018/7/14 10:31
 */
@RestController
@RequestMapping("/system/setting")
public class SettingController extends BaseController {

    @Autowired
    private SettingService settingService;

    /**
     * 获取配置信息
     */
    @GetMapping("/getSetting")
    public ApiResult getSettingList() {
        return settingService.getSettingList();
    }

    /**
     * 更新配置
     */
    @PutMapping("/updateSetting")
    public ApiResult updateSetting(@RequestBody UpdateSetting updateSetting) {
        User user = (User) getUser();
        updateSetting.setModifyUser(user.getId());
        return settingService.updateSetting(updateSetting);
    }

    /**
     * 禁用配置
     */
    @PutMapping("/closeSetting")
    public ApiResult closeSetting(@RequestBody Map<String, Object> params) {
        Long id = StringUtil.getLong(params.get("id"));
        User user = (User) getUser();
        return settingService.closeSetting(id, user.getId());
    }

    /**
     * 启用配置
     */
    @PutMapping("/openSetting")
    public ApiResult openSetting(@RequestBody Map<String, Object> params) {
        Long id = StringUtil.getLong(params.get("id"));
        User user = (User) getUser();
        return settingService.openSetting(id, user.getId());
    }

    /**
     * 新增配置
     */
    @PostMapping("/insertSetting")
    public ApiResult insertSetting(@RequestBody GlobalSetting setting) {
        User user = (User) getUser();
        setting.setModifyUser(user.getId());
        return settingService.insertSetting(setting);
    }

}
