package com.fengjunlin.accident.prediction.model.web.controller.v1.exception;
import com.fengjunlin.accident.prediction.model.web.tools.ResultData;
import com.fengjunlin.accident.prediction.model.web.tools.constants.ServerConstants;
import com.fengjunlin.accident.prediction.model.web.utils.LogManager;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @Description
 * @Author fengjl
 * @Date 2019/6/14 16:03
 * @Version 1.0
 **/
@ControllerAdvice
public class ExceptionController  {
    /**
     * 控制层异常处理方法
     * @author fengjunlin
     * @date 2018/04/23
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResultData errorHandler(Exception e) {
        if (e instanceof ServerException) {
            ServerException businessException = (ServerException)e;
            return businessException.getResultData();
        }
        e.printStackTrace();
        LogManager.error(e, this.getClass());
        return new ResultData()
                .setSuccess(false)
                .setCode(ServerConstants.RETURN_CODE_SERVER_EXCEPTION)
                .setMessage("服务器繁忙，请稍后重试");
    }

}
