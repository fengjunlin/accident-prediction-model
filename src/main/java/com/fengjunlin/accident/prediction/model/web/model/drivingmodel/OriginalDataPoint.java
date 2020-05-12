package com.fengjunlin.accident.prediction.model.web.model.drivingmodel;
import com.fengjunlin.accident.prediction.model.web.tools.constants.RequestConstants;
import org.apache.commons.lang.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @Description 传过来的原始数据点
 * @Author fengjl
 * @Date 2019/6/20 14:43
 * @Version 1.0
 **/
public class OriginalDataPoint {
    /**
     * 经度
     */
    private String lng;
    /**
     * 维度
     */
    private String lat;
    /**
     * 时间
     */
    private String time;

    public boolean checkData(String timeType){
        if (StringUtils.isEmpty(this.lat) || StringUtils.isEmpty(this.lat) || StringUtils.isEmpty(time)) {
            return false;
        }

        SimpleDateFormat format = new SimpleDateFormat (RequestConstants.mapping.get(timeType));
        try {
            format.setLenient(false);
            format.parse (time);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        // 对传递过来的数据进行大概的校验
        if (!this.lat.contains(".") || !this.lng.contains(".")) {
            return false;
        }
        return true;
    }

    public OriginalDataPoint() {
    }

    public OriginalDataPoint(String lng, String lat, String time) {
        this.lng = lng;
        this.lat = lat;
        this.time = time;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OriginalDataPoint)) return false;

        OriginalDataPoint that = (OriginalDataPoint) o;

        if (getLng() != null ? !getLng().equals(that.getLng()) : that.getLng() != null) return false;
        if (getLat() != null ? !getLat().equals(that.getLat()) : that.getLat() != null) return false;
        return getTime() != null ? getTime().equals(that.getTime()) : that.getTime() == null;
    }

    @Override
    public int hashCode() {
        int result = getLng() != null ? getLng().hashCode() : 0;
        result = 31 * result + (getLat() != null ? getLat().hashCode() : 0);
        result = 31 * result + (getTime() != null ? getTime().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "originalDataPoint{" +
                "lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", time=" + time +
                '}';
    }
}
