package com.fengjunlin.accident.prediction.model.web.tools.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 请求数据的常量类
 * @Author fengjl
 * @Date 2019/6/21 16:25
 * @Version 1.0
 **/

public class RequestConstants {
public static  Map<String,String> mapping;
         static {
             mapping = new HashMap<>(16);
             mapping.put("0","yyyyMMddHHmmss");
             mapping.put("1","yyyyMMddHHmmssSSS");
             mapping.put("2","yyyy-MM-dd HH:mm:ss");
         }
}
