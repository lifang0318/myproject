package cn.com.izj.controller.mobile;

import cn.com.izj.base.controller.BaseController;
import cn.com.izj.base.entity.LocationPoint;
import cn.com.izj.base.entity.park.Park;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.condition.ParkQueryCondition;
import cn.com.izj.service.ParkService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 停车场-controller
 *
 * @author lifang
 */
@RestController
@RequestMapping("/mobile/park")
public class ParkMobileController extends BaseController {

    @Autowired
    private ParkService parkService;

    @GetMapping("/findParks")
    public Object findParks(ParkQueryCondition condition) throws Exception {
        ApiResult result = new ApiResult();
        if(condition.getPageSize() != -1){
            PageInfo pageInfo = this.parkService.findOrdersPageByCondition(condition);
            result.setData(pageInfo);
        }else{
            List<Park> parks = this.parkService.findParksByConDiton(condition);
            result.setData(parks);
        }
        return result;
    }

    /**
     * 预约车辆时获取停车场相关信息(停车场地址、经纬度、可用车辆数、剩余停车位数量)
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/findRelativeParks")
    public ApiResult findRelativeParks() throws Exception {
        return this.parkService.findRelativeParks();
    }

    /**
     * 根据经纬度返回最近的5个停车场信息
     *
     * @param locationPoint 经纬度
     */
    @PostMapping("/getNearestPark")
    public ApiResult getNearestParkList(@RequestBody LocationPoint locationPoint) {
        return parkService.getNearestParkList(locationPoint);
    }
}
