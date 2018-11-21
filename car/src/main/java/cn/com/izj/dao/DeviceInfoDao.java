package cn.com.izj.dao;

import cn.com.izj.base.entity.DeviceInfo;
import cn.com.izj.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author: 朱鸿平
 * @date: 2018/7/15 15:53
 */
public interface DeviceInfoDao extends BaseMapper<DeviceInfo, Integer> {

    DeviceInfo getDeviceInfo(String deviceId);

    /**
     * 更新设备状态
     *
     * @param deviceId
     * @param state
     */
    void updateDeviceState(@Param("deviceId") String deviceId, @Param("state") Integer state);
}
