package com.fengjunlin.accident.prediction.model.web.model.request;

import com.fengjunlin.accident.prediction.model.web.controller.v1.exception.ServerException;
import com.fengjunlin.accident.prediction.model.web.tools.constants.ServerConstants;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;

/**
 * @Description 上传的是需要定位的经纬度数据
 * @Author fengjl
 * @Date 2019/6/14 16:45
 * @Version 1.0
 **/
public class PaseLagLat {
    /**
     * 经度
     */
    private String lng;
    /**
     * 纬度
     */
    private String lat;

    /**
     * 对参数进行校验
     */
    public void paramCheck() throws ServerException, ParseException {
        if (StringUtils.isEmpty(lng)) {
            throw new ServerException()
                    .setCode(ServerConstants.RETURN_CODE_NULL)
                    .setMessage("经度不能为空")
                    .setSuccess(false);
        }

        if (StringUtils.isEmpty(lat)) {
            throw new ServerException()
                    .setCode(ServerConstants.RETURN_CODE_NULL)
                    .setMessage("纬度不能为空")
                    .setSuccess(false);
        }

    }



    public PaseLagLat() {
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

    @Override
    public String toString() {
        return "PaseLagLat{" +
                "lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaseLagLat that = (PaseLagLat) o;

        if (lng != null ? !lng.equals(that.lng) : that.lng != null) return false;
        return lat != null ? lat.equals(that.lat) : that.lat == null;
    }

    @Override
    public int hashCode() {
        int result = lng != null ? lng.hashCode() : 0;
        result = 31 * result + (lat != null ? lat.hashCode() : 0);
        return result;
    }
}
