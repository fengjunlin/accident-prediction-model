package com.fengjunlin.accident.prediction.model.web.data;
import com.alibaba.fastjson.JSONObject;
import com.fengjunlin.accident.prediction.model.web.model.drivingmodel.BaseDataPoint;
import com.fengjunlin.accident.prediction.model.web.model.drivingmodel.BaseDataPointWithSpeed;
import com.fengjunlin.accident.prediction.model.web.model.drivingmodel.ModelDataPoint;
import com.fengjunlin.accident.prediction.model.web.model.drivingmodel.ModelDataPointWithSpeed;
import com.fengjunlin.accident.prediction.model.web.tools.DataHander;
import com.fengjunlin.accident.prediction.model.web.tools.constants.DataConstants;
import com.fengjunlin.accident.prediction.model.web.tools.constants.HbaseQueryConstants;
import com.fengjunlin.accident.prediction.model.web.utils.Configuration;
import com.fengjunlin.accident.prediction.model.web.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * @Description 事故判定数据模型
 * @Author fengjl
 * @Date 2019/4/17 13:53
 * @Version 1.0
 **/
@Component
public class  DecisionDataModel {
    private static final Logger logger = LoggerFactory.getLogger(DecisionDataModel.class);

    /**
      * @Author fengjl
      * @Description //TODO 对处理之后的模型数据点，对于速度急减或者急加可能事故点提取
      * @Date  2019/4/17
      * @return 经过判定模型之后，返回的是预测的事故数据
      */
    public  List<ModelDataPoint> extractionExtremeAcceleration (List<List<ModelDataPoint>> multipleTravelmodeDataPoints) {
        /*加载配置文件系数*/
        JSONObject coefficient = Configuration.configration.getJSONObject(DataConstants.EXTREME_ACCELERATION);
        Double maxCoefficient = coefficient.getDouble(DataConstants.GREATEST_COEFFICIENT);
        Double minCoefficient = coefficient.getDouble(DataConstants.MINIMUM_COEFFICIENT);
        ArrayList<ModelDataPoint> accidentPredictionAccidentPoints = new ArrayList<>(16);
        for (int i = 0; i < multipleTravelmodeDataPoints.size(); i++) {
            List<ModelDataPoint> modelDataPoints = multipleTravelmodeDataPoints.get(i);
            for (int j = 0; j < modelDataPoints.size(); j++) {
                ModelDataPoint modelDataPoint = modelDataPoints.get(j);
                if (modelDataPoint.getAcceleratedSpeed() >= maxCoefficient || modelDataPoint.getAcceleratedSpeed() <= minCoefficient) {
                    accidentPredictionAccidentPoints.add(modelDataPoint);
                }
            }
        }
        return accidentPredictionAccidentPoints;
    }

