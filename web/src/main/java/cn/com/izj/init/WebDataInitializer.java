package cn.com.izj.init;

import cn.com.izj.base.entity.LocationPoint;
import cn.com.izj.dao.ParkDao;
import cn.com.izj.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by GouBo on 2018/9/7 21:51.
 * 业务信息初始化
 */
@Component
public class WebDataInitializer extends AbstractDataInitializer {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ParkDao parkDao;

    @Override
    protected void doInit() {
        initParkRedis();//停车场缓存信息


    }

    /**
     * 初始化停车场缓存信息
     */
    private void initParkRedis() {
        List<LocationPoint> list = parkDao.getParkList();
        if (!CollectionUtils.isEmpty(list)) {
            for (LocationPoint point : list) {
                redisUtil.saveOrUpdateParkGeo(point);
            }
        }
    }

    @Override
    protected boolean isNeedInit() {
        return redisUtil.getParkRedis();
    }

    @Override
    public Integer getIndex() {
        return Integer.MIN_VALUE + 1;
    }
}
