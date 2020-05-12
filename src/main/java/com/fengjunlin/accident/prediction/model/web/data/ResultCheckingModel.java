package com.fengjunlin.accident.prediction.model.web.data;
/**
 * @Description 结果校验模型
 * @Author fengjl
 * @Date 2019/4/17 17:49
 * @Version 1.0
 * 结果校验模型思路
 * 结果校验模型主要是针对部分设备在停车之后还在一直发数据这种情况，或者本来已经停下了，还在发数据。
 * 提取出来之后的数据，我们会，会根据poi或者的围栏坐标来判断是否在这个围栏中。
 * 主要算法就是点在某个多边形中
 * 1、在商场中停下来，这样情况，目前可以根据停车前的加速度和速度来去除
 * 2、在道路上停下来，可以根据停车的时候和停车前的速度和加速度来过滤
 *
 **/
public class ResultCheckingModel {

}
