package cn.com.izj.utils;

import cn.com.izj.base.entity.car.OriginContrail;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 朱鸿平
 * @date: 2018/9/9 23:33
 */
public class ContrailUtil {

    /**
     * 轨迹点获取规则 去除速度为0的点后的原始点
     * 原始点 <= 3000 直接返回
     * 3000 < 原始点 <= 6000 隔 1 点取值
     * 6000 < 原始点 <= 9000 隔 2 点取值
     * 原始点 > 9000 只取前9000个点按9000逻辑处理
     *
     * @param originContrailList 原始轨迹点
     */
    public static List<OriginContrail> getContrailList(List<OriginContrail> originContrailList) {
        if (!CollectionUtils.isEmpty(originContrailList)) {
            Integer count = originContrailList.size();
            List<OriginContrail> list = new ArrayList<>();
            if (count <= 3000) {
                return originContrailList;
            } else if (count <= 6000) {
                for (int i = 0; i < count; i++) {
                    if (i % 2 == 0) {
                        list.add(originContrailList.get(i));
                    }
                }
                return list;
            } else {
                originContrailList = originContrailList.stream().limit(9000).collect(Collectors.toList());
                for (int i = 0; i < count; i++) {
                    if (i % 3 == 0) {
                        list.add(originContrailList.get(i));
                    }
                }
                return list;
            }
        }
        return new ArrayList<>();
    }
}
