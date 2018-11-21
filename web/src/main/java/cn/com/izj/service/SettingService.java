package cn.com.izj.service;

import cn.com.izj.base.entity.GlobalSetting;
import cn.com.izj.base.enums.SettingTypeEnum;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.base.service.BaseService;
import cn.com.izj.dao.GlobalSettingDao;
import cn.com.izj.dao.TripOrderDao;
import cn.com.izj.dto.SettingInfo;
import cn.com.izj.dto.UpdateSetting;
import cn.com.izj.utils.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author: 朱鸿平
 * @date: 2018/7/14 10:32
 */
@Service
public class SettingService extends BaseService {

    @Autowired
    private GlobalSettingDao settingDao;
    @Autowired
    private TripOrderDao tripOrderDao;

    /**
     * 获取可用配置列表
     */
    public ApiResult getSettingList() {
        List<SettingInfo> settingList = settingDao.getSettingList();
        if (!CollectionUtils.isEmpty(settingList)) {
            settingList = new ArrayList<>();
        }
        return ApiResult.successWithData(settingList);
    }

    /**
     * 更新配置
     */
    @Transactional
    public ApiResult updateSetting(UpdateSetting updateSetting) {
        GlobalSetting setting = new GlobalSetting();
        BeanUtils.copyProperties(updateSetting, setting);
        setting.setUpdateTime(new Date());
        int result = settingDao.updateById(setting);
        return ApiResult.validateSingleData(result);
    }

    /**
     * 禁用配置
     */
    @Transactional
    public ApiResult closeSetting(Long id, Long userId) {
        GlobalSetting setting = new GlobalSetting();
        setting.setId(id);
        setting.setFieldState(GlobalSetting.CLOSE_STATE);
        setting.setModifyUser(userId);
        setting.setUpdateTime(new Date());
        int result = settingDao.updateById(setting);
        return ApiResult.validateSingleData(result);
    }

    /**
     * 启用配置
     */
    @Transactional
    public ApiResult openSetting(Long id, Long userId) {
        GlobalSetting setting = new GlobalSetting();
        setting.setId(id);
        setting.setFieldState(GlobalSetting.OPEN_STATE);
        setting.setUpdateTime(new Date());
        setting.setModifyUser(userId);
        int result = settingDao.updateById(setting);
        return ApiResult.validateSingleData(result);
    }

    /**
     * 新增配置
     */
    @Transactional
    public ApiResult insertSetting(GlobalSetting setting) {
        setting.setCreateTime(new Date());
        setting.setFieldState(GlobalSetting.OPEN_STATE);
        int result = settingDao.insert(setting);
        return ApiResult.validateSingleData(result);
    }

    /**
     * 是否强制升级
     */
    public ApiResult getForbidVersion() {
        GlobalSetting setting = tripOrderDao.getSettingInfo(SettingTypeEnum.UPDATE_VERSION.getValue());
        if (setting == null) {
            setting = new GlobalSetting();
        }
        return ApiResult.successWithData(StringUtil.getInt(setting.getFieldValue()));
    }
}