    /**
      * @Author fengjl
      * @Description //TODO 对处理之后的模型数据点,撞车事故特征进行提取
      * @Date  2019/4/17
      * @return
      */
    @SuppressWarnings("all")
    public List<Object> collisionFeatureExtraction( List<List<ModelDataPoint>> multipleTravelmodeDataPoints) {
           List<Object> resutData = new ArrayList<>();
        for (int i = 0; i < multipleTravelmodeDataPoints.size(); i++) {
            List<List<ModelDataPoint>> stopStateModelDataPoints = DataHander.pickUpStop(multipleTravelmodeDataPoints.get(i));
            List<ModelDataPoint> allModelDataPoints = multipleTravelmodeDataPoints.get(i);
            for (int j = 0 ; j < stopStateModelDataPoints.size(); j++) {
                /*这层循环的是，停车状态的数据*/
                List<ModelDataPoint> modelDataPoints = stopStateModelDataPoints.get(j);
                // 停留时间长度,目前不考虑停留时间长度。
                double stopTime =  modelDataPoints.get(0).getBaseDataPoint().getTimeBetweenTwoPoints(modelDataPoints.get(modelDataPoints.size()-1).getBaseDataPoint());
                long endTime = modelDataPoints.get(modelDataPoints.size()-1).getBaseDataPoint().getTime();
                // 判断和下一个停车状态模型数据点之间的时间距离
                if (j >0) {
                    Integer markTwo2 = modelDataPoints.get(0).getMarkTwo();
                    Integer markTwo1 = stopStateModelDataPoints.get(j - 1).get(stopStateModelDataPoints.get(j - 1).size() - 1).getMarkTwo();

                    if ((markTwo2-markTwo1) > 10) {
                        List<ModelDataPoint> modelDataPointsStop = new ArrayList<>();
                        for (int w = markTwo2-10; w < markTwo2; w++) {
                            ModelDataPoint modelDataPoint = allModelDataPoints.get(w);
                            modelDataPointsStop.add(modelDataPoint);
                        }
                        HashMap<String, Object> result = DataHander.analysisCollision(modelDataPointsStop,stopTime);
                        if (result.size() != 0) {
                            result.put(DataConstants.END_TIME,endTime);
                            result.put(DataConstants.STAY_TIME,stopTime);
                            resutData.add(result);
                        }
                    } else {
                        List<ModelDataPoint> modelDataPointsStop = new ArrayList<>();
                        for (int w = markTwo1; w < markTwo2; w++) {
                            ModelDataPoint modelDataPoint = allModelDataPoints.get(w);
                            modelDataPointsStop.add(modelDataPoint);
                        }
                        HashMap<String, Object> result = DataHander.analysisCollision(modelDataPointsStop,stopTime);
                        if (result.size() != 0) {
                            result.put(DataConstants.END_TIME,endTime);
                            result.put(DataConstants.STAY_TIME,stopTime);
                            resutData.add(result);
                        }
                    }

                }else {
                    Integer markTwo = modelDataPoints.get(0).getMarkTwo();
                    if (markTwo > 10) {
                       List<ModelDataPoint> modelDataPointsStop = new ArrayList<>();
                        for (int w = markTwo-10; w < markTwo; w++) {
                            ModelDataPoint modelDataPoint = allModelDataPoints.get(w);
                            modelDataPointsStop.add(modelDataPoint);
                        }
                        HashMap<String, Object> result = DataHander.analysisCollision(modelDataPointsStop,stopTime);
                        if (result.size() != 0) {
                            result.put(DataConstants.END_TIME,endTime);
                            result.put(DataConstants.STAY_TIME,stopTime);
                            resutData.add(result);
                        }

                    } else {
                        ArrayList<ModelDataPoint> modelDataPointsStop = new ArrayList<>();
                        for (int w = 0; w < markTwo; w++) {
                            ModelDataPoint modelDataPoint = allModelDataPoints.get(w);
                            modelDataPointsStop.add(modelDataPoint);
                        }
                        HashMap<String, Object> result = DataHander.analysisCollision(modelDataPointsStop,stopTime);
                        if (result.size() != 0) {
                            result.put(DataConstants.END_TIME,endTime);
                            result.put(DataConstants.STAY_TIME,stopTime);
                            resutData.add(result);
                        }
                    }
                }

            }
        }
        return resutData;
    }

