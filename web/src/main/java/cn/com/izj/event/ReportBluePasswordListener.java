package cn.com.izj.event;

import cn.com.izj.entity.DevicePassword;
import cn.com.izj.event.dto.BluePassword;
import cn.com.izj.service.DevicePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by GouBo on 2018/9/2 17:32.
 */
@Component
public class ReportBluePasswordListener implements ApplicationListener<ReportBluePasswordEvent> {

    @Autowired
    private DevicePasswordService devicePasswordService;

    @Override
    public void onApplicationEvent(ReportBluePasswordEvent event) {
        BluePassword bluePassword = (BluePassword) event.getSource();

        DevicePassword devicePassword = new DevicePassword();
        devicePassword.setDeviceId(bluePassword.getDeviceId());
        devicePassword.setBluePassword(bluePassword.getBluePassword());

        devicePasswordService.upsert(devicePassword);
    }
}
