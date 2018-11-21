package cn.com.izj.service;

import cn.com.izj.base.entity.park.Park;
import cn.com.izj.dao.ParkDao;
import cn.com.izj.dao.ParkReportDao;
import cn.com.izj.entity.User;
import cn.com.izj.repository.UserRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: lifang
 * @description:停车场上报-service
 * @date: Created in 2018/8/29 23:28
 * @version:
 */
@Service
public class ParkReportService {

    @Autowired
    private ParkDao parkDao;

    @Autowired
    private ParkReportDao parkReportDao;

    @Autowired
    private UserRepository userRepository;

    /**
     * 停车场上报
     *
     * @param parks
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void parkReport(List<Park> parks) throws Exception {
        if (CollectionUtils.isNotEmpty(parks)) {
            //获取已有的停车场
            List<Park> existParks = this.parkDao.findParksByManager(parks.get(0).getManager());
            User user = this.userRepository.findUserByUsername("admin");
            List<Park> parksToSave = new ArrayList<>();
            for (Park park : parks) {
                park.setUserId(user.getId());
            }
            parksToSave.addAll(parks);
            //找到需要删除的
            if(CollectionUtils.isNotEmpty(existParks)){
                for (Park existPark : existParks) {
                    List<Park> needDelPark = parks.stream().filter(e->e.getId().equals(existPark.getId())).collect(Collectors.toList());
                    if(CollectionUtils.isEmpty(needDelPark)){
                        //没有匹配到，需要置为删除状态
                        existPark.setStatus(Park.STATUS_DELETE);
                        parksToSave.add(existPark);
                    }
                }
            }
            //先清除此负责人下已有的停车场
            this.parkReportDao.delParkByManage(parks.get(0).getManager());
            //执行批量保存
            this.parkReportDao.batchInsert(parksToSave);
        }
    }
}
