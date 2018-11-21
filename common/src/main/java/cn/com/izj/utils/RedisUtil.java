package cn.com.izj.utils;

import cn.com.izj.base.CONSTANT;
import cn.com.izj.base.entity.LocationPoint;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 朱鸿平
 * @date: 2018/6/10 16:32
 */
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 根据经纬度和距离 查询附近停车场list
     *
     * @param parkPoint (经纬度,距离)
     */
    public List<LocationPoint> getParkByPoint(LocationPoint parkPoint) {
        List<LocationPoint> list = new ArrayList<>();
        GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
        RedisGeoCommands.GeoRadiusCommandArgs geoRadiusCommandArgs = RedisGeoCommands.GeoRadiusCommandArgs
                .newGeoRadiusArgs();
        RedisGeoCommands.GeoRadiusCommandArgs cludeDistance = geoRadiusCommandArgs.includeDistance().sortAscending();
        GeoResults<RedisGeoCommands.GeoLocation<String>> geoResults = geoOps.geoRadius(CONSTANT.PARK_STATION,
                new Circle(new Point(parkPoint.getLongitude(), parkPoint.getLatitude()),
                        new Distance(StringUtil.getDouble(parkPoint.getDistance()), RedisGeoCommands.DistanceUnit.METERS)),
                cludeDistance);
        for (GeoResult<RedisGeoCommands.GeoLocation<String>> action : geoResults.getContent()) {
            String parkInfo = action.getContent().getName();
            LocationPoint locationPoint = new LocationPoint();
            locationPoint.setParkId(StringUtil.getLong(parkInfo));
            list.add(locationPoint);
        }
        return list;
    }

    public boolean getParkRedis() {
        GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
        RedisGeoCommands.GeoRadiusCommandArgs geoRadiusCommandArgs = RedisGeoCommands.GeoRadiusCommandArgs
                .newGeoRadiusArgs();
        RedisGeoCommands.GeoRadiusCommandArgs cludeDistance = geoRadiusCommandArgs.includeDistance().sortAscending();
        GeoResults<RedisGeoCommands.GeoLocation<String>> geoResults = geoOps.geoRadius(CONSTANT.PARK_STATION,
                new Circle(new Point(104.0657722950,30.6575123769),
                        new Distance(Double.MAX_VALUE, RedisGeoCommands.DistanceUnit.KILOMETERS)),
                cludeDistance);
        return CollectionUtils.isEmpty(geoResults.getContent());
    }


    /**
     * 添加/更新停车场缓存位置
     *
     * @param point (停车场id，名称，经度，纬度)
     */
    public void saveOrUpdateParkGeo(LocationPoint point) {
        GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
        Double lat = StringUtil.getDouble(point.getLatitude());
        Double lng = StringUtil.getDouble(point.getLongitude());
        String parkInfo = StringUtil.getStr(point.getParkId());
        geoOps.geoAdd(CONSTANT.PARK_STATION, new Point(lng, lat), parkInfo);
    }


    /**
     * 添加行程期间对应的车辆位置信息
     *
     * @param key
     * @param point
     * @param member
     */
    public void saveTripOrderGeo(String key, Point point, String member) {
        Assert.hasLength(key, "[Assertion failed] - key must has length");
        Assert.hasLength(key, "[Assertion failed] - member must has length");
        redisTemplate.opsForGeo().geoAdd(key, point, member);
    }

    /**
     * 计算位置距离
     *
     * @param key
     * @param member1
     * @param member2
     * @param metric
     * @return
     */
    public Distance getPointDistance(String key, String member1, String member2, Metric metric) {
        Assert.hasLength(key, "[Assertion failed] - key must has length");
        Metric metricNew = metric == null ? RedisGeoCommands.DistanceUnit.METERS : metric;
        return redisTemplate.opsForGeo().geoDist(key, member1, member2, metricNew);
    }

    /**
     * 判断车辆是否在对应停车场内
     *
     * @param point  车辆所在经纬度
     * @param parkId 指定停车场id
     */
    public Boolean isInPark(LocationPoint point, Long parkId) {
        boolean flag = false;
        List<LocationPoint> list = getParkByPoint(point);
        if (!CollectionUtils.isEmpty(list)) {
            List<Long> parkList = list.stream().map(LocationPoint::getParkId).collect(Collectors.toList());
            if (parkList.contains(parkId)) {
                flag = true;
            }
        }
        return flag;
    }
}
