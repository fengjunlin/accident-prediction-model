package com.fengjunlin.accident.prediction.model.web.data;
import com.fengjunlin.accident.prediction.model.web.model.drivingmodel.BaseDataPoint;
import com.fengjunlin.accident.prediction.model.web.model.drivingmodel.BaseDataPointWithSpeed;
import com.fengjunlin.accident.prediction.model.web.model.drivingmodel.ModelDataPoint;
import com.fengjunlin.accident.prediction.model.web.model.drivingmodel.ModelDataPointWithSpeed;
import com.fengjunlin.accident.prediction.model.web.tools.constants.DataConstants;
import com.fengjunlin.accident.prediction.model.web.utils.Configuration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * @Description 获取分析数据，当模型成熟以后，这里可能会是读取kafka中数据，或者hbase中数据，目前测试阶段读取配置文件中数据
 * @Author fengjl
 * @Date 2019/4/15 15:07
 * @Version 1.0
 **/
@SuppressWarnings("all")
public class ObtainAnalysisData {

    /**
      * @Author fengjl
      * @Description //TODO 对行程数据进行切分，防止两个相邻数据节点相差时间过长，对分析结果造成影响
      * @Date  2019/4/16
      * @return 切分之后的行程
      */
    public List<List<BaseDataPoint>> shardStrokeData( List<BaseDataPoint> tripDataList) {
        // 获取配置文件中切分数据时长
        Long longTime = Configuration.configration.getLong(DataConstants.SHARD_DATA_POINTS);
        List<List<BaseDataPoint>> travelsDataPoints = new ArrayList<>(16);
        int flag = 0;
        for (int i = 0; i < tripDataList.size()-1; i++) {
            double timeBetweenTwoPoints = tripDataList.get(i).getTimeBetweenTwoPoints(tripDataList.get(i + 1));
            if (timeBetweenTwoPoints > longTime) {
                 List<BaseDataPoint> dataPoints = new ArrayList<>(16);
                for (int j = flag; j <= i; j++) {
                    dataPoints.add(tripDataList.get(j));
                }
                travelsDataPoints.add(dataPoints);
                flag = i + 1;
            }
            if (i == tripDataList.size() -2) {
                List<BaseDataPoint> dataPoints = new ArrayList<>(16);
                for (int j = flag; j <= i; j++) {
                    dataPoints.add(tripDataList.get(j));
                }
                travelsDataPoints.add(dataPoints);
            }
        }
        return travelsDataPoints;
    }
    /**
     * @Author fengjl
     * @Description //TODO 对行程数据进行切分，防止两个相邻数据节点相差时间过长，对分析结果造成影响
     * @Date  2019/4/16
     * @return 切分之后的行程
     */
    public List<List<BaseDataPointWithSpeed>> shardStrokeDataWithSpeed( List<BaseDataPointWithSpeed> tripDataList) {
        // 获取配置文件中切分数据时长
        Long longTime = Configuration.configration.getLong(DataConstants.SHARD_DATA_POINTS);
        List<List<BaseDataPointWithSpeed>> travelsDataPoints = new ArrayList<>(16);
        int flag = 0;
        for (int i = 0; i < tripDataList.size()-1; i++) {
            double timeBetweenTwoPoints = tripDataList.get(i).getTimeBetweenTwoPoints(tripDataList.get(i + 1));
            if (timeBetweenTwoPoints > longTime) {
                List<BaseDataPointWithSpeed> dataPoints = new ArrayList<>(16);
                for (int j = flag; j <= i; j++) {
                    dataPoints.add(tripDataList.get(j));
                }
                travelsDataPoints.add(dataPoints);
                flag = i + 1;
            }

            if (i == tripDataList.size() -2) {
                List<BaseDataPointWithSpeed> dataPoints = new ArrayList<>(16);
                for (int j = flag; j <= i; j++) {
                    dataPoints.add(tripDataList.get(j));
                }
                travelsDataPoints.add(dataPoints);
            }
        }
        return travelsDataPoints;
    }

