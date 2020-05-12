package com.fengjunlin.accident.prediction.model.web.model.request;
import com.fengjunlin.accident.prediction.model.web.controller.v1.exception.ServerException;
import com.fengjunlin.accident.prediction.model.web.model.drivingmodel.OriginalDataPoint;
import com.fengjunlin.accident.prediction.model.web.tools.constants.RequestConstants;
import com.fengjunlin.accident.prediction.model.web.tools.constants.ServerConstants;
import org.apache.commons.lang.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
/**
 * @Description 驾驶模型请求参数映射实体类
 * @Author fengjl
 * @Date 2019/6/21 15:31
 * @Version 1.0
 **/
public class OriginalDataRequest {

    /**
     *  时间格式 0：0  1：yyyyMMddHHmmssSSS 2：yyyy-MM-dd HH:mm:ss
     */
    private String timeType;
    /**
     * 经纬度类型，0：百度,1：高德,2：Gps,3:wgs-84
     */
    private String lngAndLatType;
    /**
     * 传递过来的行程数据
     */
    List<OriginalDataPoint> data;

    /**
     * 对传递过来的参数进行校验
     */
    public void paramCheck(){
        if (StringUtils.isEmpty(this.timeType)) {
            throw new ServerException()
                    .setCode(ServerConstants.RETURN_CODE_NULL)
                    .setMessage("时间类型不能为空")
                    .setSuccess(false);
        }
        if (StringUtils.isEmpty(this.lngAndLatType)) {
            throw new ServerException()
                    .setCode(ServerConstants.RETURN_CODE_NULL)
                    .setMessage("经纬度类型不能为空")
                    .setSuccess(false);
        }
        if (this.data.size() == 0) {
            throw  new ServerException()
                    .setCode(ServerConstants.RETURN_CODE_NULL)
                    .setMessage("行程数据不能为空")
                    .setSuccess(false);
        }
        /*对数据的时间格式和传入的时间格式进行对比*/
        int flag = 0;
        for (OriginalDataPoint point:data) {
            String time = point.getTime();
            SimpleDateFormat format = new SimpleDateFormat (RequestConstants.mapping.get(this.timeType));
            try {
                format.setLenient(false);
                format.parse (time);
            } catch (ParseException e) {
                flag++;
                e.printStackTrace();
            }
        }
        if (flag == this.data.size()) {
            throw new ServerException()
                    .setCode(ServerConstants.RETURN_CODE_NULL)
                    .setMessage("数据的时间格0和传入的格式不匹配")
                    .setSuccess(false);
        }
    }

    public OriginalDataRequest() {
    }

    public OriginalDataRequest(String timeType, String lngAndLatType, List<OriginalDataPoint> data) {
        this.timeType = timeType;
        this.lngAndLatType = lngAndLatType;
        this.data = data;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getLngAndLatType() {
        return lngAndLatType;
    }

    public void setLngAndLatType(String lngAndLatType) {
        this.lngAndLatType = lngAndLatType;
    }

    public List<OriginalDataPoint> getData() {
        return data;
    }

    public void setData(List<OriginalDataPoint> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OriginalDataRequest{" +
                "timeType=" + timeType +
                ", lngAndLatType=" + lngAndLatType +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OriginalDataRequest)) return false;

        OriginalDataRequest that = (OriginalDataRequest) o;

        if (getTimeType() != null ? !getTimeType().equals(that.getTimeType()) : that.getTimeType() != null)
            return false;
        if (getLngAndLatType() != null ? !getLngAndLatType().equals(that.getLngAndLatType()) : that.getLngAndLatType() != null)
            return false;
        return getData() != null ? getData().equals(that.getData()) : that.getData() == null;
    }

    @Override
    public int hashCode() {
        int result = getTimeType() != null ? getTimeType().hashCode() : 0;
        result = 31 * result + (getLngAndLatType() != null ? getLngAndLatType().hashCode() : 0);
        result = 31 * result + (getData() != null ? getData().hashCode() : 0);
        return result;
    }

}
