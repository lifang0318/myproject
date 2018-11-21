package cn.com.izj.event;

import cn.com.izj.event.dto.BluePassword;
import org.springframework.context.ApplicationEvent;

/**
 * 蓝牙密码上报
 * Created by GouBo on 2018/9/2 17:15.
 */
public class ReportBluePasswordEvent extends ApplicationEvent {
    public ReportBluePasswordEvent(BluePassword bluePassword) {
        super(bluePassword);
    }
}
