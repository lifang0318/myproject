package cn.com.izj.event;

import org.springframework.context.ApplicationEvent;

/**
 * 设备下线事件
 * Created by GouBo on 2018/7/16 23:04.
 */
public class DeviceOfflineEvent extends ApplicationEvent {
    public DeviceOfflineEvent(String deviceId) {
        super(deviceId);
    }
}
