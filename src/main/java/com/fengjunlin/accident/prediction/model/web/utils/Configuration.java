package com.fengjunlin.accident.prediction.model.web.utils;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 读取项目json的配置信息
 * @Author fengjl
 * @Date 2019/4/15 12:37
 * @Version 1.0
 **/
public class Configuration {

    public static JSONObject configration;
    public static List<Map<String, Object>> maps;

    static {
        configration = ReadFile.getConfigData("configuration.json");
        maps  = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("labelID",1);
        map1.put("labelName","郊区王子");
        map1.put("labelWeight",0);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("labelID",2);
        map2.put("labelName","节油明星");
        map2.put("labelWeight",0);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("labelID",3);
        map3.put("labelName","市区王子");
        map3.put("labelWeight",0);
        Map<String, Object> map4 = new HashMap<>();
        map4.put("labelID",4);
        map4.put("labelName","三急得分高手");
        map4.put("labelWeight",0);
        Map<String, Object> map5 = new HashMap<>();  map1.put("labelID",1);
        map5.put("labelID",5);
        map5.put("labelName","疲劳驾驶得分高手");
        map5.put("labelWeight",0);
        maps.add(map1);
        maps.add(map2);
        maps.add(map3);
        maps.add(map4);
        maps.add(map5);
    }

    public static void main(String[] args) {
        String timeFormat = configration.getString("timeFormat");
        System.out.println(timeFormat);

    }

}