    /**
      * @Author fengjl
      * @Description //TODO 对处理之后的模型数据点,对严重拥堵路段信息进行提取
      * @Date  2019/4/17
      * @return 严重拥堵路段轨迹，拥堵时间长，严重拥堵时间范围
      */
    @SuppressWarnings("all")
    public List<Object> congestionInformationExtraction(List<List<ModelDataPoint>> multipleTravelmodeDataPoints) {
        /*获取拥堵路段*/
        List<List<List<ModelDataPoint>>> multipleCongestedRoad = new ArrayList<>();
        for (int i = 0; i < multipleTravelmodeDataPoints.size(); i++) {
            List<ModelDataPoint> modelDataPoints = multipleTravelmodeDataPoints.get(i);
            List<List<ModelDataPoint>> congestionInformationInformationList = DataHander.extractCongestionSectionInformation(modelDataPoints);
            multipleCongestedRoad.add(congestionInformationInformationList);
        }
        /*提取拥堵路段信息*/
        List<Object> information = new ArrayList<>();
        int lab = 0;
        for (int i = 0; i < multipleCongestedRoad.size(); i++) {
            for ( int j = 0; j < multipleCongestedRoad.get(i).size(); j++) {
                List<ModelDataPoint> modelDataPoints = multipleCongestedRoad.get(i).get(j);
                List<Object> track = new ArrayList<>();
                List<Double> roadLength = new ArrayList<>();
                // 拥堵路段数
                lab++;
                // 每个拥堵路段的开始时间
                long startTime = modelDataPoints.get(0).getBaseDataPoint().getTime();
                // 每个拥堵路段的结束时间
                long endTime = modelDataPoints.get(modelDataPoints.size()-1).getBaseDataPoint().getTime();
                // 每个拥堵路段的时长
                double speedBetweenTwoPoints = modelDataPoints.get(0).getBaseDataPoint().getTimeBetweenTwoPoints(modelDataPoints.get(modelDataPoints.size() - 1).getBaseDataPoint());
                List<Double> speeds = new ArrayList<>();
                for (int w = 0 ; w < multipleCongestedRoad.get(i).get(j).size()-1; w++) {
                    ArrayList<Double> locationPoint = new ArrayList<>();
                    locationPoint.add(multipleCongestedRoad.get(i).get(j).get(w).getBaseDataPoint().getLng());
                    locationPoint.add(multipleCongestedRoad.get(i).get(j).get(w).getBaseDataPoint().getLat());
                    track.add(locationPoint);
                    roadLength.add(multipleCongestedRoad.get(i).get(j).get(w).getBaseDataPoint().getDistanceBetweentTwoPoints(modelDataPoints.get(w + 1).getBaseDataPoint()));
                    Double speed = multipleCongestedRoad.get(i).get(j).get(w).getPreSpeed();
                    speeds.add(speed);
                }
                DoubleSummaryStatistics stats = speeds.stream().mapToDouble(x -> x).summaryStatistics();
                DoubleSummaryStatistics distance = roadLength.stream().mapToDouble(x -> x).summaryStatistics();
                // 每个拥堵路段的最高速度
                double maxSpeed = stats.getMax()*3.6;
                // 每个拥堵路段的最低速度
                double minSpeed = stats.getMin()*3.6;
                // 每个拥堵路段的平均速度
                double averageSpeed = stats.getAverage()*3.6;
                // 每个拥堵路段的行程
                double sectionLength = distance.getSum();
                HashMap<String, Object> drivingInformation = new HashMap<>();
                drivingInformation.put(DataConstants.START_TIME,startTime);
                drivingInformation.put(DataConstants.END_TIME,endTime);
                drivingInformation.put(DataConstants.TIME_SPAN,(double) Math.round(speedBetweenTwoPoints * 100) / 100);
                drivingInformation.put(DataConstants.MAX_SPEED,(double) Math.round(maxSpeed * 100) / 100);
                drivingInformation.put(DataConstants.MIN_SPEED,(double) Math.round(minSpeed * 100) / 100);
                drivingInformation.put(DataConstants.AVERAGE_SPEED,(double) Math.round(averageSpeed * 100) / 100);
                drivingInformation.put(DataConstants.TRACK,track);
                drivingInformation.put(DataConstants.SECTION_LENGTH,sectionLength);
                information.add(drivingInformation);
            }
        }
        // 拥堵次数
        HashMap<String, Object> passImpedTimes = new HashMap<>();
        passImpedTimes.put(DataConstants.NUMBER_OF_CONGESTION,lab);
        information.add(passImpedTimes);
        return information;
    }

