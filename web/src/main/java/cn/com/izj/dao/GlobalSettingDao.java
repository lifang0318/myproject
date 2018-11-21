package cn.com.izj.dao;

import cn.com.izj.base.entity.GlobalSetting;
import cn.com.izj.dto.SettingInfo;
import cn.com.izj.mybatis.mapper.BaseMapper;

import java.util.List;

/**
 * @author: 朱鸿平
 * @date: 2018/7/14 10:32
 */
public interface GlobalSettingDao extends BaseMapper<GlobalSetting, Long> {

    /**
     * 获取可用配置列表
     */
    List<SettingInfo> getSettingList();

}
