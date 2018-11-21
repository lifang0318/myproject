package cn.com.izj.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * 自定义json工具类
 *
 * @author: 朱鸿平
 * @date: 2018/6/4 21:24
 */
public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        // 去除默认的时间格式
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 设置时区为中国上海
        OBJECT_MAPPER.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        // 序列化时 NULL 的处理
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 反序列化时，属性不存在的兼容处理
        OBJECT_MAPPER.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 序列化时，统一的时间格式处理
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        OBJECT_MAPPER.setSerializerFactory(OBJECT_MAPPER.getSerializerFactory().withSerializerModifier(new MyBeanSerializerModifier()));

    }

    /**
     * object -> 实体bean
     *
     * @param object 待转实体
     * @param clazz  目标类
     * @param <T>    泛型类
     * @return 泛型类
     */
    public static <T> T ObjectToBean(Object object, Class<T> clazz) {
        return jsonToBean(JsonUtil.beanToJson(object), clazz);
    }

    /**
     * json-> object
     *
     * @param json  待转json
     * @param clazz 目标类
     * @return 泛型类
     */
    public static <T> T jsonToBean(String json, Class<T> clazz) {
        try {
            if (StringUtils.isNotEmpty(json))
                return OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * json -> Map
     *
     * @param json 待转json
     * @return map
     */
    public static HashMap jsonToMap(Object json) {
        return ObjectToBean(json, HashMap.class);
    }

    /**
     * Bean -> json
     *
     * @param src 实体bean
     * @return json
     */
    public static <T> String beanToJson(T src) {
        try {
            if (src instanceof String) {
                return (String) src;
            } else {
                return OBJECT_MAPPER.writeValueAsString(src);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Json to Collection
     *
     * @param json          待转json
     * @param typeReference colletion
     * @return
     */
    public static <T> T jsonToCollection(String json, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
