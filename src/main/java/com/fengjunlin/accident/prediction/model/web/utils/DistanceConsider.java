package com.fengjunlin.accident.prediction.model.web.utils;

/**
 * @Description 测算两个经纬度之间的距离，单位是米
 * @Author fengjl
 * @Date 2019/4/15 10:36
 * @Version 1.0
 **/
public class DistanceConsider {
    // 地球半径
    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * Description 计算距离
     * 参数传入：所在位置的坐标  目标位置的坐标
     * 一个参数的经纬之前要求使用","英文的逗号进行分隔
     * 返回： 输入的两个坐标点之间的距离  精确到小数点后两位
     */
    public static double getDistance(double soureceLng,double soureceLat,double targetLng ,double targetLat) {
        String location = soureceLng + "," + soureceLat;
        String target = targetLng + "," + targetLat;
        int locationCount = location.indexOf(",");
        // 源地点的经度
        double lngLocation = Double.parseDouble(location.substring(0, locationCount));
        // 源地点的维度
        double latLocation = Double.parseDouble(location.substring(locationCount + 1, location.length()));
        int targetCount = target.indexOf(",");
        // 目标地点的经度
        double lngTarget = Double.parseDouble(target.substring(0, targetCount));
        // 目标地点的维度
        double latTarget = Double.parseDouble(target.substring(targetCount + 1, target.length()));

        double lat1 = rad(latLocation);
        double lat2 = rad(latTarget);
        double lat = lat1 - lat2;
        double lng = rad(lngLocation) - rad(lngTarget);
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(lat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(lng / 2), 2)));
        distance = distance * EARTH_RADIUS;
        distance = Math.round(distance * 10000d) / 10000d;
        distance = distance * 1000;
        return distance;
    }

    public static void main(String args[]) {
        String location = "116.477741,39.995816";
        String target = "116.480439,39.994016";
        System.out.println(getDistance(Double.parseDouble("116.477741"),Double.parseDouble("39.995816") ,Double.parseDouble("116.480439"),Double.parseDouble("39.994016")));
    }
}
