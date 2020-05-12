package com.fengjunlin.accident.prediction.model.web.model.drivingmodel;

import com.fengjunlin.accident.prediction.model.web.tools.DataHander;

/**
 * @Description 有速度版本的
 * @Author fengjl
 * @Date 2019/7/1 17:52
 * @Version 1.0
 **/
public class BaseDataPointWithSpeed implements Comparable<BaseDataPointWithSpeed>{
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
     * speed 设备传递过来的速度
     */
    private double speed;

    /**
     * 获取基础的数据模型店
     * @param baseDataPointWithSpeed 紧邻着的下一个数据点
     * @return 模型数据点
     */
    public ModelDataPointWithSpeed getModelDataPointWithSpeed(BaseDataPointWithSpeed baseDataPointWithSpeed) {
        ModelDataPointWithSpeed modelDataPointWithSpeed = new ModelDataPointWithSpeed();
        modelDataPointWithSpeed.setPreSpeed(this.speed);
        modelDataPointWithSpeed.setBaseDataPointWithSpeed(baseDataPointWithSpeed);
        modelDataPointWithSpeed.setNextSpeed(baseDataPointWithSpeed.speed);
        modelDataPointWithSpeed.setAcceleratedSpeed((baseDataPointWithSpeed.speed-this.speed) / 3.6 / getTimeBetweenTwoPoints(baseDataPointWithSpeed));
        // 这个时间仅仅是一部分
        modelDataPointWithSpeed.setLongTime(getTimeBetweenTwoPoints(baseDataPointWithSpeed));
        return modelDataPointWithSpeed;
    }

    /**
     * 算出行程中每两个节点之间的时间差，以便对行程进行切分
     *
     * @param baseDataPointWithSpeed 行程中紧挨着的下一个数据点
     * @return 时间差 单位 s
     */
    public double getTimeBetweenTwoPoints(BaseDataPointWithSpeed baseDataPointWithSpeed) {
        // 两个计数点之间的时间，单位 s
        double timeBetweenTwoPoints = DataHander.getTimeBetweenTwoPoints(this.time,baseDataPointWithSpeed.time);
        return timeBetweenTwoPoints;
    }



    public BaseDataPointWithSpeed() {

    }

    public BaseDataPointWithSpeed(double lng, double lat, Long time, double speed) {
        this.lng = lng;
        this.lat = lat;
        this.time = time;
        this.speed = speed;
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

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof BaseDataPointWithSpeed)) return false;

        BaseDataPointWithSpeed that = (BaseDataPointWithSpeed) o;

        if (Double.compare(that.getLng(), getLng()) != 0) return false;
        if (Double.compare(that.getLat(), getLat()) != 0) return false;
        if (Double.compare(that.getSpeed(), getSpeed()) != 0) return false;
        return getTime() != null ? getTime().equals(that.getTime()) : that.getTime() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getLng());
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getLat());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getTime() != null ? getTime().hashCode() : 0);
        temp = Double.doubleToLongBits(getSpeed());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "BaseDataPointWithSpeed{" +
                "lng=" + lng +
                ", lat=" + lat +
                ", time=" + time +
                ", speed=" + speed +
                '}';
    }


    @Override
    public int compareTo(BaseDataPointWithSpeed o) {
        if(time < o.time){
            return -1;

        }
        if(time > o.time){
            return 1;
        }
        return 0;
    }

}
