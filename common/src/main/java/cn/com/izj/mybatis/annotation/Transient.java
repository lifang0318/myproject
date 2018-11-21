package cn.com.izj.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by GouBo on 2018/6/6 22:46.
 * 不进行序列化的字段
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface Transient {

}
