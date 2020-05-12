package com.fengjunlin.accident.prediction.model.web.utils;

import com.alibaba.fastjson.JSONObject;
import com.fengjunlin.accident.prediction.model.web.tools.constants.DataConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 数据处理工具类
 * @Author fengjl
 * @Date 2019/4/24 11:19
 * @Version 1.0
 **/
public class DataUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
    /**
     * 根据速度来判断等级,后续要根据车的重量，停车的时间，天气，路面阻尼系数综合给出一个系数
     *
     * @param speed 速度
     * @param stopTime
     * @return 等级
     */
    public static Integer analysisLevel(double speed,double stopTime) {
        JSONObject jsonObject = Configuration.configration.getJSONObject(DataConstants.LEAVEL);
        // 轻微
        JSONObject jsonSligth = jsonObject.getJSONObject(DataConstants.SLIGHT);
        double minSlight = jsonSligth.getDouble(DataConstants.MIN);
        double maxSlight = jsonSligth.getDouble(DataConstants.MAX);
        // 中度
        JSONObject jsonModerate = jsonObject.getJSONObject(DataConstants.MODERATE);
        double minModerate = jsonModerate.getDouble(DataConstants.MIN);
        double maxModerate = jsonModerate.getDouble(DataConstants.MAX);
        // 严重
        JSONObject jsonSeverity = jsonObject.getJSONObject(DataConstants.SEVERITY);
        double minSeverity = jsonSeverity.getDouble(DataConstants.MIN);
        double maxSeverity = jsonSeverity.getDouble(DataConstants.MAX);

        if (minSlight<= speed && speed < maxSlight) {
            return 1;
        } else if (minModerate<= speed && speed < maxModerate) {
            return 2;
        } else {
            return 3;
        }
    }

    public static void main(String[] args) {
        Integer integer = DataUtil.analysisLevel(1.0,0);
        System.out.println(integer);
    }

}
