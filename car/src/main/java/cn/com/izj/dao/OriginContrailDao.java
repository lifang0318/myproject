package cn.com.izj.dao;

import cn.com.izj.base.entity.LocationPoint;
import cn.com.izj.base.entity.car.OriginContrail;
import cn.com.izj.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 车辆原始轨迹-dao
 *
 * @author lifang
 */
public interface OriginContrailDao extends BaseMapper<OriginContrail, Long> {

    /**
     * 根据时间范围查询对应轨迹数据，按时间升序排列
     *
     * @param carId     车辆id
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @return
     * @throws Exception
     */
    List<OriginContrail> findByTimeRange(@Param("carId") Long carId, @Param("startTime") Date startTime, @Param("endTime") Date endTime) throws Exception;

    /**
     * 获取车辆最近一次上报的位置信息
     */
    LocationPoint getLocationByCarId(Long carId);
}
