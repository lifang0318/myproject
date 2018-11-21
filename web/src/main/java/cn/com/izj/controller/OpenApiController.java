package cn.com.izj.controller;

import cn.com.izj.base.entity.park.Park;
import cn.com.izj.dao.CarParkingFeeReportDao;
import cn.com.izj.dto.OpenResponse;
import cn.com.izj.entity.CarParkingFeeReport;
import cn.com.izj.service.DeviceService;
import cn.com.izj.service.ParkReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author GouBo
 * @date 2018/8/26
 */
@RestController
@RequestMapping("/partner")
public class OpenApiController {

    @Autowired
    CarParkingFeeReportDao carParkingFeeReportDao;

    @Autowired
    ParkReportService parkReportService;

    @PostMapping("/carParkingFeeReport")
    public Object carParkingFeeReport(@RequestBody CarParkingFeeReport carParkingFeeReport) {
        try {
            carParkingFeeReportDao.insert(carParkingFeeReport);
        } catch (Exception e) {
            e.printStackTrace();
            return new OpenResponse(OpenResponse.ERROR);
        }
        return new OpenResponse(OpenResponse.SUCCESS);
    }

    /**
     * 停车场上报
     *
     * @return
     */
    @PostMapping("/parkReport")
    public Object parkReport(@RequestBody List<Park> parks) {
        try {
            if (CollectionUtils.isEmpty(parks)) {
                return new OpenResponse(OpenResponse.ERROR);
            }
            this.parkReportService.parkReport(parks);
        } catch (Exception e) {
            e.printStackTrace();
            return new OpenResponse(OpenResponse.ERROR);
        }
        return new OpenResponse(OpenResponse.SUCCESS);
    }

    @PostMapping("/carInReport")
    public Object carInReport() {
        return new OpenResponse(OpenResponse.SUCCESS);
    }

    /**
     * 服务器检测
     */
    @GetMapping("/test")
    public Object test() {
        return null;
    }


    /*************************以下为测试代码，后期删除**************************/

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/findCar")
    public Object findCar(String deviceId, int code) {
        return deviceService.findCar(deviceId, code);
    }

    @GetMapping("/OpenOrCloseDoor")
    public Object OpenOrCloseDoor(String deviceId, int code) {
        return deviceService.OpenOrCloseDoor(deviceId, code);
    }

    @GetMapping("/returnCar")
    public Object returnCar(String deviceId) {
        return deviceService.returnCar(deviceId);
    }

}
