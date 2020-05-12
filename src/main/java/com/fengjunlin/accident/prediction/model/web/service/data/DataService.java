package com.fengjunlin.accident.prediction.model.web.service.data;
import com.fengjunlin.accident.prediction.model.web.model.drivingmodel.BaseDataPoint;
import com.fengjunlin.accident.prediction.model.web.model.drivingmodel.ModelDataPoint;
import java.util.List;
import java.util.Map;

/**
 * @Description 模型数据定位的service层
 * @Author fengjl
 * @Date 2019/6/20 10:05
 * @Version 1.0
 **/
public interface DataService {

    /**
     * 对传递过来的行程数据的畅行信息进行算法提取
     *
     * @param list 加工之后的基础数据点
     * @return 提取之后的结果信息
     */
    List<Object> loadFreeGoData(List<BaseDataPoint> list);

    /**
     * 对传递过来的行程数据的拥堵信息进行算法提取
     *
     * @param list 加工之后的基础数据点
     * @return 提取之后的结果数据
     */
    List<Object> loadCongestionInformation(List<BaseDataPoint> list);

    /**
     * 对传递过来的含有异常加速度的数据点，进行提取
     *
     * @param list 加工之后的基础数据
     * @return 提取之后的结果数据
     */
    List<ModelDataPoint> loadExceptionalPoint(List<BaseDataPoint> list);

    /**
     * 对传递过来的行程的综合数据进行提取
     *  提取行驶过程中的，最高的速度，行驶时间长，时间范围，平均速度，经过路口数，不良加速次数，不良急减次数,下车次数（可能去学校，或者商场，或者其他地方，反正是停车了），停车办事儿时间长度
     *
     * @param list 加工之后的基础数据
     * @return
     */
    Map<String,Object> loadExtractIndexInformation(List<BaseDataPoint> list);

    /**
     * 提取行程中可能出现的碰撞信息
     *
     * @param list 经过处理之后模型数据点
     * @return 返回经过模型提取之后的可能出现的碰撞信息
     */
    List<Object> loadCollisionFeatureExtraction(List<BaseDataPoint> list);


    /**
     *对行程中的轨迹数据进行提取
     *
     * @param list 经过处理之后的模型数据点
     * @return 返回经过模型处理之后的轨迹数据
     */
    Map<Integer,List<List<Double>>> loadTrackData(List<BaseDataPoint> list);



}
