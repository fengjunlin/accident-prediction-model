package com.fengjunlin.accident.prediction.model.web.model.drivingmodel;

/**
 * @Description 驾驶碰撞模型中可分析的模型数据点，包含加速度，preSpeed，nextSpeed，longTime，timePoint，locationPoint
 * @Author fengjl
 * @Date 2019/6/20 14:05
 * @Version 1.0
 **/
public class ModelDataPoint implements Comparable<ModelDataPoint>{
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
    private BaseDataPoint baseDataPoint;
    /**
     * 时间长度
     */
    private Double longTime;

    /**
     * 数据第几段行程
     */
    private Integer markOne;
    /**
     * 本次行程中改模型数据点的位置
     */
    private Integer markTwo;

    public ModelDataPoint() {
    }

    public ModelDataPoint(Double acceleratedSpeed, Double preSpeed, Double nextSpeed, BaseDataPoint baseDataPoint, Double longTime) {
        this.acceleratedSpeed = acceleratedSpeed;
        this.preSpeed = preSpeed;
        this.nextSpeed = nextSpeed;
        this.baseDataPoint = baseDataPoint;
        this.longTime = longTime;
    }

    @Override
    public int compareTo(ModelDataPoint o) {
        if(baseDataPoint.getTime() < o.baseDataPoint.getTime()){
            return -1;
        }
        if(baseDataPoint.getTime() > o.baseDataPoint.getTime()){
            return 1;
        }
        return 0;
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

    public BaseDataPoint getBaseDataPoint() {
        return baseDataPoint;
    }

    public void setBaseDataPoint(BaseDataPoint baseDataPoint) {
        this.baseDataPoint = baseDataPoint;
    }

    public Double getLongTime() {
        return longTime;
    }

    public void setLongTime(Double longTime) {
        this.longTime = longTime;
    }

    public Integer getMarkOne() {
        return markOne;
    }

    public void setMarkOne(Integer markOne) {
        this.markOne = markOne;
    }

    public Integer getMarkTwo() {
        return markTwo;
    }

    public void setMarkTwo(Integer markTwo) {
        this.markTwo = markTwo;
    }
    @Override
    public String toString() {
        return "ModelDataPoint{" +
                "acceleratedSpeed=" + acceleratedSpeed +
                ", preSpeed=" + preSpeed +
                ", nextSpeed=" + nextSpeed +
                ", baseDataPoint=" + baseDataPoint +
                ", longTime=" + longTime +
                ", markOne=" + markOne +
                ", markTwo=" + markTwo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModelDataPoint that = (ModelDataPoint) o;

        if (acceleratedSpeed != null ? !acceleratedSpeed.equals(that.acceleratedSpeed) : that.acceleratedSpeed != null)
            return false;
        if (preSpeed != null ? !preSpeed.equals(that.preSpeed) : that.preSpeed != null) return false;
        if (nextSpeed != null ? !nextSpeed.equals(that.nextSpeed) : that.nextSpeed != null) return false;
        if (baseDataPoint != null ? !baseDataPoint.equals(that.baseDataPoint) : that.baseDataPoint != null)
            return false;
        if (longTime != null ? !longTime.equals(that.longTime) : that.longTime != null) return false;
        if (markOne != null ? !markOne.equals(that.markOne) : that.markOne != null) return false;
        return markTwo != null ? markTwo.equals(that.markTwo) : that.markTwo == null;
    }

    @Override
    public int hashCode() {
        int result = acceleratedSpeed != null ? acceleratedSpeed.hashCode() : 0;
        result = 31 * result + (preSpeed != null ? preSpeed.hashCode() : 0);
        result = 31 * result + (nextSpeed != null ? nextSpeed.hashCode() : 0);
        result = 31 * result + (baseDataPoint != null ? baseDataPoint.hashCode() : 0);
        result = 31 * result + (longTime != null ? longTime.hashCode() : 0);
        result = 31 * result + (markOne != null ? markOne.hashCode() : 0);
        result = 31 * result + (markTwo != null ? markTwo.hashCode() : 0);
        return result;
    }
}
