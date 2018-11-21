package cn.com.izj.controller.system;

import cn.com.izj.base.CONSTANT;
import cn.com.izj.base.controller.BaseController;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.condition.TripOrderFindCondition;
import cn.com.izj.dto.OperateDevice;
import cn.com.izj.dto.TripOrderResultDto;
import cn.com.izj.service.TripOrderService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author: lifang
 * @description: 行程订单-controller
 * @date: Created in 2018/8/14 10:00
 * @version:
 */
@RequestMapping("/system/tripOrder")
@RestController
public class TripOrderSystemController extends BaseController {

    @Autowired
    private TripOrderService tripOrderService;

    /**
     * 查询订单集合
     *
     * @param condition 查询条件
     * @return
     */
    @GetMapping("/findTripOrders")
    public Object findTripOrders(TripOrderFindCondition condition) {
        ApiResult result = new ApiResult();
        if(condition.getEndTime() != null){
            DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
            try {
                condition.setEndTime(format.parse(fmt.format(condition.getEndTime())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (condition.getPageSize() != -1) {
            PageInfo pageInfo = this.tripOrderService.findOrdersPageByCondition(condition);
            result.setData(pageInfo);
        } else {
            List<TripOrderResultDto> orders = this.tripOrderService.findTripOrdersByCondition(condition);
            result.setData(orders);
        }
        return result;
    }

    /**
     * 后台打开车门
     */
    @PostMapping("/openCar")
    public ApiResult openCar(@RequestBody OperateDevice operateDevice) {
        operateDevice.setCode(CONSTANT.OPEN_CAR);
        return tripOrderService.openOrLockCar(operateDevice.getCarId(), operateDevice.getCode());
    }

    /**
     * 后台锁车门
     */
    @PostMapping("/lockCar")
    public ApiResult lockCarMoment(@RequestBody OperateDevice operateDevice) {
        operateDevice.setCode(CONSTANT.CLOSE_CAR);
        return tripOrderService.openOrLockCar(operateDevice.getCarId(), operateDevice.getCode());
    }

    /**
     * 后台声音寻车
     */
    @PostMapping("/searchCarBySound")
    public ApiResult searchCarBySound(@RequestBody OperateDevice operateDevice) {
        return tripOrderService.searchCar(operateDevice.getCarId());
    }
}
