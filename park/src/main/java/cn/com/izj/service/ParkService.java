package cn.com.izj.service;

import cn.com.izj.base.CONSTANT;
import cn.com.izj.base.entity.LocationPoint;
import cn.com.izj.base.entity.park.Park;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.condition.ParkQueryCondition;
import cn.com.izj.dao.CarCommonDao;
import cn.com.izj.dao.ParkDao;
import cn.com.izj.dto.RelativePark;
import cn.com.izj.utils.RedisUtil;
import cn.com.izj.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 停车场-controller
 *
 * @author lifang
 */
@Service
public class ParkService {

    @Autowired
    private ParkDao parkDao;

    @Autowired
    private CarCommonDao carCommonDao;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    /**
     * 新增停车场记录
     *
     * @param park
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int add(Park park) {
        park.setCreateTime(new Date());
        park.setUpdateTime(new Date());
        park.setStatus(Park.STATUS_ENABLE);
        park.setParkCountBalance(park.getParkCount());//初始化时，剩余停车位总数=总停车位数
        int result = parkDao.insert(park);
        if (1 == result) {
            updateParkRedis(park);
        }
        return result;
    }

    /**
     * 更新停车场
     *
     * @param park
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateById(Park park) {
        park.setUpdateTime(new Date());
        Park oldPark = this.parkDao.findById(park.getId());
        if (null != park.getParkCount() && oldPark.getParkCountBalance() > park.getParkCount()) {
            park.setParkCountBalance(park.getParkCount());
        }
        Integer status = park.getStatus();
        if (status.equals(Park.STATUS_ENABLE)) {
            updateParkRedis(oldPark);
        } else {
            updateParkRedis(park);
        }

        return this.parkDao.updateById(park);
    }

    public void updateParkRedis(Park park) {
        LocationPoint point = new LocationPoint();
        point.setLongitude(StringUtil.getDouble(park.getLongitude()));
        point.setLatitude(StringUtil.getDouble(park.getLatitude()));
        point.setParkId(park.getId());
        redisUtil.saveOrUpdateParkGeo(point);
    }

    /**
     * 根据id获取停车场信息
     *
     * @param id
     * @return
     */
    public Park getParkById(Long id) {
        return this.parkDao.findById(id);
    }

    /**
     * 根据条件查询park集合
     *
     * @param condition 查询条件
     * @return
     */
    public List<Park> findParksByConDiton(ParkQueryCondition condition) {
        Park park = new Park();
        BeanUtils.copyProperties(condition, park);
        return this.parkDao.findAll(park, new RowBounds());
    }

    /**
     * 预约车辆时获取停车场相关信息(停车场地址、经纬度、可用车辆数、剩余停车位数量)
     *
     * @return
     */
    public ApiResult findRelativeParks() throws Exception {
        ApiResult result = new ApiResult();
        List<RelativePark> relativeParks = this.parkDao.findRelativeParks();
        result.setData(CollectionUtils.isEmpty(relativeParks) ? new ArrayList<>() : relativeParks);
        return result;
    }

    /**
     * 根据经纬度返回最近的5个停车场信息
     *
     * @param locationPoint
     * @return
     */
    public ApiResult getNearestParkList(LocationPoint locationPoint) {
        locationPoint.setDistance(CONSTANT.NEAREST_PARK_DISTANCE);
        List<LocationPoint> list = redisUtil.getParkByPoint(locationPoint);
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }
        list = list.stream().limit(5).collect(Collectors.toList());
        List<LocationPoint> result = new ArrayList<>();
        for (LocationPoint point : list) {
            Park park = parkDao.findById(point.getParkId());
            LocationPoint location = new LocationPoint();
            location.setLongitude(StringUtil.getDouble(park.getLongitude()));
            location.setLatitude(StringUtil.getDouble(park.getLatitude()));
            location.setParkId(point.getParkId());
            location.setParkName(park.getName());
            location.setParkAddress(park.getAddress());
            result.add(location);
        }
        return ApiResult.successWithData(result);
    }

    /**
     * 车辆列表分页查询
     *
     * @param condition
     * @return
     */
    public PageInfo findOrdersPageByCondition(ParkQueryCondition condition) {
        PageHelper.startPage(condition.getPageNumber(), condition.getPageSize());
        List<Park> list = this.findParksByConDiton(condition);
        PageInfo<Park> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
