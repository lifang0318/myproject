package cn.com.izj.base.annotation.rule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 长度
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface Length {
    /**
     * <font color="red">校验字段</font>
     *
     * @return String
     * @Title filed
     * @since 1.0
     */
    public String filed();

    /**
     * <font color="red">最小长度</font>
     *
     * @return int
     * @Title min
     * @since 1.0
     */
    public int min();

    /**
     * <font color="red">最大长度</font>
     *
     * @return int
     * @Title max
     * @since 1.0
     */
    public int max();

}