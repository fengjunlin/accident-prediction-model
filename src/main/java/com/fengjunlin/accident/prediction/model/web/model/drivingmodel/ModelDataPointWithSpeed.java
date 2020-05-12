package com.fengjunlin.accident.prediction.model.web.model.drivingmodel;
/**
 * @Description
 * @Author fengjl
 * @Date 2019/7/1 21:41
 * @Version 1.0
 **/
public class ModelDataPointWithSpeed implements Comparable<ModelDataPointWithSpeed>{
    /**
     * 加速度
     */
    private Double acceleratedSpeed;
    /**
     * 上一个数据点的速度
     */
    private Double preSpeed;
    /**
     * 下一个时间点的速度
     */
    private Double nextSpeed;
    /**
     * location和时间点
     */
    private BaseDataPointWithSpeed baseDataPointWithSpeed;
    /**
     * 时间长度
     */
    private Double longTime;



    public ModelDataPointWithSpeed() {
    }

    public ModelDataPointWithSpeed(Double acceleratedSpeed, Double preSpeed, Double nextSpeed, BaseDataPointWithSpeed baseDataPointWithSpeed, Double longTime) {
        this.acceleratedSpeed = acceleratedSpeed;
        this.preSpeed = preSpeed;
        this.nextSpeed = nextSpeed;
        this.baseDataPointWithSpeed = baseDataPointWithSpeed;
        this.longTime = longTime;
    }

    public Double getAcceleratedSpeed() {
        return acceleratedSpeed;
    }

    public void setAcceleratedSpeed(Double acceleratedSpeed) {
        this.acceleratedSpeed = acceleratedSpeed;
    }

    public Double getPreSpeed() {
        return preSpeed;
    }

    public void setPreSpeed(Double preSpeed) {
        this.preSpeed = preSpeed;
    }

    public Double getNextSpeed() {
        return nextSpeed;
    }

    public void setNextSpeed(Double nextSpeed) {
        this.nextSpeed = nextSpeed;
    }

    public BaseDataPointWithSpeed getBaseDataPointWithSpeed() {
        return baseDataPointWithSpeed;
    }

    public void setBaseDataPointWithSpeed(BaseDataPointWithSpeed baseDataPointWithSpeed) {
        this.baseDataPointWithSpeed = baseDataPointWithSpeed;
    }

    public Double getLongTime() {
        return longTime;
    }

    public void setLongTime(Double longTime) {
        this.longTime = longTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModelDataPointWithSpeed)) return false;

        ModelDataPointWithSpeed that = (ModelDataPointWithSpeed) o;

        if (getAcceleratedSpeed() != null ? !getAcceleratedSpeed().equals(that.getAcceleratedSpeed()) : that.getAcceleratedSpeed() != null)
            return false;
        if (getPreSpeed() != null ? !getPreSpeed().equals(that.getPreSpeed()) : that.getPreSpeed() != null)
            return false;
        if (getNextSpeed() != null ? !getNextSpeed().equals(that.getNextSpeed()) : that.getNextSpeed() != null)
            return false;
        if (getBaseDataPointWithSpeed() != null ? !getBaseDataPointWithSpeed().equals(that.getBaseDataPointWithSpeed()) : that.getBaseDataPointWithSpeed() != null)
            return false;
        return getLongTime() != null ? getLongTime().equals(that.getLongTime()) : that.getLongTime() == null;
    }

    @Override
    public int hashCode() {
        int result = getAcceleratedSpeed() != null ? getAcceleratedSpeed().hashCode() : 0;
        result = 31 * result + (getPreSpeed() != null ? getPreSpeed().hashCode() : 0);
        result = 31 * result + (getNextSpeed() != null ? getNextSpeed().hashCode() : 0);
        result = 31 * result + (getBaseDataPointWithSpeed() != null ? getBaseDataPointWithSpeed().hashCode() : 0);
        result = 31 * result + (getLongTime() != null ? getLongTime().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ModelDataPointWithSpeed{" +
                "acceleratedSpeed=" + acceleratedSpeed +
                ", preSpeed=" + preSpeed +
                ", nextSpeed=" + nextSpeed +
                ", baseDataPointWithSpeed=" + baseDataPointWithSpeed +
                ", longTime=" + longTime +
                '}';
    }

    @Override
    public int compareTo(ModelDataPointWithSpeed o) {
        if(baseDataPointWithSpeed.getTime() < o.baseDataPointWithSpeed.getTime()){
            return -1;
        }
        if(baseDataPointWithSpeed.getTime() > o.baseDataPointWithSpeed.getTime()){
            return 1;
        }
        return 0;
    }
}
