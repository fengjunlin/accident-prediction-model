
package com.fengjunlin.accident.prediction.model.web.service.position.impl;
import com.fengjunlin.accident.prediction.model.web.controller.v1.exception.ServerException;
import com.fengjunlin.accident.prediction.model.web.map.BoxData;
import com.fengjunlin.accident.prediction.model.web.map.GetLocation;
import com.fengjunlin.accident.prediction.model.web.map.PointData;
import com.fengjunlin.accident.prediction.model.web.map.loadBoxData;
import com.fengjunlin.accident.prediction.model.web.model.request.CheckDataAd;
import com.fengjunlin.accident.prediction.model.web.model.request.PaseLagLat;
import com.fengjunlin.accident.prediction.model.web.service.position.locationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Description 经纬度判定接口
 * @Author fengjl
 * @Date 2019/6/14 10:06
 * @Version 1.0
 **/

@Service("locationService")
public class locationServiceImpl implements locationService{
    private static final Logger logger = LoggerFactory.getLogger(locationServiceImpl.class);

    @Autowired
    private GetLocation getLocation;


    /**
     *  判定单个经纬度
     * @param paseLagLat 经度纬度封装后的地域信息
     * @return 返回改经纬度的地域位置
     */

    @Override
    public String paseLocation(PaseLagLat paseLagLat) {
        String location = getLocation.getLocation(paseLagLat.getLng(), paseLagLat.getLat());
        return location;
    }


     /**
     *  判定经纬度是否在给定的城市当中
     * @param checkDataAd 经度,维度,需要判别的城市编码
     * @return true：在，false：不在
     */

    @Override
    public Boolean regionalDiscrimination(CheckDataAd checkDataAd) throws Exception {
        String lng = checkDataAd.getLng();
        String cityCode = checkDataAd.getAdcode();
        String lat = checkDataAd.getLat();
        double x = Double.parseDouble(lng);
        double y = Double.parseDouble(lat);
        PointData pt = new PointData((int)(double)(x*loadBoxData.BASE_LINE), (int)(double)(y*loadBoxData.BASE_LINE));
        Map<Integer, List<BoxData>> boxDatas = loadBoxData.boxDatas;
        for (Map.Entry<Integer,List<BoxData>> entry:boxDatas.entrySet()) {
            List<BoxData> value = entry.getValue();
            for (BoxData boxData:value) {
                if (cityCode.equals(boxData.getAdcode().toString())) {
                    if (boxData.checkInBox(pt)) {
                        return true;
                    }else {
                        return false;
                    }
                }
            }
        }
        throw new ServerException()
                .setSuccess(false)
                .setMessage("输入的城市编码，和数据库中的不匹配");
    }


    /**
     * 需要判定经纬度的集合
     *
     * @param lngAndLats 需要判定的经纬度数据的集合
     * @return 返回每个经纬度的判定结果
     */

    @Override
    public Map<PaseLagLat, String> paseLocations(List<PaseLagLat> lngAndLats) {
        Map<PaseLagLat,String> result = new HashMap<>(16);
        for (PaseLagLat lngAndLat:lngAndLats) {
            String location = getLocation.getLocation(lngAndLat.getLng(), lngAndLat.getLat());
            result.put(lngAndLat,location);
        }
        return result;
    }
}

