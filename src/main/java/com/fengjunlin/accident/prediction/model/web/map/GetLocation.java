
package com.fengjunlin.accident.prediction.model.web.map;
import com.fengjunlin.accident.prediction.model.web.utils.ReadProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 主要用来解析经纬度
 */

@Component
@Order(value = 1)
public class GetLocation {
    /*
    加载围栏坐标信息，进入内存中
     */

        static {
            String url = ReadProperty.getConfigData("jdbc.url");
            String password = ReadProperty.getConfigData("jdbc.password");
            String userName = ReadProperty.getConfigData("jdbc.user");
            String driver = ReadProperty.getConfigData("jdbc.driver");
            String sql = ReadProperty.getConfigData("sql");
            loadBoxData.loadMaxAndMinData(sql,url,userName,password,driver);
            loadBoxData.InitPolygonData(sql, url, userName, password, driver);
        }


/**
     * 传入经纬度，确定坐标的具体位置
     *
     * @param lng 经度
     * @param lat 维度
     * @return 返回具体的地域位置信息
     */

    public  static String getLocation(String lng, String lat ){
            // 经度
            Double doubleLng = Double.parseDouble(lng);
            // 纬度
            Double doubleLat = Double.parseDouble(lat);
            String result = loadBoxData.GetCityByPoint(doubleLng, doubleLat);
             if ( result.equals("null")){
                 return null;
             }
            return result;
        }

    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        for (int i =0; i<100000;i++) {
            String location = getLocation("116.454622", "39.751023");
        }
        long l1 = System.currentTimeMillis();
        System.out.println((l1-l)/1000);
    }
}

