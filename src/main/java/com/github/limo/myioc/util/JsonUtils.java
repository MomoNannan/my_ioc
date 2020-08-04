package com.github.limo.myioc.util;

import com.github.limo.myioc.model.DefaultBeanDefinition;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.experimental.UtilityClass;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/4
 * @time 11:54
 */
@UtilityClass
public class JsonUtils {

    /**
     * 将 json array str 解析成 对象 List
     * 参考: Gson基本操作，JsonObject，JsonArray，String，JavaBean，List互转 https://www.cnblogs.com/robbinluobo/p/7217387.html
     * @param jsonArrayStr
     * @param targetType
     * @param <T>
     * @return
     */
    public static <T> List<T> deserializeArray(String jsonArrayStr, Class<T> targetType) {
        // 注: new TypeToken<List<T>>() {}.getType() 这种方式只适用于固定的类型, 而在应用于泛型时, 泛型会被擦除, 无法正确解析成目标类型.
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(jsonArrayStr).getAsJsonArray();
        List<T> result = new ArrayList<>();
        Gson gson = new Gson();
        for (JsonElement jsonElement : jsonArray) {
            T t = gson.fromJson(jsonElement, targetType);
            result.add(t);
        }
        return result;
    }

    public static void main(String[] args) {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("beans.json");
        String fileContent = FileUtils.getFileContent(in);
        List<DefaultBeanDefinition> users = deserializeArray(fileContent, DefaultBeanDefinition.class);
        System.out.println(users);
    }
}
