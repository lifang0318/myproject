package cn.com.izj.controller.system;

import cn.com.izj.base.controller.BaseController;
import cn.com.izj.base.entity.car.CarCommonInfo;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.dto.AddCarInfo;
import cn.com.izj.dto.AuditCar;
import cn.com.izj.dto.QueryCarCondition;
import cn.com.izj.dto.UpdateGrade;
import cn.com.izj.entity.User;
import cn.com.izj.service.CarInfoService;
import cn.com.izj.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 后台车辆 API
 *
 * @author: 朱鸿平
 * @date: 2018/6/10 15:47
 */
@RestController
@RequestMapping("/system/car")
public class CarSystemController extends BaseController {

    @Autowired
    private CarInfoService carInfoService;

    /**
     * 添加车辆
     *
     * @param info 车辆基本信息
     */
    @PostMapping("/addCar")
    public ApiResult insertCar(@RequestBody AddCarInfo info) {
        return carInfoService.insertCar(info);
    }

    /**
     * 删除车辆
     *
     * @param params 车辆id
     */
    @DeleteMapping("/delete")
    public ApiResult deleteCar(@RequestBody Map<String, Object> params) {
        Long carId = StringUtil.getLong(params.get("carId"));
        return carInfoService.UpdateCarStateById(carId, CarCommonInfo.STATE_DELETE);
    }

    /**
     * 车辆下线
     *
     * @param params 车辆id
     */
    @PutMapping("/downline")
    public ApiResult downlineCar(@RequestBody Map<String, Object> params) {
        Long carId = StringUtil.getLong(params.get("carId"));
        return carInfoService.UpdateCarStateById(carId, CarCommonInfo.STATE_DOWNLINE);
    }

    /**
     * 车辆上线
     *
     * @param params 车辆id
     */
    @PutMapping("/online")
    public ApiResult onlineCar(@RequestBody Map<String, Object> params) {
        Long carId = StringUtil.getLong(params.get("carId"));
        return carInfoService.updateCarOnline(carId);
    }


    /**
     * 修改车辆等级
     *
     * @param gradeInfo 修改字段
     */
    @PutMapping("/updateGrade")
    public ApiResult updateGradeById(@RequestBody UpdateGrade gradeInfo) {
        return carInfoService.updateGradeById(gradeInfo);
    }

    /**
     * 审核车辆
     *
     * @param carInfo
     * @return
     */
    @Deprecated
    @PutMapping("/auditCar")
    public ApiResult auditCar(@RequestBody AuditCar carInfo) {
        User user = (User) getUser();
        carInfo.setAuditorId(user.getId());
        Integer result = carInfoService.auditCar(carInfo);
        return ApiResult.validateSingleData(result);
    }

    /**
     * 所有车辆列表
     */
    @PostMapping("/carList")
    public ApiResult carList(@RequestBody QueryCarCondition condition) {
        return carInfoService.getCarList(condition);
    }

}

