package cn.com.izj.mybatis.annotation;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by GouBo on 2018/6/6 22:46.
 * 表名注解
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface Table {

    /**
     * 表名,如无的话为实体类名称下划线规则
     *
     * @return 映射的表名
     */
    String value() default StringUtils.EMPTY;

}
