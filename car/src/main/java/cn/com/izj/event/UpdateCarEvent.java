package cn.com.izj.event;

import cn.com.izj.base.entity.car.CarCommonInfo;
import cn.com.izj.base.entity.car.ReservationCar;
import cn.com.izj.dao.CarCommonDao;
import cn.com.izj.dao.ReservationCarDao;
import cn.com.izj.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.TimerTask;

/**
 * @author: 朱鸿平
 * @date: 2018/7/21 11:42
 */
public class UpdateCarEvent extends TimerTask {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private CarCommonDao carCommonDao;
    private ReservationCarDao reservationCarDao;

    private Long reservationId;//预约id
    private Long userId;//用户id

    public UpdateCarEvent(Long reservationId, Long userId) {
        super();
        carCommonDao = (CarCommonDao) SpringContextUtil.getBean("carCommonDao");
        reservationCarDao = (ReservationCarDao) SpringContextUtil.getBean("reservationCarDao");
        this.reservationId = reservationId;
        this.userId = userId;
    }

    public UpdateCarEvent() {
        super();
    }

    @Override
    @Transactional
    public void run() {
        int result = carCommonDao.updateReservationCarState(reservationId, CarCommonInfo.STATE_FREE);
        reservationCarDao.updateUserReservation(userId, ReservationCar.RESERVATION_OVER_TIME);
        if (result != 1) {
            logger.error("预约订单id: " + reservationId + " 超时,更新车辆状态失败");
        }
    }
}
