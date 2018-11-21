package cn.com.izj.event;

import cn.com.izj.base.entity.DeviceInfo;
import cn.com.izj.dao.DeviceInfoDao;
import cn.com.izj.event.dto.DeviceOnline;
import cn.com.izj.service.CarInfoService;
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
public class DeviceOfflineListener implements ApplicationListener<DeviceOfflineEvent> {
    @Autowired
    private CarInfoService carInfoService;
    @Autowired
    private DeviceInfoDao deviceInfoDao;

    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    @Override
    public void onApplicationEvent(DeviceOfflineEvent event) {
        executorService.execute(() -> {
            try {
                DeviceOnline deviceOnline = (DeviceOnline) event.getSource();
                String deviceId = deviceOnline.getDeviceId();
                DeviceInfo deviceInfo = deviceInfoDao.getDeviceInfo(deviceId);
                if (deviceInfo == null) {
                    deviceInfo = new DeviceInfo();
                    deviceInfo.setCcid(deviceOnline.getCcid());
                    deviceInfo.setDeviceId(deviceId);
                    deviceInfo.setState(DeviceInfo.OFFLINE);
                    deviceInfo.setCreateTime(new Date());
                    deviceInfoDao.insert(deviceInfo);
                } else {
                    carInfoService.updateDeviceState(deviceId, DeviceInfo.OFFLINE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}