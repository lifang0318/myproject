package cn.com.izj.controller.mobile;

import cn.com.izj.base.controller.BaseController;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.dto.Device;
import cn.com.izj.entity.User;
import cn.com.izj.service.CarInfoService;
import cn.com.izj.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author: 朱鸿平
 * @date: 2018/6/6 20:12
 */
@RestController
@RequestMapping("/mobile/car")
public class CarMobileController extends BaseController {

    @Autowired
    private CarInfoService carInfoService;

    /**
     * 车辆详情
     *
     * @param carId 车辆id
     */
    @GetMapping("/info/{carId}")
    public ApiResult getCarInfo(@PathVariable Long carId) {
        return carInfoService.getCarInfoById(carId);
    }


    /**
     * 可预约车辆
     */
    @GetMapping("/getReservationList/{parkId}")
    public ApiResult getReservationList(@PathVariable Long parkId) {
        return carInfoService.getReservationList(parkId);
    }

    /**
     * 我的车辆
     */
    @GetMapping("/myCarList")
    @Deprecated
    public ApiResult getCarList() {
        User user = (User) getUser();
        return carInfoService.getMyCarList(user.getId());
    }

    /**
     * 取消预约
     */
    @PutMapping("/cancelReservation")
    public ApiResult cancelReservation(@RequestBody Map<String, Object> params) {
        Long reservationId = StringUtil.getLong(params.get("reservationId"));
        User user = (User) getUser();
        return carInfoService.cancelReservation(user.getId(), reservationId);
    }

    /**
     * 获取设备信息
     */
    @GetMapping("/getDeviceInfo/{carId}")
    public ApiResult getDeviceInfo(@PathVariable Long carId) {
        Device device = carInfoService.getDeviceInfo(carId);
        return ApiResult.successWithData(device);
    }

}
