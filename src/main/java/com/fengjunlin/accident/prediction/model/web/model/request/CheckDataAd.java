package com.fengjunlin.accident.prediction.model.web.model.request;

import com.fengjunlin.accident.prediction.model.web.controller.v1.exception.ServerException;
import com.fengjunlin.accident.prediction.model.web.tools.constants.ServerConstants;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;

/**
 * @Description
 * @Author fengjl
 * @Date 2019/6/18 11:58
 * @Version 1.0
 **/
public class CheckDataAd {
    /**
     * 经度
     */
    private String lng;
    /**
     * 纬度
     */
    private String lat;
    /**
     *  城市编码
     */
    private String adcode;
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

        if (StringUtils.isEmpty(adcode)) {
            throw new ServerException()
                    .setCode(ServerConstants.RETURN_CODE_NULL)
                    .setMessage("城市编码不能为空")
                    .setSuccess(false);
        }
    }

    public CheckDataAd() {
    }

    public CheckDataAd(String lng, String lat, String adcode) {
        this.lng = lng;
        this.lat = lat;
        this.adcode = adcode;
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

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckDataAd)) return false;
        CheckDataAd that = (CheckDataAd) o;
        if (getLng() != null ? !getLng().equals(that.getLng()) : that.getLng() != null) return false;
        if (getLat() != null ? !getLat().equals(that.getLat()) : that.getLat() != null) return false;
        return getAdcode() != null ? getAdcode().equals(that.getAdcode()) : that.getAdcode() == null;
    }

    @Override
    public int hashCode() {
        int result = getLng() != null ? getLng().hashCode() : 0;
        result = 31 * result + (getLat() != null ? getLat().hashCode() : 0);
        result = 31 * result + (getAdcode() != null ? getAdcode().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "checkDataAd{" +
                "lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", adcode='" + adcode + '\'' +
                '}';
    }
}