    /**
      * @Author fengjl
      * @Description //TODO 根据切分好了的行程数据点，算出模型数据点
      * @Date  2019/4/16
      * @Param
      * @return
      */
    public  List<List<ModelDataPoint>> loadModelDataPoints(List<List<BaseDataPoint>> mutipleTravel) {
        List<List<ModelDataPoint>> mutipleModelTravel = new ArrayList<>(16);
        for (int i = 0; i < mutipleTravel.size(); i++) {
            List<BaseDataPoint> dataPoints = mutipleTravel.get(i);
            List<ModelDataPoint> modelDataPointList = new ArrayList<>(16);
            for (int j = 0; j < dataPoints.size()-2; j++) {
                ModelDataPoint modeDataPoint = dataPoints.get(j).getModeDataPoint(dataPoints.get(j + 1));
                ModelDataPoint nextModeDataPoint = dataPoints.get(j + 1).getModeDataPoint(dataPoints.get(j + 2));
                modeDataPoint.setLongTime(modeDataPoint.getLongTime()+nextModeDataPoint.getLongTime());
                modeDataPoint.setNextSpeed(nextModeDataPoint.getPreSpeed());
                modeDataPoint.setAcceleratedSpeed((nextModeDataPoint.getPreSpeed() - modeDataPoint.getPreSpeed()) / (modeDataPoint.getLongTime()+nextModeDataPoint.getLongTime()));
                // 给当前模型数据点打上位置标签
                modeDataPoint.setMarkOne(i);
                modeDataPoint.setMarkTwo(j);
                modelDataPointList.add(modeDataPoint);
            }
            Collections.sort(modelDataPointList);
            mutipleModelTravel.add(modelDataPointList);
        }
        return mutipleModelTravel;
    }

    /**
     * @Author fengjl
     * @Description //TODO 根据切分好了的行程数据点，算出模型数据点
     * @Date  2019/4/16
     * @Param
     * @return
     */
    public  List<List<ModelDataPointWithSpeed>> loadModelDataPointsWithSpeed(List<List<BaseDataPointWithSpeed>> mutipleTravel) {
        List<List<ModelDataPointWithSpeed>> mutipleModelTravel = new ArrayList<>(16);
        for (int i = 0; i < mutipleTravel.size(); i++) {
            List<BaseDataPointWithSpeed> dataPoints = mutipleTravel.get(i);
            List<ModelDataPointWithSpeed> modelDataPointList = new ArrayList<>(16);
            for (int j = 0; j < dataPoints.size()-1; j++) {
                ModelDataPointWithSpeed modeDataPoint = dataPoints.get(j).getModelDataPointWithSpeed(dataPoints.get(j + 1));

            }
            Collections.sort(modelDataPointList);
            mutipleModelTravel.add(modelDataPointList);
        }
        return mutipleModelTravel;
    }

    public  static List<List<ModelDataPoint>> loadModelDataPoint(List<BaseDataPoint> dataPoints){
        ObtainAnalysisData obtainAnalysisData = new ObtainAnalysisData();
        List<List<BaseDataPoint>> mutipleTravel = obtainAnalysisData.shardStrokeData(dataPoints);
        List<List<ModelDataPoint>> mutipleModelTravel = obtainAnalysisData.loadModelDataPoints(mutipleTravel);
        return mutipleModelTravel;
    }

  /*  public  static List<List<ModelDataPointWithSpeed>> loadModelDataPointWithSpeed(List<BaseDataPointWithSpeed> dataPoints){
        ObtainAnalysisData obtainAnalysisData = new ObtainAnalysisData();
        List<List<BaseDataPointWithSpeed>> mutipleTravel = obtainAnalysisData.shardStrokeDataWithSpeed(dataPoints);
        List<List<ModelDataPointWithSpeed>> mutipleModelTravel = obtainAnalysisData.loadModelDataPointsWithSpeed(mutipleTravel);
        return mutipleModelTravel;
    }*/
}