    /**
      * @Author fengjl
      * @Description //TODO 对处理之后的模型数据点,对畅通行驶路段信息进行提取
      * @Date  2019/4/17
      * @Param
      * @return 轨迹数据，行驶时长，行驶时间范围：几点几分到几点几分
      */
    @SuppressWarnings("all")
    public List<Object> unimpededDrivingSectionInformationExtraction( List<List<ModelDataPoint>> multipleTravelmodeDataPoints) {
        /*获取加速畅行路段*/
        List<List<List<ModelDataPoint>>> multipleSmoothInformation = new ArrayList<>();
        for (int i = 0; i < multipleTravelmodeDataPoints.size(); i++) {
            List<ModelDataPoint> modelDataPoints = multipleTravelmodeDataPoints.get(i);
            List<List<ModelDataPoint>> smoothInformationList = DataHander.extractSmoothInformation(modelDataPoints);
            multipleSmoothInformation.add(smoothInformationList);
        }
        /*畅行路段信息提取*/
        List<Object> information = new ArrayList<>();
        int lab = 0;
        for (int i = 0; i < multipleSmoothInformation.size(); i++) {
            for ( int j = 0; j < multipleSmoothInformation.get(i).size(); j++) {
                List<ModelDataPoint> modelDataPoints = multipleSmoothInformation.get(i).get(j);
                List<Object> track = new ArrayList<>();
                List<Double> roadLength = new ArrayList<>();
                // 畅行路段数
                lab++;
                //  每个畅行路段的开始时间
                long startTime = modelDataPoints.get(0).getBaseDataPoint().getTime();
                // 每个畅行路段的结束时间
                long endTime = modelDataPoints.get(modelDataPoints.size()-1).getBaseDataPoint().getTime();
                // 每个畅行路段的时长
                double speedBetweenTwoPoints = modelDataPoints.get(0).getBaseDataPoint().getTimeBetweenTwoPoints(modelDataPoints.get(modelDataPoints.size() - 1).getBaseDataPoint());
                List<Double> speeds = new ArrayList<>();
                for (int w = 0 ; w < multipleSmoothInformation.get(i).get(j).size() -1; w++) {
                   ArrayList<Double> locationPoint = new ArrayList<>();
                   locationPoint.add(multipleSmoothInformation.get(i).get(j).get(w).getBaseDataPoint().getLng());
                   locationPoint.add(multipleSmoothInformation.get(i).get(j).get(w).getBaseDataPoint().getLat());
                   track.add(locationPoint);
                   roadLength.add(multipleSmoothInformation.get(i).get(j).get(w).getBaseDataPoint().getDistanceBetweentTwoPoints(modelDataPoints.get(w+1).getBaseDataPoint()));
                   Double speed = multipleSmoothInformation.get(i).get(j).get(w).getPreSpeed();
                   speeds.add(speed);
               }
                DoubleSummaryStatistics stats = speeds.stream().mapToDouble(x -> x).summaryStatistics();
                DoubleSummaryStatistics distance = roadLength.stream().mapToDouble(x -> x).summaryStatistics();
                // 每个畅行路段的最高速度
                double maxSpeed = stats.getMax()*3.6;
                // 每个畅行路段的最低速度
                double minSpeed = stats.getMin()*3.6;
                // 每个畅行路段的平均速度
                double averageSpeed = stats.getAverage()*3.6;
                // 每个畅行路段的行程
                double sectionLength = distance.getSum();
                HashMap<String, Object> drivingInformation = new HashMap<>();
                drivingInformation.put(DataConstants.START_TIME,startTime);
                drivingInformation.put(DataConstants.END_TIME,endTime);
                drivingInformation.put(DataConstants.TIME_SPAN,(double) Math.round(speedBetweenTwoPoints * 100) / 100);
                drivingInformation.put(DataConstants.MAX_SPEED,(double) Math.round(maxSpeed * 100) / 100);
                drivingInformation.put(DataConstants.MIN_SPEED,(double) Math.round(minSpeed * 100) / 100);
                drivingInformation.put(DataConstants.AVERAGE_SPEED,(double) Math.round(averageSpeed * 100) / 100);
                drivingInformation.put(DataConstants.TRACK,track);
                drivingInformation.put(DataConstants.SECTION_LENGTH,sectionLength);
                information.add(drivingInformation);
            }
        }
        // 畅行次数
        HashMap<String, Object> passImpedTimes = new HashMap<>();
        passImpedTimes.put(DataConstants.PASS_IMPED_TIMES,lab);
        information.add(passImpedTimes);
        return information;
    }

