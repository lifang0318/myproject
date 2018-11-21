package cn.com.izj.dao;

import cn.com.izj.base.entity.car.CarGradeRule;
import cn.com.izj.mybatis.mapper.BaseMapper;

/**
 * @author: lifang
 * @description: 车辆等级规则-dao
 * @date: Created in 2018/6/13 22:01
 * @version:
 */
public interface CarGradeRuleDao extends BaseMapper<CarGradeRule, Long> {

    /**
     * 根据车辆等级查询计价规则等信息
     *
     * @param grade 车辆等级
     * @return
     * @throws Exception
     */
    CarGradeRule getCarGradeRuleByGrade(Integer grade) throws Exception;

}
