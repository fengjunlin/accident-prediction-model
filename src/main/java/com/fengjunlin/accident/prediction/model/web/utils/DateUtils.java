package com.fengjunlin.accident.prediction.model.web.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间工具类
 */
public class DateUtils {
    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);


    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String MINUTE_PATTERN = "yyyy-MM-dd HH:mm";

    public static final String HOUR_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static final String MONTH_PATTERN = "yyyy-MM";

    public static final String YEAR_PATTERN = "yyyy";

    public static final String MINUTE_ONLY_PATTERN = "mm";

    public static final String HOUR_ONLY_PATTERN = "HH";
    /**
     * 获取系统的当前时间
     *
     * @return 返回结果数据
     */
    public static String getCurrentTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_PATTERN);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 将传入的时间和和对应的时间格式进行解析，成为毫秒
     *
     * @param time 时间
     * @param timeformat
     * @return
     */
    public static Long getLongTime(String time,String timeformat) {
        SimpleDateFormat sdf = new SimpleDateFormat(timeformat);
        Date startDate = null;
        try {

            startDate = sdf.parse(time);
        } catch (ParseException e) {
            return null;
        }
        // 获取毫秒数
        Long startLong = startDate.getTime();
        return startLong;
    }

    /**
     * 将毫秒转换成为指定格式
     *
     * @param time
     * @return
     */
    public static String timeFormat(Long time){
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(date);
    }

    /**
     * @Author fengjl
     * @Description //TODO 查询只能查询7天的数据
     * @Date  2019/2/14
     * @return boolean
     */
    public static boolean checkTime(String strartTime , String lastTime) {

        String strTime = strartTime.substring(0, 8);
        String endTime = lastTime.substring(0,8);

        boolean flag = true;

        SimpleDateFormat format = new SimpleDateFormat ( "yyyyMMdd" );
        try {
            format.setLenient ( false );
            Date strDate = format.parse(strTime);
            Date endDate = format.parse(endTime);
            int day = differentDaysByMillisecond(strDate, endDate);
            if (day > 7){
                flag=false;
            }
        } catch (ParseException e) {
            flag = false;
        }

        return flag;
    }

    /**
     * @Author fengjl
     * @Description //TODO判断两个日期相差的天数
     * @return int
     */
    public static int differentDaysByMillisecond(Date date1,Date date2){
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

    public static void main(String[] args) {
        Long longTime = getLongTime("2019-06-25 15:07:05", "yyyy-MM-dd HH:mm:ss");
        System.out.println(longTime);
    }
}
