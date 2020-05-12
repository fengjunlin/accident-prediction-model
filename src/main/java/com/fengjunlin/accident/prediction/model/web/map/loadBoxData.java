package com.fengjunlin.accident.prediction.model.web.map;
import com.alibaba.fastjson.JSON;
import com.fengjunlin.accident.prediction.model.web.model.position.MaxAndMinLnglat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * @Author fengjl
  * @Description //TODO 加载全国的地域围栏信息
  * @Date  2019/6/14
  */
public class loadBoxData {
    private static final Logger logger = LoggerFactory.getLogger(loadBoxData.class);
    /**
     * 常量 经纬度扩大10^6方，加快计算速度
     */
    public final static int BASE_LINE = (int) 1e6;
    /**
     * 全国所有的地域围栏信息
     */
//    private static List<BoxData>  cityBoxData = new ArrayList<>(5357);

    public static Map<Integer,List<MaxAndMinLnglat>> provinceBox = new HashMap<>(50);

    public static Map<Integer,List<MaxAndMinLnglat>> cityBox = new HashMap<>(800);

    public static Map<Integer,List<Integer>> mapping = new HashMap<>(1000);

    public static Map<Integer,List<BoxData>> boxDatas = new HashMap<>(1000);

    public static boolean loadMaxAndMinData(String sql, String url, String userName, String password, String driver) {

        Connection connection = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, userName, password);
            ps = connection.prepareStatement("SELECT * FROM city_shape WHERE city_shape.`level` = \"city\" OR city_shape.`level` = \"province\"");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                int adcode = Integer.parseInt(resultSet.getString("adcode"));
                String level = resultSet.getString("level");
                int minX = (int)(resultSet.getDouble("min_x")*BASE_LINE);
                int minY = (int)(resultSet.getDouble("min_y")*BASE_LINE);
                int maxX = (int)(resultSet.getDouble("max_x")*BASE_LINE);
                int maxY = (int)(resultSet.getDouble("max_y")*BASE_LINE);
                if (level != null && level.equals("province")) {
                    if (!provinceBox.containsKey(adcode)) {
                        List<MaxAndMinLnglat> v = loadMM(minX, minY, maxX, maxY);
                        provinceBox.put(adcode,v);
                    }else {
                        List<MaxAndMinLnglat> v = provinceBox.get(adcode);
                        MaxAndMinLnglat m = new MaxAndMinLnglat(new PointData(minX,minY),new PointData(maxX,maxY));
                        v.add(m);
                    }
                    continue;
                }

                if (level != null && level.equals("city")) {
                    if (!cityBox.containsKey(adcode)) {
                        MaxAndMinLnglat m = new MaxAndMinLnglat(new PointData(minX,minY),new PointData(maxX,maxY));
                        List<MaxAndMinLnglat> v = new ArrayList<>(16);
                        v.add(m);
                        cityBox.put(adcode,v);
                    }else {
                        List<MaxAndMinLnglat> v = cityBox.get(adcode);
                        MaxAndMinLnglat m = new MaxAndMinLnglat(new PointData(minX,minY),new PointData(maxX,maxY));
                        v.add(m);
                    }
                    continue;
                }
            }
            resultSet.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return false;
        }finally {
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }



    /**
     * 把mysql中的全国地域围栏的信息，加载进入内存中
     *
     * @param sql sql语句
     * @param url url
     * @param userName 用户名
     * @param password 密码+
     * @param driver jdbc客户端
     * @return 加载成功返回true，否则返回false
     */
    public static boolean InitPolygonData(String sql, String url, String userName, String password, String driver){
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, userName, password);
            ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                BoxData bd = new BoxData();
                String areaName = resultSet.getString("name");
                bd.setCityName(areaName);
                int adcode = resultSet.getInt("adcode");
                bd.setAdcode(adcode);
                int minX = resultSet.getInt("min_x");
                int minY = resultSet.getInt("min_y");
                int maxX = resultSet.getInt("max_x");
                int maxY = resultSet.getInt("max_y");
                int city = resultSet.getInt("city");
                int province = resultSet.getInt("province");
                bd.setMinPoint(new PointData(minX,minY));
                bd.setMaxPoint(new PointData(maxX,maxY));
                String crawl = resultSet.getString("shape");
                String[] crawlList = crawl.split(";");
                for (int i=0;i<crawlList.length;++i){
                    if(crawlList[i] != null){
                        String[] pts = crawlList[i].split(",");
                        if(pts.length == 2){
                            PointData pd = new PointData(
                                    (int)(double)(Double.parseDouble(pts[0])*BASE_LINE),
                                    (int)(double)(Double.parseDouble(pts[1])*BASE_LINE)
                            );
                            bd.getCityPointList().add(pd);
                        }
                    }
                }
//                cityBoxData.add(bd);
                /*建立省份城市和市城市的映射关系*/
                if (!mapping.containsKey(province)) {
                    List<Integer> l = new ArrayList<>(16);
                    l.add(city);
                    mapping.put(province,l);
                } else {
                    List<Integer> l = mapping.get(province);
                    l.add(city);
                }

                if (!boxDatas.containsKey(city)) {
                    List<BoxData> li = new ArrayList<>(16);
                    li.add(bd);
                    boxDatas.put(city,li);
                 } else {
                    List<BoxData> boxData = boxDatas.get(city);
                    boxData.add(bd);
                }


            }
            resultSet.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return false;
        }finally {
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断点在全国的具体的区域
     *
     * @param x 经度
     * @param y 维度
     * @return
     */
    public static String GetCityByPoint(double x, double y){
        try{
            HashMap<String, Object> resultMap = new HashMap<>();
            PointData pt = new PointData((int)(double)(x*BASE_LINE), (int)(double)(y*BASE_LINE));
            for (Map.Entry<Integer,List<MaxAndMinLnglat>> entry:cityBox.entrySet()) {
                Integer key = entry.getKey();
                List<MaxAndMinLnglat> value = entry.getValue();
                for (int i = 0;i<value.size();i++) {
                    MaxAndMinLnglat maxAndMin = value.get(i);
                    PointData maxLngAndLat = maxAndMin.getMaxLngAndLat();
                    PointData minLngAndLat = maxAndMin.getMinLngAndLat();
                    if (pt.getX() > minLngAndLat.getX() && pt.getY() > minLngAndLat.getY()) {
                        if (pt.getX() < maxLngAndLat.getX() && pt.getY() < maxLngAndLat.getY()) {
                            List<BoxData> boxData = boxDatas.get(key);
                            for (int w = 0; w < boxData.size(); w++) {
                                BoxData boxData1 = boxData.get(w);
                                if (boxData1.checkInBox(pt)) {
                                    resultMap.put("location",boxData1.getCityName());
                                    resultMap.put("adcode",boxData1.getAdcode());
                                    return JSON.toJSONString(resultMap);
                                }
                            }
                        }
                    }
                }

            }
            return "null";
        }
        catch (Exception ex){
            ex.printStackTrace();
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }


    public static List<MaxAndMinLnglat> loadMM(int minx,int miny,int maxx,int maxy){

        MaxAndMinLnglat m = new MaxAndMinLnglat(new PointData(minx,miny),new PointData(maxx,maxy));
        List<MaxAndMinLnglat> v = new ArrayList<>(16);
        v.add(m);
        return v;
    }

}