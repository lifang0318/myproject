package cn.com.izj.event;

import cn.com.izj.base.entity.DeviceInfo;
import cn.com.izj.dao.DeviceInfoDao;
import cn.com.izj.event.dto.DeviceOnline;
import cn.com.izj.service.CarInfoService;
import cn.com.izj.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: 朱鸿平
 * @date: 2018/7/20 23:35
 */
@Component
public class DeviceOnlineListener implements ApplicationListener<DeviceOnlineEvent> {

    private static Logger logger = LoggerFactory.getLogger(DeviceOnlineListener.class);

    @Autowired
    private CarInfoService carInfoService;
    @Autowired
    private DeviceInfoDao deviceInfoDao;

    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    @Override
    public void onApplicationEvent(DeviceOnlineEvent event) {
        executorService.execute(() -> {
            try {
                DeviceOnline deviceOnline = (DeviceOnline) event.getSource();
                String deviceId = deviceOnline.getDeviceId();
                DeviceInfo deviceInfo = deviceInfoDao.getDeviceInfo(deviceId);
                if (deviceInfo == null) {
                    deviceInfo = new DeviceInfo();
                    deviceInfo.setCcid(deviceOnline.getCcid());
                    deviceInfo.setDeviceId(deviceId);
                    deviceInfo.setState(DeviceInfo.ONLINE);
                    deviceInfo.setCreateTime(new Date());
                    deviceInfoDao.insert(deviceInfo);
                } else {
                    carInfoService.updateDeviceState(deviceId, DeviceInfo.ONLINE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
    }
}
