package cn.com.izj.event;

import cn.com.izj.entity.DevicePassword;
import cn.com.izj.event.dto.KeyboardPassword;
import cn.com.izj.service.DevicePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by GouBo on 2018/9/2 17:33.
 */
@Component
public class KeyboardPasswordListener implements ApplicationListener<KeyboardPasswordEvent> {

    @Autowired
    private DevicePasswordService devicePasswordService;

    @Override
    public void onApplicationEvent(KeyboardPasswordEvent event) {
        KeyboardPassword keyboardPassword = (KeyboardPassword) event.getSource();

        DevicePassword devicePassword = new DevicePassword();
        devicePassword.setDeviceId(keyboardPassword.getDeviceId());
        devicePassword.setKeyboardPassword(keyboardPassword.getPassword());

        devicePasswordService.upsert(devicePassword);
    }
}
