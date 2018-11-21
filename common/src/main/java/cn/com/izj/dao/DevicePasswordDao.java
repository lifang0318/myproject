package cn.com.izj.dao;

import cn.com.izj.entity.DevicePassword;
import cn.com.izj.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by GouBo on 2018/9/2 17:46.
 */
public interface DevicePasswordDao extends BaseMapper<DevicePassword, Long> {
    DevicePassword getDriverPassword(@Param("deviceId") String deviceId);

    void updateByDeviceId(DevicePassword devicePassword);
}
