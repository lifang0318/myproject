package cn.com.izj.controller.system;

import cn.com.izj.base.controller.BaseController;
import cn.com.izj.base.entity.park.Park;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.entity.User;
import cn.com.izj.service.ParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 停车场-controller
 *
 * @author lifang
 */
@RestController
@RequestMapping("/system/park")
public class ParkSystemController extends BaseController {

    @Autowired
    private ParkService parkService;

    /**
     * 添加停车场记录
     *
     * @param park
     * @return
     */
    @PostMapping("/add")
    public Object add(@RequestBody Park park) throws Exception {
        User user = (User) getUser();
        park.setUserId(user.getId());
        int result = this.parkService.add(park);
        return ApiResult.validateSingleData(result);
    }

    /**
     * 更新停车场信息
     *
     * @param id
     * @param park
     * @return
     */
    @PutMapping("/update/{id}")
    public Object update(@PathVariable Long id, @RequestBody Park park) throws Exception {
        park.setId(id);
        int result = this.parkService.updateById(park);
        return ApiResult.validateSingleData(result);
    }

    /**
     * 删除停车场
     *
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping("/delete/{id}")
    public Object delete(@PathVariable Long id) throws Exception {
        Park park = this.parkService.getParkById(id);
        park.setStatus(Park.STATUS_DELETE);
        int result = this.parkService.updateById(park);
        return ApiResult.validateSingleData(result);
    }


    /**
     * 启用停车场
     *
     * @param id
     * @return
     * @throws Exception
     */
    @PutMapping("/enable/{id}")
    public Object enable(@PathVariable Long id) throws Exception {
        Park park = this.parkService.getParkById(id);
        park.setStatus(Park.STATUS_ENABLE);
        int result = this.parkService.updateById(park);
        return ApiResult.validateSingleData(result);
    }
    /**
     * 禁用停车场
     *
     * @param id
     * @return
     * @throws Exception
     */
    @PutMapping("/disable/{id}")
    public Object disable(@PathVariable Long id) throws Exception {
        Park park = this.parkService.getParkById(id);
        park.setStatus(Park.STATUS_DISABLED);
        int result = this.parkService.updateById(park);
        return ApiResult.validateSingleData(result);
    }

    /**
     * 根据id获取停车场信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/getParkInfo/{id}")
    public Object getParkById(@PathVariable Long id) throws Exception {
        ApiResult result = new ApiResult();
        Park park = this.parkService.getParkById(id);
        result.setData(park);
        return result;
    }

}
