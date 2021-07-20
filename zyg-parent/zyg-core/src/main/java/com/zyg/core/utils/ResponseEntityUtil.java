package com.zyg.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Type;
import java.util.List;

/**
 * ResponseEntity工具类
 */
public class ResponseEntityUtil {
    private static final String DATA = "data";
    private static final String MSG = "msg";
    private static final String CODE = "code";
    private static final String IS_SUCCESS = "success";

    /**
     * ResponseEntity转javaBean
     *
     * @param entity
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toJavaBean(ResponseEntity<Object> entity, Class<T> clazz) {
        String dateStr = getJSONData(entity).toJSONString();
        return JSONObject.toJavaObject(JSON.parseObject(dateStr), clazz);
    }

    public static <T> T toJavaBean(ResponseEntity<Object> entity) {
        String dateStr = getJSONData(entity).toJSONString();
        Gson gson = new Gson();
        Type type = new TypeToken<T>() {
        }.getType();
        return gson.fromJson(dateStr, type);
    }

    /**
     * ResponseEntity转javaObjectList
     * @param entity
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T>   toJavaList(ResponseEntity<Object> entity,Class<T> clazz) {
        String jsonStr = JSONObject.toJSONString(entity.getBody());
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        JSONArray jsonArray = jsonObject.getJSONArray(DATA);
        String dateStr = jsonArray.toJSONString();
        return JSONArray.parseArray(dateStr,clazz);
    }

    /**
     * ResponseEntity转String、Integer等类型
     *
     * @param entity
     * @param <T>
     * @return
     */
    public static <T> T toJava(ResponseEntity<Object> entity) {
        return (T) getJSONData(entity);
    }

    /**
     * 获取JSON格式参数
     *
     * @param entity
     * @return
     */
    public static JSONObject getJSONData(ResponseEntity<Object> entity) {
        String jsonStr = JSONObject.toJSONString(entity.getBody());
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        return jsonObject.getJSONObject(DATA);
    }

    /**
     * 获取结果码
     *
     * @param entity
     * @return
     */
    public static Integer getResultCode(ResponseEntity<Object> entity) {
        String jsonStr = JSONObject.toJSONString(entity.getBody());
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        return jsonObject.getInteger(CODE);
    }

    /**
     * 获取消息
     *
     * @param entity
     * @return
     */
    public static String getResultMsg(ResponseEntity<Object> entity) {
        String jsonStr = JSONObject.toJSONString(entity.getBody());
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        return jsonObject.getString(MSG);
    }

    /**
     * 判断接口是否请求成功
     * @param entity
     * @return
     */
    public static Boolean isSuccess(ResponseEntity<Object> entity) {
        boolean result = false;
        if(entity.getStatusCodeValue() == HttpStatus.OK.value()) {
            String jsonStr = JSONObject.toJSONString(entity.getBody());
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            result = jsonObject.getBoolean(IS_SUCCESS);
        }
        return result;
    }
}