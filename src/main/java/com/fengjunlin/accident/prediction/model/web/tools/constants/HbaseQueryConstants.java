package com.fengjunlin.accident.prediction.model.web.tools.constants;

/**
 * Hbase 查询相关常量
 */
public class HbaseQueryConstants {

	/**
	 * 歌途云镜表名 
	 */
	public static final String GT_YUNJING_TABLE_NAME = "gtInfo";
	/**
	 * 歌途云镜基础数据列族名
	 */
	public static final String GT_LOCATION_FAMILY_NAME = "basic";
	
	/**
	 * 歌途云镜rowkey前缀
	 */
	public static final String GT_YUNJING_ROW_KEY_PREFIX = "gt_";

	/**h
	 * GT808 表名
	 */
	public static final String GT808_TABLE_NAME = "device_jt808_info";
	/**
	 * GPS 定位数据列簇
	 */
	public static final String GT808_GPS_FAMILY_NAME = "location_info_up";
	/**
	 * GPS rowkey 前缀
	 */
	public static final String GT808_ROW_KEY_PREFIX = "jt808_";
	/**
	 * 成为OBD 表名
	 */
	public static final String CHENGWEI_TABLE_NAME = "device_chengwei_info";
	/**
	 * 成为GPS 定位数据列簇
	 */
	public static final String CHENGWEI_GPS_FAMILY_NAME = "trail";
	/**
	 * GPS rowkey前缀
	 */
	public static final String CHENGWEI_ROW_KEY_PREFIX = "cw_";

	/**
	 * MIDAS 表名
	 */
	public static final String MIDAS_TABLE_NAME = "device_madas_info";
	/**
	 * MIDAS GPS 定位数据列簇
	 */
	public static final String MIDAS_GPS_FAMILY_NAME = "madas_ttws_adas";
	/**
	 * GPS rowkey前缀
	 */
	public static final String MIDAS_ROW_KEY_PREFIX = "madas_";
	/**
	 * 轨迹数据点
	 */
	public static final String TARCK = "track";
	/**
	 * 速度集
	 */
	public static final String SPEEDS = "speeds";
	/**
	 * 加速度集
	 */
	public static final String ACCELERATED_SPEEDS = "acceleratedSpeeds";




}

