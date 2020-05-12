package com.fengjunlin.accident.prediction.model.web.tools;
import com.fengjunlin.accident.prediction.model.web.model.drivingmodel.BaseDataPoint;
import com.fengjunlin.accident.prediction.model.web.model.drivingmodel.Gps;
import com.fengjunlin.accident.prediction.model.web.model.drivingmodel.OriginalDataPoint;
import com.fengjunlin.accident.prediction.model.web.model.request.OriginalDataRequest;
import com.fengjunlin.accident.prediction.model.web.tools.constants.RequestConstants;
import com.fengjunlin.accident.prediction.model.web.utils.DateUtils;
import com.fengjunlin.accident.prediction.model.web.utils.PositionUtil;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * @Description 重要用来处理基础数据，注入到spring容器中
 * @Author fengjl
 * @Date 2019/6/21 15:14
 * @Version 1.0
 **/
@Component
public class LoadBaseData {

     /* 经纬度类型，0：百度,1：高德,2：wgs-84*/
    public List<BaseDataPoint> handerBaseData(OriginalDataRequest originalDataRequest){
        String lngAndLatType = originalDataRequest.getLngAndLatType();
        String timeType = originalDataRequest.getTimeType();
        List<OriginalDataPoint> list = originalDataRequest.getData();
        List<BaseDataPoint> resultList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            OriginalDataPoint p = list.get(i);
            // 如果这个数据不符合，我们需要的计算条件，需要都把它过滤掉。
            boolean bool = p.checkData(timeType);
            String lat = p.getLat();
            String lng = p.getLng();
            String time = p.getTime();
            Long longTime = DateUtils.getLongTime(time, RequestConstants.mapping.get(timeType));
            if (longTime == null) {
                continue;
            }
            if (bool) {
                // 换行经纬度和统一时间格式，然后再封装成为模型数据的基础数据点
                switch (lngAndLatType){
                    case "0":
                        try {
                            Gps gps0 = PositionUtil.bd09_To_Gcj02(lat, lng);
                            BaseDataPoint point = new BaseDataPoint(gps0.getWgLon(), gps0.getWgLat(), longTime);
                            resultList.add(point);
                        } catch (Exception e) {
                            e.printStackTrace();
                            break;
                        }
                        break;
                    case "2":
                        try {
                            Gps gps1 = PositionUtil.gps84_To_Gcj02(lat, lng);
                            BaseDataPoint point1 = new BaseDataPoint(gps1.getWgLon(), gps1.getWgLat(), longTime);
                            resultList.add(point1);
                        } catch (Exception e) {
                            e.printStackTrace();
                            break;
                        }
                        break;
                     default:
                         double doubleLng = Double.parseDouble(lng);
                         double doubleLat = Double.parseDouble(lat);
                         BaseDataPoint point2 = new BaseDataPoint(doubleLng, doubleLat, longTime);
                         resultList.add(point2);
                         break;
                }
            }
        }
        Collections.sort(resultList);
        /**
         * 1、先把缺少字段的数据过滤掉
         * 2、然后对数据安装时间进行排序
         * 3、经纬度进行统一的转换
         */
        return resultList;
    }
}
