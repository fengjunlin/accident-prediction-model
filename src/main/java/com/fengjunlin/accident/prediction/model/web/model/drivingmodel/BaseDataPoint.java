package com.fengjunlin.accident.prediction.model.web.model.drivingmodel;

import com.fengjunlin.accident.prediction.model.web.tools.DataHander;
import com.fengjunlin.accident.prediction.model.web.utils.DistanceConsider;

/**
 * @Description 最基础的数据点，包括最原始的经纬度和时间。如其中一个为空就必须过滤掉。同时实现经纬度时间从小到大的排序，
 *               这样拿到的经纬度才是车辆的轨迹
 * @Author fengjl
 * @Date 2019/6/20 14:52
 * @Version 1.0
 **/
public class BaseDataPoint implements Comparable<BaseDataPoint>{

    /**
     * 经度
     */
    private double lng;
    /**
     * 维度
     */
    private double lat;
    /**
     * 时间点,已经转换成为了毫秒
     */
    private Long time;


    /**
     * 获取两个计数点之间的速度
     */
    public double getSpeedBetweenTwoPoints(BaseDataPoint baseDataPoint) {
        // 两个计数点之间的距离,单位 m
        double distanceBetweentTwoPoints = DistanceConsider.getDistance(this.lng,this.lat,baseDataPoint.lng,baseDataPoint.lat);
        // 两个计数点之间的时间，单位 s
        double timeBetweenTwoPoints = DataHander.getTimeBetweenTwoPoints(this.time,baseDataPoint.time);
        double averageSpeed = distanceBetweentTwoPoints / timeBetweenTwoPoints;
        return averageSpeed;
    }

    /**
     * 计算出两个基础数据点之间的距离
     *
     * @param baseDataPoint 行程中紧挨着的下一个数据点
     * @return 两个基础数据点之间的距离 单位 m
     */
    public double getDistanceBetweentTwoPoints(BaseDataPoint baseDataPoint) {
        // 两个计数点之间的距离,单位 m
        double distanceBetweentTwoPoints = DistanceConsider.getDistance(this.lng,this.lat,baseDataPoint.lng,baseDataPoint.lat);
        return distanceBetweentTwoPoints;
    }

    /**
     * 算出行程中每两个节点之间的时间差，以便对行程进行切分
     *
     * @param baseDataPoint 行程中紧挨着的下一个数据点
     * @return 时间差 单位 s
     */
    public double getTimeBetweenTwoPoints(BaseDataPoint baseDataPoint) {
        // 两个计数点之间的时间，单位 s
        double timeBetweenTwoPoints = DataHander.getTimeBetweenTwoPoints(this.time,baseDataPoint.time);
        return timeBetweenTwoPoints;
    }

    /**
     * 获取基础的数据模型店
     * @param baseDataPoint 紧邻着的下一个数据点
     * @return 模型数据点
     */
    public ModelDataPoint getModeDataPoint(BaseDataPoint baseDataPoint) {
        ModelDataPoint modelDataPoint = new ModelDataPoint();
        modelDataPoint.setPreSpeed(getSpeedBetweenTwoPoints(baseDataPoint));
        modelDataPoint.setBaseDataPoint(baseDataPoint);
        // 这个时间仅仅是一部分
        modelDataPoint.setLongTime(getTimeBetweenTwoPoints(baseDataPoint) / 2);
        return modelDataPoint;
    }

    public BaseDataPoint(double lng, double lat, Long time) {
        this.lng = lng;
        this.lat = lat;
        this.time = time;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseDataPoint that = (BaseDataPoint) o;

        if (Double.compare(that.lng, lng) != 0) return false;
        if (Double.compare(that.lat, lat) != 0) return false;
        return time.equals(that.time);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(lng);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + time.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "BaseDataPoint{" +
                "lng=" + lng +
                ", lat=" + lat +
                ", time=" + time +
                '}';
    }

    @Override
    public int compareTo(BaseDataPoint o) {
        if(time < o.time){
            return -1;

        }
        if(time > o.time){
            return 1;
        }
        return 0;
    }
}