    /**
     *  提取行驶过程中的，最高的速度，行驶时间长，时间范围，平均速度，经过路口数，不良加速次数，不良急减次数,下车次数（可能去学校，或者商场，或者其他地方，反正是停车了），停车办事儿时间长度，
     *  可以根据每个行程的开始时间去查poi来判断是在那个商场。
     */
    public Map<String, Object> extractIndexInformation( List<List<ModelDataPoint>> multipleTravelmodeDataPoints) {
        List<Double> speeds = new ArrayList<>();
        List<Double> accidentPrediction = new ArrayList<>();
        List<Double> timeLong = new ArrayList<>();
        for (int i = 0 ; i < multipleTravelmodeDataPoints.size(); i ++) {
            List<ModelDataPoint> modelDataPoints = multipleTravelmodeDataPoints.get(i);
            for (int j = 0 ; j < modelDataPoints.size(); j++) {
                Double speed = modelDataPoints.get(j).getPreSpeed();
                Double acceleratedSpeed = modelDataPoints.get(j).getAcceleratedSpeed();
                accidentPrediction.add(acceleratedSpeed);
                speeds.add(speed);
                timeLong.add(modelDataPoints.get(j).getLongTime());
            }
        }
        /*提取每个子行程之间的停车时长和停车的开始时间和结束时间*/
        List<Object> objects = new ArrayList<>();
        for (int i = 1; i< multipleTravelmodeDataPoints.size(); i++) {
            List<ModelDataPoint> modelDataPoints1 = multipleTravelmodeDataPoints.get(i);
            List<ModelDataPoint> modelDataPoints = multipleTravelmodeDataPoints.get(i - 1);
            Long startTime = modelDataPoints.get(modelDataPoints.size() - 1).getBaseDataPoint().getTime();
            Long endTime = modelDataPoints1.get(0).getBaseDataPoint().getTime();
            double timeBetweenTwoPoints = modelDataPoints.get(modelDataPoints.size() - 1).getBaseDataPoint().getTimeBetweenTwoPoints(modelDataPoints1.get(0).getBaseDataPoint());
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put(DataConstants.START_TIME,startTime);
            resultMap.put(DataConstants.END_TIME,endTime);
            resultMap.put(DataConstants.TIME_SPAN,timeBetweenTwoPoints);
            List<Object> locations = new ArrayList<>();
            for ( int w = 0; w < 5; w++) {
                ArrayList<Double> doubles = new ArrayList<Double>();
                double lng = modelDataPoints1.get(w).getBaseDataPoint().getLng();
                double lat = modelDataPoints1.get(w).getBaseDataPoint().getLat();
                doubles.add(lng);
                doubles.add(lat);
                locations.add(doubles);
            }
            resultMap.put(DataConstants.LOCATION,locations);
            objects.add(resultMap);
        }

        int size = multipleTravelmodeDataPoints.size();
        List<ModelDataPoint> modelDataPoints = multipleTravelmodeDataPoints.get(size - 1);
        // 总行程时长
        double totalTravelTime = multipleTravelmodeDataPoints.get(0).get(0).getBaseDataPoint().getTimeBetweenTwoPoints(modelDataPoints.get(modelDataPoints.size() - 1).getBaseDataPoint());
        // 整体行程的开始时间和结束时间
        long startTime = multipleTravelmodeDataPoints.get(0).get(0).getBaseDataPoint().getTime();
        long endTime = modelDataPoints.get(modelDataPoints.size() - 1).getBaseDataPoint().getTime();
        int outCarNumber = multipleTravelmodeDataPoints.size()-1;

        /*提取不良加速次数和不良减速次数*/
        int rapidlyMark = 0;
        int sharpSlowdown = 0 ;
        for (int i = 0; i < accidentPrediction.size(); i ++) {
            if (accidentPrediction.get(i) > 0.5) {
                rapidlyMark ++;
            }
            if (accidentPrediction.get(i) < -0.5) {
                sharpSlowdown++;
            }
        }

        DoubleSummaryStatistics stats = speeds.stream().mapToDouble(x -> x).summaryStatistics();
        DoubleSummaryStatistics timeLongState = timeLong.stream().mapToDouble(x -> x).summaryStatistics();
        // 整个行程中的最高ll
        // 速度
        double maxSpeed = stats.getMax()*3.6;
        // 整个行程中平均速度
        double averageSpeed = stats.getAverage()*3.6;
        // 驾驶时长
        double travelTime = timeLongState.getSum();
        // 下车办事总时间
        double workTime = totalTravelTime -travelTime;
        Map<String, Object> resultData = new HashMap<>(16);
        resultData.put(DataConstants.RAPIDLY_TIME,rapidlyMark);
        resultData.put(DataConstants.SLOWDOWN_TIME,sharpSlowdown);
        resultData.put(DataConstants.MAX_SPEED,(double) Math.round(maxSpeed * 100) / 100);
        resultData.put(DataConstants.AVERAGE_SPEED,(double) Math.round(averageSpeed * 100) / 100);
        resultData.put(DataConstants.DRIVE_TRAVEL_TIME,travelTime);
        resultData.put(DataConstants.TATAL_TRAVEL_TIME, totalTravelTime);
        resultData.put(DataConstants.START_TIME,startTime);
        resultData.put(DataConstants.END_TIME,endTime);
        resultData.put(DataConstants.OUT_OF_CAR_NUMBER,outCarNumber);
        resultData.put(DataConstants.DO_WORK_TIME,workTime);
        resultData.put(DataConstants.DO_WORK_INFORMATION,objects);
        return resultData;
    }

