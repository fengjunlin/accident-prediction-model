package com.fengjunlin.accident.prediction.model.web.controller.v1;
import com.fengjunlin.accident.prediction.model.web.controller.v1.exception.ServerException;
import com.fengjunlin.accident.prediction.model.web.model.request.CheckDataAd;
import com.fengjunlin.accident.prediction.model.web.model.request.PaseLagLat;
import com.fengjunlin.accident.prediction.model.web.service.position.locationService;
import com.fengjunlin.accident.prediction.model.web.tools.ServerAck;
import com.fengjunlin.accident.prediction.model.web.tools.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.text.ParseException;
import java.util.List;
import java.util.Map;


/**
 * @Description 地域解析接口controller
 * @Author fengjl
 * @Date 2019/6/14 15:51
 * @Version 1.0
 **/

@RestController
@RequestMapping("/api/v1/location")
public class SearchDataController {
    private static final Logger logger = LoggerFactory.getLogger(SearchDataController.class);


    @Autowired
    private ServerAck serverAck;

    @Autowired
    private locationService locationService;


/**
     * 经纬度地域接口
     *
     * @param paseLagLat
     * @throws ServerException
     * @throws ParseException
     * @return 返回解析后的结果
     */

    @RequestMapping(value = "/paseLngAndLat",method = {RequestMethod.GET,RequestMethod.POST})
    public ResultData paseLngAndLat(PaseLagLat paseLagLat) throws Exception {
        logger.info("**************** 请求需要解析的经纬度："+paseLagLat+" ****************");
        paseLagLat.paramCheck();
        String location = locationService.paseLocation(paseLagLat);
        logger.info("**************** 解析后的结果数据为："+location+" ****************");
        return serverAck.getSuccess().setData(location);
    }


/**
     * 检查经纬度是否在给定的编码中
     *
     * @param checkDataAd
     * @return true：在，false：不在
     */

    @RequestMapping(value = "checkDataWithAdcode",method = {RequestMethod.GET,RequestMethod.POST})
    public ResultData checkDataWithAdcode(CheckDataAd checkDataAd) throws Exception{
        logger.info("求取过来的数据为："+checkDataAd);
        checkDataAd.paramCheck();
        Boolean bool = locationService.regionalDiscrimination(checkDataAd);
        return serverAck.getSuccess().setData(bool);
    }

/**
     * 主要用于解析多个地域接口信息
     *
     * @param list 多个需要解析的地域位置
     * @return 返回解析后的结果
     */

    @RequestMapping(value = "paseLngAndLats",method = {RequestMethod.GET,RequestMethod.POST})
    public ResultData paseLngAndLats(List<PaseLagLat> list) throws Exception{
        logger.info("求取过来的数据为："+list);
        for (PaseLagLat paseLagLat:list) {
            paseLagLat.paramCheck();
        }
        Map<PaseLagLat, String> resultMap = locationService.paseLocations(list);
        return serverAck.getSuccess().setData(resultMap);
    }
}




