package cn.com.izj.dao;

import cn.com.izj.base.entity.LocationPoint;
import cn.com.izj.base.entity.park.Park;
import cn.com.izj.dto.RelativePark;
import cn.com.izj.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: lifang
 * @description:停车场-dao
 * @date: Created in 2018/6/6 0:35
 * @version:
 */
public interface ParkDao extends BaseMapper<Park, Long> {

    /**
     * 预约车辆时获取停车场相关信息(停车场地址、车位总数、可用车辆数、剩余停车位数量)
     *
     * @return
     */
    List<RelativePark> findRelativeParks() throws Exception;

    /**
     * 根据经纬度查询停车场id
     *
     * @param longitude
     * @param latitude
     */
    Long getParkId(@Param("lng") BigDecimal longitude, @Param("lat") BigDecimal latitude);

    /**
     * 获取所有停车场信息
     *
     * @return
     */
    List<LocationPoint> getParkList();

    /**
     * 根据管理员获取停车场列表
     *
     * @param manager
     */
    List<Park> findParksByManager(String manager);
}



