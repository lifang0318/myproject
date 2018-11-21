package cn.com.izj.event;

import cn.com.izj.event.dto.GPSInfo;
import org.springframework.context.ApplicationEvent;

/**
 * 上报车辆信息，GPS点位等
 * Created by GouBo on 2018/7/7 10:27.
 */
public class ReportCarInfoEvent extends ApplicationEvent {
    public ReportCarInfoEvent(GPSInfo gpsInfo) {
        super(gpsInfo);
    }
}
