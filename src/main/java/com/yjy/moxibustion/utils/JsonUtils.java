package com.yjy.moxibustion.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author 杨景元
 * @date 2021/1/19 15:33
 */
public class JsonUtils {
    /**
     * Json 字符串转换为 JavaBean
     * @param jsonString Json 字符串
     * @param clazz Json 字符串对应的 JavaBean 的字节码对象
     * @return 指定字节码对象的实例
     */
    public static <T> T json2pojo(String jsonString, Class<T> clazz) {
        return JSONObject.parseObject(jsonString, clazz);
    }

    /**
     * Json 字符串转换为 List
     * @param jsonString Json 字符串
     * @param clazz List 集合存放类型的字节码对象
     * @return 存放指定字节码对象实例的 List
     */
    public static <T> List<T> json2list(String jsonString, Class<T> clazz) {
        return JSONObject.parseArray(jsonString, clazz);
    }

    /**
     * Json 字符串转换为 Map
     * @param jsonString Json 字符串
     * @return Map 集合
     */
    public static Map json2map(String jsonString) {
        return JSONObject.parseObject(jsonString, Map.class);
    }

    /**
     * JavaBean 转换为 Json 字符串
     * @param obj JavaBean 对象
     * @return JavaBean 对应的 Json 字符串
     */
    public static String pojo2json(Object obj) {
        return JSONObject.toJSONString(obj);
    }

    public static JSONObject castToJson(String jsoup){
        int startIndex = jsoup.indexOf("(");
        int endIndex = jsoup.indexOf(")");
        String json = jsoup.substring(startIndex + 1, endIndex);
        JSONObject object = JSONObject.parseObject(json);
        return object;
    }
}
