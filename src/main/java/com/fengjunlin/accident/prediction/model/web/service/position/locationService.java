package com.fengjunlin.accident.prediction.model.web.service.position;
import com.fengjunlin.accident.prediction.model.web.model.request.CheckDataAd;
import com.fengjunlin.accident.prediction.model.web.model.request.PaseLagLat;
import java.util.List;
import java.util.Map;

/**
 * @Description 经纬度定位的service层
 * @Author fengjl
 * @Date 2019/6/14 10:05
 * @Version 1.0
 **/

public interface locationService {


/**
     * 返回经过算法定位后的地域位置,判定单个位置
     *
     * @param paseLagLat 经度，纬度封装后的对象
     * @return 返回经过算法定位后的经纬度地域位置
     */

     String paseLocation(PaseLagLat paseLagLat) throws Exception;


/**
     * 确定经纬度是否在给定的城市编码中
     *
     * @param checkDataAd 经度,维度,需要判别的城市编码
     * @return true：在这个传入的城市编码中，false：不在这个传入的城市编码中
     */

     Boolean regionalDiscrimination(CheckDataAd checkDataAd) throws Exception;


/**
     * 判定需要多个地域位置
     *
     * @param lngAndLats 需要判定的经纬度数据的集合
     * @return 返回经纬度判定结果
     */

     Map<PaseLagLat,String> paseLocations(List<PaseLagLat> lngAndLats) throws Exception;

}


