package cn.com.izj.mybatis.annotation;

import cn.com.izj.mybatis.keygen.KeyGeneratorType;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by GouBo on 2018/6/6 22:46.
 * 标注主键字段,设置主键生成规则
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface Id {

    /**
     * 设置主键生成策略
     *
     * @return 主键生成策略, 见枚举值
     */
    KeyGeneratorType value() default KeyGeneratorType.NONE;
}
