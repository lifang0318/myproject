package cn.com.izj.controller.mobile;

import cn.com.izj.base.CONSTANT;
import cn.com.izj.base.controller.BaseController;
import cn.com.izj.base.entity.order.TripOrder;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.condition.TripOrderFindCondition;
import cn.com.izj.dto.*;
import cn.com.izj.entity.User;
import cn.com.izj.service.TripOrderService;
import cn.com.izj.utils.DateTimeUtils;
import cn.com.izj.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: lifang
 * @description: 行程订单-controller
 * @date: Created in 2018/6/10 12:16
 * @version:
 */
@RequestMapping("/mobile/tripOrder")
@RestController
public class TripOrderMobileController extends BaseController {

    @Autowired
    private TripOrderService tripOrderService;

    /**
     * 生成行程订单记录
     *
     * @param tripOrder
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public Object createTripOrder(@RequestBody TripOrder tripOrder) {
        User user = (User) getUser();
        return this.tripOrderService.createTripOrder(tripOrder,user);
    }

    /**
     * 查询用户所有订单
     *
     * @return
     */
    @GetMapping("/queryAllOrdersByUser")
    public Object queryAllOrdersByUser() {
        ApiResult result = new ApiResult();
        User user = (User) getUser();
        TripOrderFindCondition condition = new TripOrderFindCondition();
        condition.setUserId(user.getId());
        List<TripOrderResultDto> tripOrders = this.tripOrderService.findTripOrdersByCondition(condition);
        result.setData(tripOrders);
        return result;
    }

    /**
     * 查询用户已完成订单
     *
     * @return
     */
    @GetMapping("/queryfinishedOrdersByUser")
    public Object queryfinishedOrdersByUser() {
        ApiResult result = new ApiResult();
        User user = (User) getUser();
        TripOrderFindCondition condition = new TripOrderFindCondition();
        condition.setUserId(user.getId());
        condition.setStatus(TripOrder.STATUS_COMPLETE);
        List<TripOrderResultDto> tripOrders = this.tripOrderService.findTripOrdersByCondition(condition);
        result.setData(tripOrders);
        return result;
    }

    /**
     * 根据车牌号查询订单（支持模糊匹配）
     *
     * @return
     */
    @PostMapping("/queryOrdersByPlateNum")
    public Object queryOrdersByPlateNum(@RequestBody Map<String,Object> params) {
        String plateNumber = StringUtil.getStr(params.get("plateNumber"));
        ApiResult result = new ApiResult();
        List<TripOrderResultDto> tripOrders = this.tripOrderService.queryOrdersByPlateNum(plateNumber);
        result.setData(tripOrders);
        return result;
    }

    /**
     * 根据时间区间查询订单
     *
     * @return
     */
    @GetMapping("/queryOrdersByTimeRange")
    public Object queryOrdersByTimeRange(@DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime, @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime) throws ParseException {
        ApiResult result = new ApiResult();
        User user = (User) getUser();
        Date start = format.parse(DateTimeUtils.formatDate(startTime, "yyyy-MM-dd 00:00:00"));
        Date end = format.parse(DateTimeUtils.formatDate(endTime, "yyyy-MM-dd 23:59:59"));
        List<TripOrderResultDto> tripOrders = this.tripOrderService.queryOrdersByTimeRange(user.getId(), start, end);
        result.setData(tripOrders);
        return result;
    }

    /**
     * 根据订单号查询订单详情
     *
     * @param orderNum 订单号
     * @return
     */
    @GetMapping("/{orderNum}")
    public Object getTripOrderById(@PathVariable String orderNum) {
        ApiResult result = new ApiResult();
        TripOrderResultDto tripOrder = this.tripOrderService.getTripOrderByNum(orderNum);
        result.setData(tripOrder);
        return result;
    }

    /**
     * 结束行程
     *
     * @param orderNum
     * @return
     * @throws Exception
     */
    @PutMapping("/completeTripOrder/{orderNum}")
    public Object completeTrip(@PathVariable("orderNum") String orderNum) throws Exception {
        User user = (User)getUser();
        return this.tripOrderService.completeTrip(orderNum,user);
    }

    /**
     * 查询用户未完成订单
     */
    @GetMapping("/unfinishedOrder")
    public ApiResult queryUnfinishedOrder() {
        User user = (User) getUser();
        return tripOrderService.queryUnfinishedOrder(user.getId());
    }

    /**
     * 更新订单终点停车场
     *
     * @param park 更新信息
     */
    @PutMapping("/updateDestinationPark")
    public ApiResult updateDestinationPark(@RequestBody DestinationPark park) {
        return tripOrderService.updateDestinationPark(park);
    }

    /**
     * 打开车门
     */
    @PostMapping("/openCar")
    public ApiResult openCar(@RequestBody OperateDevice operateDevice) {
        operateDevice.setCode(CONSTANT.OPEN_CAR);
        User user = (User) getUser();
        operateDevice.setUserId(user.getId());
        return tripOrderService.openOrCloseCar(operateDevice);
    }

    /**
     * 锁车门
     */
    @PostMapping("/lockCar")
    public ApiResult lockCarMoment(@RequestBody OperateDevice operateDevice) {
        operateDevice.setCode(CONSTANT.CLOSE_CAR);
        User user = (User) getUser();
        operateDevice.setUserId(user.getId());
        return tripOrderService.openOrCloseCar(operateDevice);
    }

    /**
     * 声音寻车
     */
    @PostMapping("/searchCarBySound")
    public ApiResult searchCarBySound(@RequestBody OperateDevice operateDevice) {
        User user = (User) getUser();
        operateDevice.setUserId(user.getId());
        return tripOrderService.searchCarBySound(operateDevice);
    }

    /**
     * 用户实际支付金额计算
     */
    @PostMapping("/payAmount")
    public ApiResult getPayAmount(@RequestBody PayAmount payAmount) {
        User user = (User) getUser();
        payAmount.setUserId(user.getId());
        payAmount.setHalfUser(user.isHalfUser());
        return tripOrderService.calculatePayAmount(payAmount);
    }

    /**
     * 预约车辆
     *
     * @param operation 车辆操作类
     */
    @PostMapping("/reservation")
    public ApiResult createReservate(@RequestBody ReservationInfo operation) {
        User user = (User) getUser();
        operation.setUserId(user.getId());
        return tripOrderService.createReservate(operation);
    }

    /**
     * 查询用户预约信息
     */
    @GetMapping("/getReservation")
    public ApiResult getReservation() {
        User user = (User) getUser();
        return tripOrderService.getReservation(user.getId());
    }

    /**
     * 获取赠送比例&最大赠送额度
     */
    @GetMapping("/getPresent")
    public ApiResult getPresent() {
        return tripOrderService.getPresent();
    }

}
