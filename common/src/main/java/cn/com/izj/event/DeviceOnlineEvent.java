package cn.com.izj.event;

import cn.com.izj.event.dto.DeviceOnline;
import org.springframework.context.ApplicationEvent;

/**
 * 设备上线事件
 * Created by GouBo on 2018/7/16 23:03.
 */
public class DeviceOnlineEvent extends ApplicationEvent {
    public DeviceOnlineEvent(DeviceOnline deviceOnline) {
        super(deviceOnline);
    }
}