    /**
     * 对车辆的行程轨迹，加速度，速度，进行计算提取
     *
     * @param list 模型的基础数据点
     * @return 返回提取之后的结果数据
     */
    @SuppressWarnings("all")
    public Map<String,Object> loadCarTrackData( List<List<BaseDataPoint>> lists) {
        Map<String,Object> resultMap = new HashMap<>(16);
        List<List<ModelDataPoint>> modelDataPointLists = new ArrayList<>(16);
        for (int w = 0; w < lists.size(); w++) {
            List<BaseDataPoint> list = lists.get(w);
            List<ModelDataPoint> modelDataPoints = new ArrayList<>(16);
            for (int i = 0; i < list.size() - 2; i++) {
                ModelDataPoint modeDataPoint = list.get(i).getModeDataPoint(list.get(i + 1));
                ModelDataPoint nextModeDataPoint = list.get(i + 1).getModeDataPoint(list.get(i + 2));
                modeDataPoint.setLongTime(modeDataPoint.getLongTime() + nextModeDataPoint.getLongTime());
                modeDataPoint.setNextSpeed(nextModeDataPoint.getPreSpeed());
                modeDataPoint.setAcceleratedSpeed((nextModeDataPoint.getPreSpeed() - modeDataPoint.getPreSpeed()) / (modeDataPoint.getLongTime() + nextModeDataPoint.getLongTime()));
                modelDataPoints.add(modeDataPoint);
            }
            modelDataPointLists.add(modelDataPoints);
        }

        List<Map<String,Object>> track = new ArrayList<>(16);
        List<Map<String,Object>> acceleratedSpeedsLists = new ArrayList<>(16);
        for (int k = 0; k < modelDataPointLists.size(); k++) {
            List<ModelDataPoint> modelDataPoints = modelDataPointLists.get(k);
            for (int j = 0; j < modelDataPoints.size(); j++) {
                ModelDataPoint modelDataPoint = modelDataPoints.get(j);
                Long time = modelDataPoint.getBaseDataPoint().getTime();
                String timeFormat = DateUtils.timeFormat(time);
                Double speed = (double) Math.round(modelDataPoint.getPreSpeed() * 100) / 100;
                double acceleratedSpeed = (double) Math.round(modelDataPoint.getAcceleratedSpeed() * 100) / 100;
                Map<String,Object> acceleratedSpeedMap = new HashMap<>(16);
                acceleratedSpeedMap.put("acceleratedSpeed",acceleratedSpeed);
                acceleratedSpeedMap.put("time",timeFormat);
                Map<String,Object> lngAndLat = new HashMap<>(16);
                double lng = modelDataPoint.getBaseDataPoint().getLng();
                double lat = modelDataPoint.getBaseDataPoint().getLat();
                lngAndLat.put("lng",lng);
                lngAndLat.put("lat",lat);
                lngAndLat.put("time",timeFormat);
                lngAndLat.put("speed",speed);
                track.add(lngAndLat);
                acceleratedSpeedsLists.add(acceleratedSpeedMap);
            }
        }

        resultMap.put(HbaseQueryConstants.TARCK,track);
        resultMap.put(HbaseQueryConstants.ACCELERATED_SPEEDS,acceleratedSpeedsLists);
        return resultMap;
    }

