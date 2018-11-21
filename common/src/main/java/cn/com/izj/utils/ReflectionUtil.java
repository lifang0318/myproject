package cn.com.izj.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by GouBo on 2018/6/6 22:46.
 */
public class ReflectionUtil {

    /**
     * 查找类中,拥有某个注解的字段,如果不存在返回空
     *
     * @param clazz     类的Class
     * @param annoClazz 注解Class
     * @return 字段Field
     */
    public static Field findFieldWithAnnotation(Class<?> clazz, Class<?> annoClazz) {
        List<Field> fieldList = getAllFields(clazz);

        for (Field field : fieldList) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annoClazz.isAssignableFrom(annotation.getClass())) {
                    return field;
                }
            }
        }
        return null;
    }

    /**
     * 获取子类和父类的所有Field
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != Object.class) {//当父类为Object的时候说明到达了最上层的父类(Object类).
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass(); //得到父类,然后赋给自己
        }
        return fieldList;
    }

}
