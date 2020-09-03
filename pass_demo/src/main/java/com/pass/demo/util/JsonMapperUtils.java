package com.pass.demo.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class JsonMapperUtils {

    private final static ObjectMapper MAPPER = new ObjectMapper();
    private final static Logger LOGGER = LoggerFactory.getLogger(JsonMapperUtils.class);

    static {
        // 将对象的所有字段全部列入
        MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //反序列化的时候如果多了其他属性,不抛出异常
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //如果是空对象的时候,不抛异常
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //取消时间的转化格式,默认是时间戳,可以取消,同时需要设置要表现的时间格式
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    // 将单个对象转换成json格式的字符串（没有格式化后的json）
    public static <T> String obj2String(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : MAPPER.writeValueAsString(obj);
        } catch (IOException e) {
            LOGGER.warn("Parse object to String error", e);
            return null;
        }
    }

    // 将单个对象转换成json格式的字符串（格式化后的json）
    public static <T> String obj2StringPretty(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (IOException e) {
            LOGGER.warn("Parse object to String error", e);
            return null;
        }
    }

    // 将json形式的字符串数据转换成单个对象
    public static <T> T string2Obj(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : MAPPER.readValue(str, clazz);
        } catch (IOException e) {
            LOGGER.warn("Parse object to Object error", e);
            return null;
        }
    }

    // 将json形式的字符串数据转换成多个对象
    public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return typeReference.getType().equals(String.class) ? (T) str : (T) MAPPER.readValue(str, typeReference);
        } catch (IOException e) {
            LOGGER.warn("Parse object to Object error", e);
            return null;
        }
    }

    // 将json形式的字符串数据转换成多个对象
    public static <T> T string2Obj(String str, Class<T> collectionClass, Class<?>... elementClasses) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return MAPPER.readValue(str, javaType);
        } catch (IOException e) {
            LOGGER.warn("Parse object to Object error", e);
            return null;
        }
    }

    // 将Object转化为对象
    public static <T> T obj2obj(Object obj, TypeReference<T> typeReference) {
        if (obj == null || typeReference == null) {
            return null;
        }
        try {
            String json = obj2String(obj);
            return typeReference.getType().equals(String.class) ? (T) json : (T) MAPPER.readValue(json, typeReference);
        } catch (Exception e) {
            LOGGER.warn("Parse object to object error", e);
            return null;
        }
    }
}
