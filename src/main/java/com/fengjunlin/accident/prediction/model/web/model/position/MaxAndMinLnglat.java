package com.fengjunlin.accident.prediction.model.web.model.position;
import com.fengjunlin.accident.prediction.model.web.map.PointData;
/**
 * @Description 最大最小经纬度pojo
 * @Author fengjl
 * @Date 2019/6/17 14:56
 * @Version 1.0
 **/
public class MaxAndMinLnglat {
    /**
     * 最小经纬度数据
     */
    private PointData minLngAndLat;
    /**
     * 最大经纬度
     */
    private PointData maxLngAndLat;



    public MaxAndMinLnglat() {
    }

    public MaxAndMinLnglat(PointData minLngAndLat, PointData maxLngAndLat) {
        this.minLngAndLat = minLngAndLat;
        this.maxLngAndLat = maxLngAndLat;
    }

    public PointData getMinLngAndLat() {
        return minLngAndLat;
    }

    public void setMinLngAndLat(PointData minLngAndLat) {
        this.minLngAndLat = minLngAndLat;
    }

    public PointData getMaxLngAndLat() {
        return maxLngAndLat;
    }

    public void setMaxLngAndLat(PointData maxLngAndLat) {
        this.maxLngAndLat = maxLngAndLat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MaxAndMinLnglat that = (MaxAndMinLnglat) o;

        if (minLngAndLat != null ? !minLngAndLat.equals(that.minLngAndLat) : that.minLngAndLat != null) return false;
        return maxLngAndLat != null ? maxLngAndLat.equals(that.maxLngAndLat) : that.maxLngAndLat == null;
    }

    @Override
    public int hashCode() {
        int result = minLngAndLat != null ? minLngAndLat.hashCode() : 0;
        result = 31 * result + (maxLngAndLat != null ? maxLngAndLat.hashCode() : 0);
        return result;
    }
}
