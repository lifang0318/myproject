package cn.com.izj.service;

import cn.com.izj.base.entity.car.CarDynamicInfo;
import cn.com.izj.base.enums.ResponseEnum;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.dao.CarDynamicDao;
import cn.com.izj.dao.DevicePasswordDao;
import cn.com.izj.entity.DevicePassword;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by GouBo on 2018/9/2 17:48.
 */
@Service
public class DevicePasswordService {

    @Autowired
    private DevicePasswordDao devicePasswordDao;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private CarDynamicDao carDynamicDao;

    /**
     * 更新或插入DevicePassword
     */
    public void upsert(DevicePassword devicePassword) {
        DevicePassword oldDriverPassword = devicePasswordDao.getDriverPassword(devicePassword.getDeviceId());
        if (oldDriverPassword == null) {
            devicePasswordDao.insert(devicePassword);
        } else {
            devicePasswordDao.updateByDeviceId(devicePassword);
        }
    }

    /**
     * 根据设备id获取DevicePassword
     */
    public Object getDevicePassword(Long carId) {
        CarDynamicInfo carInfo = carDynamicDao.findById(carId);
        if (carInfo == null || StringUtils.isBlank(carInfo.getDeviceId())) {
            return new ApiResult(ResponseEnum.ERROR.getValue(), ResponseEnum.ERROR.getDesc());
        }
        return devicePasswordDao.getDriverPassword(carInfo.getDeviceId());
    }

    /**
     * 设置密码键盘密码
     */
    public ApiResult setKeyboardPassword(String deviceId, String password) {
        TcpResponse tcpResponse = deviceService.setKeyboardPassword(deviceId, password);
        if (tcpResponse.isStatus()) {
            return new ApiResult(ResponseEnum.SUCCESS.getValue(), ResponseEnum.SUCCESS.getDesc());
        } else {
            return new ApiResult(ResponseEnum.ERROR.getValue(), ResponseEnum.ERROR.getDesc());
        }

    }

    /**
     * 重置蓝牙密码
     */
    public ApiResult resetBluePassword(Long carId, String password) {
        CarDynamicInfo carInfo = carDynamicDao.findById(carId);
        if (carInfo == null || StringUtils.isBlank(carInfo.getDeviceId())) {
            return new ApiResult(ResponseEnum.ERROR.getValue(), ResponseEnum.ERROR.getDesc());
        }
        TcpResponse tcpResponse = deviceService.resetBluePassword(carInfo.getDeviceId(), password);
        if (tcpResponse.isStatus()) {
            return new ApiResult(ResponseEnum.SUCCESS.getValue(), ResponseEnum.SUCCESS.getDesc());
        } else {
            return new ApiResult(ResponseEnum.ERROR.getValue(), ResponseEnum.ERROR.getDesc());
        }
    }
}