    /**
     * 对车辆的行程轨迹，加速度，速度，进行计算提取
     * 这种方式通过设备传入的经纬度进行计算
     *
     * @param list 模型的基础数据点
     * @return 返回提取之后的结果数据
     */
    @SuppressWarnings("all")
    public Map<String,Object> loadCarTrackDataWithSpeed( List<List<BaseDataPointWithSpeed>> lists) {
        Map<String,Object> resultMap = new HashMap<>(16);
        List<List<ModelDataPointWithSpeed>> modelDataPointLists = new ArrayList<>(16);
        for (int w = 0; w < lists.size(); w++) {
            List<BaseDataPointWithSpeed> list = lists.get(w);
            List<ModelDataPointWithSpeed> modelDataPoints = new ArrayList<>(16);
            for (int i = 0; i < list.size() - 1; i++) {
                ModelDataPointWithSpeed modeDataPoint = list.get(i).getModelDataPointWithSpeed(list.get(i + 1));
                modelDataPoints.add(modeDataPoint);
            }
            modelDataPointLists.add(modelDataPoints);
        }

        List<Map<String,Object>> track = new ArrayList<>(16);
        List<Map<String,Object>> acceleratedSpeedsLists = new ArrayList<>(16);
        for (int k = 0; k < modelDataPointLists.size(); k++) {
            List<ModelDataPointWithSpeed> modelDataPoints = modelDataPointLists.get(k);
            for (int j = 0; j < modelDataPoints.size(); j++) {
                try {
                    ModelDataPointWithSpeed modelDataPoint = modelDataPoints.get(j);
                    Long time = modelDataPoint.getBaseDataPointWithSpeed().getTime();
                    String timeFormat = DateUtils.timeFormat(time);
                    Double speed = (double) Math.round(modelDataPoint.getPreSpeed() * 100) / 100;
                    double acceleratedSpeed = (double) Math.round(modelDataPoint.getAcceleratedSpeed() * 100) / 100;
                    Map<String,Object> acceleratedSpeedMap = new HashMap<>(16);
                    acceleratedSpeedMap.put("acceleratedSpeed",acceleratedSpeed);
                    acceleratedSpeedMap.put("time",timeFormat);
                    Map<String,Object> lngAndLat = new HashMap<>(16);
                    double lng = modelDataPoint.getBaseDataPointWithSpeed().getLng();
                    double lat = modelDataPoint.getBaseDataPointWithSpeed().getLat();
                    lngAndLat.put("lng",lng);
                    lngAndLat.put("lat",lat);
                    lngAndLat.put("time",timeFormat);
                    lngAndLat.put("speed",speed);
                    track.add(lngAndLat);
                    acceleratedSpeedsLists.add(acceleratedSpeedMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        resultMap.put(HbaseQueryConstants.TARCK,track);
        resultMap.put(HbaseQueryConstants.ACCELERATED_SPEEDS,acceleratedSpeedsLists);
        return resultMap;
    }


}
