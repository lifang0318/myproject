package cn.com.izj.controller;

import cn.com.izj.dto.BluePasswordDto;
import cn.com.izj.event.dto.KeyboardPassword;
import cn.com.izj.service.DevicePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by GouBo on 2018/9/2 18:15.
 */
@RestController
@RequestMapping("/mobile/device/password")
public class PasswordController {

    @Autowired
    private DevicePasswordService devicePasswordService;

    /**
     * 获取设备密码信息
     */
    @GetMapping
    public Object getDevicePassword(Long carId) {
        return devicePasswordService.getDevicePassword(carId);
    }

    /**
     * 设置设备键盘密码
     */
    @PostMapping("/keyboard")
    public Object setKeyboardPassword(@RequestBody KeyboardPassword keyboardPassword) {
        return devicePasswordService.setKeyboardPassword(keyboardPassword.getDeviceId(), keyboardPassword.getPassword());
    }

    /**
     * 重置蓝牙密码
     */
    @PostMapping("/blue")
    public Object resetBluePassword(@RequestBody BluePasswordDto bluePasswordDto) {
        return devicePasswordService.resetBluePassword(bluePasswordDto.getCarId(), bluePasswordDto.getBluePassword());
    }
}
