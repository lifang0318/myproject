package cn.com.izj.repository.support;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GouBo on 2018/6/9 1:34.
 */
public class QueryResultConverter {

    private static Logger logger = LoggerFactory.getLogger(QueryResultConverter.class);

    public static <T, I> Page<I> convert(Page<T> pageData, Class<I> clazz, Pageable pageable) {
        List<T> contents = pageData.getContent();
        List<I> infos = convert(contents, clazz);
        return new PageImpl<>(infos, pageable, pageData.getTotalElements());
    }

    public static <I, T> List<I> convert(List<T> contents, Class<I> clazz) {
        List<I> infos = new ArrayList<>();
        for (T domain : contents) {
            I info = null;
            try {
                info = clazz.newInstance();
                BeanUtils.copyProperties(info, domain);
            } catch (Exception e) {
                logger.info("转换数据失败", e);
                throw new RuntimeException("转换数据失败");
            }
            if (info != null) {
                infos.add(info);
            }
        }
        return infos;
    }
}
