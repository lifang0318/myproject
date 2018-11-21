package cn.com.izj.event;

import cn.com.izj.base.entity.car.CarCommonInfo;
import cn.com.izj.base.entity.car.OriginContrail;
import cn.com.izj.dao.CarCommonDao;
import cn.com.izj.dao.OriginContrailDao;
import cn.com.izj.event.dto.GPSInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by GouBo on 2018/7/7 10:30.
 */
@Component
public class ReportCarInfoListener implements ApplicationListener<ReportCarInfoEvent> {

    private static Logger logger = LoggerFactory.getLogger(ReportCarInfoListener.class);

    @Autowired
    private OriginContrailDao originContrailDao;

    @Autowired
    private CarCommonDao carCommonDao;

    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    @Override
    public void onApplicationEvent(ReportCarInfoEvent event) {
        executorService.execute(() -> {
            try {
                GPSInfo gpsInfo = (GPSInfo) event.getSource();
                Assert.notNull(gpsInfo, "车辆位置信息上报失败");
                //获取对应设备的车辆id
                CarCommonInfo  carCommonInfo = carCommonDao.getCarIdByDeviceId(gpsInfo.getDeviceId());

                if (carCommonInfo == null) {
                    logger.info("未获取到设备id为" + gpsInfo.getDeviceId() + "对应的车辆信息");
                    return;
                }
                if(carCommonInfo.getCarState() != CarCommonInfo.STATE_IN_USE){
                    return;
                }
                OriginContrail originContrail = new OriginContrail();
                originContrail.setCarId(carCommonInfo.getId());
                originContrail.setDeviceId(gpsInfo.getDeviceId());
                originContrail.setLongitude(gpsInfo.getLongitude());
                originContrail.setLatitude(gpsInfo.getLatitude());
                originContrail.setDirection(gpsInfo.getDirection());
                originContrail.setSpeed(gpsInfo.getSpeed());
                originContrail.setCreateTime(gpsInfo.getTime());
                originContrail.setTotalMileage(gpsInfo.getTotalMileage());
                originContrailDao.insert(originContrail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
