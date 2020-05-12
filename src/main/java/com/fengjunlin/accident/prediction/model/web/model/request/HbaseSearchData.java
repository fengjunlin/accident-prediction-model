package com.fengjunlin.accident.prediction.model.web.model.request;
import com.fengjunlin.accident.prediction.model.web.controller.v1.exception.ServerException;
import com.fengjunlin.accident.prediction.model.web.tools.constants.ServerConstants;
import com.fengjunlin.accident.prediction.model.web.utils.DateUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @Description Hbase查询参数映射实体类
 * @Author fengjl
 * @Date 2019/6/27 14:07
 * @Version 1.0
 **/
public class HbaseSearchData {

    /**
     * 设备Id
     */
    private String deviceId;
    /**
     * 开始时间 时间格式为：
     */
    private String startTime;
    /**
     *  结束时间
     */
    private String endTime;
    /**
     * 设备类型 1：歌途云镜，2：MIDAS，3：成为OBD，4：两客一危（jt808）
     */
    private String deviceType;

    /**
     * 对传递过来的参数进行校验
     */
    public void paramCheck(){
        if (StringUtils.isEmpty(this.deviceId)) {
            throw new ServerException()
                    .setCode(ServerConstants.RETURN_CODE_NULL)
                    .setMessage("设备ID不能为空")
                    .setSuccess(false);
        }
        if (StringUtils.isEmpty(this.startTime)) {
            throw new ServerException()
                    .setCode(ServerConstants.RETURN_CODE_NULL)
                    .setMessage("开始时间不能为空")
                    .setSuccess(false);
        }
        if (StringUtils.isEmpty(this.endTime)) {
            throw new ServerException()
                    .setCode(ServerConstants.RETURN_CODE_NULL)
                    .setMessage("结束时间不能为空")
                    .setSuccess(false);
        }
        if (StringUtils.isEmpty(this.deviceType)) {
            throw new ServerException()
                    .setCode(ServerConstants.RETURN_CODE_NULL)
                    .setMessage("设备类型不能为空")
                    .setSuccess(false);
        }

        boolean flag = false;

        try {
            Long intStartTime = Long.parseLong(startTime);
            Long intEndTime = Long.parseLong(endTime);
            if (intStartTime > intEndTime) {
                throw new ServerException()
                        .setCode(ServerConstants.RETURN_CODE_NULL)
                        .setMessage("开始的时间比结束时间大")
                        .setSuccess(false);
            }
        } catch (NumberFormatException e) {
            flag = true;
        }

        if (flag) {
            throw new ServerException()
                    .setCode(ServerConstants.RETURN_CODE_NULL)
                    .setMessage("开始时间或者结束时间格式有误")
                    .setSuccess(false);
        }

        try {
            int intDeviceType = Integer.parseInt(this.deviceType);
            if (intDeviceType < 1 || intDeviceType > 4 ){
                throw new ServerException()
                        .setCode(ServerConstants.RETURN_CODE_NULL)
                        .setMessage("传入的设备类型格式有问题")
                        .setSuccess(false);
            }
        } catch (NumberFormatException e) {
            throw new ServerException()
                    .setCode(ServerConstants.RETURN_CODE_NULL)
                    .setMessage("设备类型格式有问题")
                    .setSuccess(false);
        }

        boolean bool = DateUtils.checkTime(this.startTime, this.endTime);
        if (!bool) {
            throw new ServerException()
                    .setCode(ServerConstants.RETURN_CODE_NULL)
                    .setMessage("开始时间和结束时间相差不能超过7天")
                    .setSuccess(false);
        }
    }

    public HbaseSearchData() {
    }

    public HbaseSearchData(String deviceId, String startTime, String endTime, String deviceType) {
        this.deviceId = deviceId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HbaseSearchData)) return false;

        HbaseSearchData that = (HbaseSearchData) o;

        if (getDeviceId() != null ? !getDeviceId().equals(that.getDeviceId()) : that.getDeviceId() != null)
            return false;
        if (getStartTime() != null ? !getStartTime().equals(that.getStartTime()) : that.getStartTime() != null)
            return false;
        if (getEndTime() != null ? !getEndTime().equals(that.getEndTime()) : that.getEndTime() != null) return false;
        return getDeviceType() != null ? getDeviceType().equals(that.getDeviceType()) : that.getDeviceType() == null;
    }

    @Override
    public int hashCode() {
        int result = getDeviceId() != null ? getDeviceId().hashCode() : 0;
        result = 31 * result + (getStartTime() != null ? getStartTime().hashCode() : 0);
        result = 31 * result + (getEndTime() != null ? getEndTime().hashCode() : 0);
        result = 31 * result + (getDeviceType() != null ? getDeviceType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HbaseSearchData{" +
                "deviceId='" + deviceId + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", deviceType='" + deviceType + '\'' +
                '}';
    }
}
