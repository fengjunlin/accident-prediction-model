package com.fengjunlin.accident.prediction.model.web.utils;
import com.alibaba.fastjson.JSONObject;

import java.io.InputStream;

/**
 * @Description
 * @Author fengjl
 * @Date 2019/4/15 14:00
 * @Version 1.0
 **/
public class ReadFile {
    public static JSONObject getConfigData(String configurationName ){
        try{
            InputStream in = ReadProperty.class.getResourceAsStream("/"+configurationName);
            byte b[] = new byte[1024*100];
            int len = 0;
            int temp=0;          //所有读取的内容都使用temp接收
            while((temp=in.read())!=-1){    //当没有读取完时，继续读取
                b[len]=(byte)temp;
                len++;
            }
            in.close();
            return (JSONObject) JSONObject.parse(new String(b,0,len));
        }catch(Exception e){
            return null;
        }
    }
}
