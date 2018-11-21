package cn.com.izj.event;

import cn.com.izj.event.dto.BluePassword;
import cn.com.izj.event.dto.DeviceOnline;
import cn.com.izj.event.dto.GPSInfo;
import cn.com.izj.event.dto.KeyboardPassword;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * Created by GouBo on 2018/7/7 10:35.
 */
@Component
public class DeviceEventPublish implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher tradeEventPublisher;

    /**
     * 推送密码键盘密码
     *
     * @param keyboardPassword
     */
    public void publishKeyboardPasswordEvent(KeyboardPassword keyboardPassword) {
        this.tradeEventPublisher.publishEvent(new KeyboardPasswordEvent(keyboardPassword));
    }

    /**
     * 上报设备蓝牙密码
     *
     * @param bluePassword
     */
    public void publishReportBluePasswordEvent(BluePassword bluePassword) {
        this.tradeEventPublisher.publishEvent(new ReportBluePasswordEvent(bluePassword));
    }

    /**
     * 推送车辆信息，GPS点位等
     *
     * @param gpsInfo 位置信息
     */
    public void publishReportCarInfoEvent(GPSInfo gpsInfo) {
        this.tradeEventPublisher.publishEvent(new ReportCarInfoEvent(gpsInfo));
    }

    /**
     * 上报设备上线
     *
     * @param deviceOnline 设备上线信息
     */
    public void publishDeviceOnlineEvent(DeviceOnline deviceOnline) {
        this.tradeEventPublisher.publishEvent(new DeviceOnlineEvent(deviceOnline));
    }

    /**
     * 上报设备离线
     *
     * @param deviceId 设备id
     */
    public void publishDeviceOfflineEvent(String deviceId) {
        this.tradeEventPublisher.publishEvent(new DeviceOfflineEvent(deviceId));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.tradeEventPublisher = applicationEventPublisher;
    }
}
