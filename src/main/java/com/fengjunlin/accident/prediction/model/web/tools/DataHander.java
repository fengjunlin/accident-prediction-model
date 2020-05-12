package com.fengjunlin.accident.prediction.model.web.tools;
import com.fengjunlin.accident.prediction.model.web.model.drivingmodel.ModelDataPoint;
import com.fengjunlin.accident.prediction.model.web.tools.constants.DataConstants;
import com.fengjunlin.accident.prediction.model.web.utils.Configuration;
import com.fengjunlin.accident.prediction.model.web.utils.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
/**
 * @Description 这个类主要用来处理基本的数据操作的
 * @Author fengjl
 * @Date 2019/6/20 19:29
 * @Version 1.0
 **/
@SuppressWarnings("all")
public class DataHander {

    private static final Logger logger = LoggerFactory.getLogger(DataHander.class);


    public static Double getTimeBetweenTwoPoints(long startTime, long endTime) {
        // 计算时间差，单位毫秒,转换为秒
        double differeTime = (endTime - startTime) / 1000;
        return differeTime;
    }

    /**
     * @return 畅行数据信息
     * @Author fengjl
     * @Description //TODO 提取畅行路段信息
     * @Date 2019/4/17
     * @Param 子行程模型数据点
     */
    public static List<List<ModelDataPoint>> extractSmoothInformation(List<ModelDataPoint> list) {
        List<ModelDataPoint> modelDataPoints = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPreSpeed() > 10) {
                modelDataPoints.add(list.get(i));
            }
        }

        /*提取出来车辆高速行驶的数据点*/
        int flag = 0;
        List<List<ModelDataPoint>> smoothInformationModeDataPoints = new ArrayList<>();
        for (int i = 0; i < modelDataPoints.size() - 1; i++) {
            double timeBetweenTwoPoints = modelDataPoints.get(i).getBaseDataPoint().getTimeBetweenTwoPoints(modelDataPoints.get(i + 1).getBaseDataPoint());
            if (timeBetweenTwoPoints > 10) {
                List<ModelDataPoint> dataPoints = new ArrayList<>(16);
                for (int j = flag; j <= i; j++) {
                    dataPoints.add(modelDataPoints.get(j));
                }
                smoothInformationModeDataPoints.add(dataPoints);
                flag = i + 1;
            }
        }

        /*对部提取出来具有畅行特征的数据但是不合法的进行过滤*/
        for (int i = 0; i < smoothInformationModeDataPoints.size(); i++) {
            if (smoothInformationModeDataPoints.get(i).size() < 16) {
                smoothInformationModeDataPoints.remove(i);
                i--;
            }
        }
        return smoothInformationModeDataPoints;
    }

    /**
     * @return 拥堵路段数据
     * @Author fengjl
     * @Description //TODO 提取拥堵路段信息
     * @Date 2019/4/19
     * @Param 子行程模型数据点
     */
    public static List<List<ModelDataPoint>> extractCongestionSectionInformation(List<ModelDataPoint> list) {
        List<ModelDataPoint> modelDataPoints = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPreSpeed() < 3) {
                modelDataPoints.add(list.get(i));
            }
        }

        /*提取出来车辆行驶拥堵路段的数据点*/
        int flag = 0;
        List<List<ModelDataPoint>> congestionSectionInformationModeDataPoints = new ArrayList<>();
        for (int i = 0; i < modelDataPoints.size() - 1; i++) {
            double timeBetweenTwoPoints = modelDataPoints.get(i).getBaseDataPoint().getTimeBetweenTwoPoints(modelDataPoints.get(i + 1).getBaseDataPoint());
            if (timeBetweenTwoPoints > 10) {
                List<ModelDataPoint> dataPoints = new ArrayList<>(16);
                for (int j = flag; j <= i; j++) {
                    dataPoints.add(modelDataPoints.get(j));
                }
                congestionSectionInformationModeDataPoints.add(dataPoints);
                flag = i + 1;
            }
        }

        /*对部提取出来具有拥堵特征的数据但是不合法的进行过滤*/
        // 根据模型数据点的个数来过滤
        for (int i = 0; i < congestionSectionInformationModeDataPoints.size(); i++) {
            if (congestionSectionInformationModeDataPoints.get(i).size() < 16) {
                congestionSectionInformationModeDataPoints.remove(i);
                i--;
            }
        }

        // 排除可能是在红路灯路口，等绿灯是数据
        for (int i = 0; i < congestionSectionInformationModeDataPoints.size(); i++) {
            List<ModelDataPoint> modelDataPoint = congestionSectionInformationModeDataPoints.get(i);
            ArrayList<Double> doubleSpeeds = new ArrayList<>();

            for (int j = 0; j < modelDataPoint.size(); j++) {
                Double speed = modelDataPoint.get(j).getPreSpeed();
                doubleSpeeds.add(speed);
            }

            DoubleSummaryStatistics speeds =doubleSpeeds.stream().mapToDouble(x -> x).summaryStatistics();
            if (speeds.getAverage() < 0.2) {
                congestionSectionInformationModeDataPoints.remove(i);
                i--;
            }
        }
        return congestionSectionInformationModeDataPoints;
    }
    
    /** 
      * @Author fengjl
      * @Description //TODO 提取车辆在道路上停下来这个状态，这是被撞或者追尾的一个公共特征
      * @Date  2019/4/22
      * @Param 子行程模型数据点
      * @return 车辆在道路上的停车状态
      */
    public static List<List<ModelDataPoint>> pickUpStop(List<ModelDataPoint> list) {
        /*对于路段中的停车状态数据进行切断*/
        List<ModelDataPoint> modelDataPoints = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            // 综合所有的数据提取出来的一个系数，后面肯定要通过数据训练得到
            if (list.get(i).getPreSpeed() < 0.5) {
                modelDataPoints.add(list.get(i));
            }
        }

        /*对停车状态的模型数据点进行提取*/
        int flag = 0;
        List<List<ModelDataPoint>> parkingStateModelDataPoints = new ArrayList<>();
        for (int i = 0; i < modelDataPoints.size() - 1; i++) {
            double timeBetweenTwoPoints = modelDataPoints.get(i).getBaseDataPoint().getTimeBetweenTwoPoints(modelDataPoints.get(i + 1).getBaseDataPoint());
            if (timeBetweenTwoPoints > 10) {
                ArrayList<ModelDataPoint> dataPoints = new ArrayList<>(16);
                for (int j = flag; j <= i; j++) {
                    dataPoints.add(modelDataPoints.get(j));
                }
                parkingStateModelDataPoints.add(dataPoints);
                flag = i + 1;
            }
        }

       /*对时间点过短停车数据进行过滤*/
        for (int i = 0; i < parkingStateModelDataPoints.size(); i++) {
            // 等这个项目上线以后，5这个系数肯定也是通过训练之后才得到的
            if (parkingStateModelDataPoints.get(i).size() < 5) {
                parkingStateModelDataPoints.remove(i);
                i--;
            }
        }
        return parkingStateModelDataPoints;
    }

    /**
      * @Author fengjl
      * @Description //TODO 对停车下来的，前10个模型数据点，大概是30秒的钟的行程进行单独分析,同时返回碰撞严重的等级
      * @Date  2019/4/24
      * @Param 提取点前10个模型数据点
      * @return 分析的结果
      */
    public static HashMap<String, Object> analysisCollision(List<ModelDataPoint> list, double stopTime) {
        // 这个加速度阈值很重要
        Double  acceleratedMark = Configuration.configration.getDouble(DataConstants.ACCELERATION_MARK);
        List<Double> acceleratedSpeeds = new ArrayList<>();
        List<Double> speeds = new ArrayList<>();
        List<Object> track = new ArrayList<>();
        Long startTime = list.get(0).getBaseDataPoint().getTime();
        for (int i = 0 ; i < list.size(); i ++) {
            Double acceleratedSpeed = list.get(i).getAcceleratedSpeed();
            Double speed = list.get(i).getPreSpeed();
            acceleratedSpeeds.add(acceleratedSpeed);
            speeds.add(speed);
            ArrayList<Double> doubles = new ArrayList<>();
            doubles.add(list.get(i).getBaseDataPoint().getLng());
            doubles.add(list.get(i).getBaseDataPoint().getLat());
            track.add(doubles);
        }

        DoubleSummaryStatistics speedsStats = speeds.stream().mapToDouble(x -> x).summaryStatistics();
        DoubleSummaryStatistics accelerateStats = acceleratedSpeeds.stream().mapToDouble(x -> x).summaryStatistics();
        double minAccelea = accelerateStats.getMin();
        if (minAccelea <= acceleratedMark) {
            double speed = 0;
             for (int j = 0; j < acceleratedSpeeds.size(); j ++) {
                 if (acceleratedSpeeds.get(j) == minAccelea) {
                     speed = speeds.get(j);
                 }
             }
             /*判断碰撞的等级*/
            Integer integer = DataUtil.analysisLevel(speed,stopTime);
            double maxAccela = accelerateStats.getMax();
            double maxSpeed = speedsStats.getMax();
            double minSpeed = speedsStats.getMin();
            double avrageSpped = speedsStats.getAverage();
            HashMap<String, Object> reuslt = new HashMap<>();
            reuslt.put(DataConstants.LEAVEL,integer);
            reuslt.put(DataConstants.ACCELERATION_MARK,minAccelea);
            reuslt.put(DataConstants.MAX_SPEED,maxSpeed * 3.6);
            reuslt.put(DataConstants.AVERAGE_SPEED,avrageSpped * 3.6);
            reuslt.put(DataConstants.TRACK,track);
            reuslt.put(DataConstants.START_TIME,startTime);
            return reuslt;
        } else {
            HashMap<String, Object> reuslt = new HashMap<>();
            return reuslt;
        }
    }

}