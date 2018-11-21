package cn.com.izj.controller;

import cn.com.izj.base.controller.BaseController;
import cn.com.izj.dto.RoleInfo;
import cn.com.izj.dto.UserInfo;
import cn.com.izj.form.BatchSmsForm;
import cn.com.izj.properties.AuthorizeConstants;
import cn.com.izj.service.RoleService;
import cn.com.izj.service.SmsService;
import cn.com.izj.service.UserService;
import cn.com.izj.support.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author GouBo
 * @date 2018/8/19
 */
@RestController
@RequestMapping("/system/sms")
public class SmsController extends BaseController {

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Autowired
    SmsService smsService;

    @PostMapping
    public Object batch(@RequestBody BatchSmsForm batchSmsForm) {
        String[] mobiles = batchSmsForm.getMobiles();
        if (mobiles == null || mobiles.length == 0) {
            RoleInfo roleInfo = roleService.getRoleByName(AuthorizeConstants.GENERAL);
            List<UserInfo> userInfos = userService.findByRoleId(roleInfo.getId());
            String[] names = userInfos.stream().map(UserInfo::getUsername).toArray(String[]::new);
            batchSmsForm.setMobiles(names);
        }

        smsService.smsSend(batchSmsForm.getMobiles(), batchSmsForm.getContent());
        return new SimpleResponse("ok");
    }
}
