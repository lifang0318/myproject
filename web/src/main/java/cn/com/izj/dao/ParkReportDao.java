package cn.com.izj.dao;

import cn.com.izj.base.entity.park.Park;
import cn.com.izj.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: lifang
 * @description:停车场上报-dao
 * @date: Created in 2018/8/29 23:28
 * @version:
 */
public interface ParkReportDao extends BaseMapper<Park, Long> {

    /**
     * 批量添加停车场
     *
     * @throws Exception
     */
    void batchInsert(@Param("parks") List<Park> parks) throws Exception;

    /**
     * 删除指定管理员的停车场
     *
     * @param manager
     * @throws Exception
     */
    void delParkByManage(@Param("manager") String manager) throws Exception;


}
