package com.fengjunlin.accident.prediction.model.web.tools;

import com.fengjunlin.accident.prediction.model.web.tools.ResultData;
import com.fengjunlin.accident.prediction.model.web.tools.constants.ServerConstants;

/**
 * @Description
 * @Author fengjl
 * @Date 2019/6/14 17:08
 * @Version 1.0
 **/
public final class ServerAck {

    /**
     * 成功
     */
    @SuppressWarnings("unused")
    private ResultData success;

    /**
     * 参数异常
     */
    @SuppressWarnings("unused")
    private ResultData paramError;

    /**
     * 空数据
     */
    @SuppressWarnings("unused")
    private ResultData emptyData;

    /**
     * 失败
     */
    @SuppressWarnings("unused")
    private ResultData failure;

    /**
     * 服务异常
     */
    @SuppressWarnings("unused")
    private ResultData serverError;

    public ResultData getSuccess() {
        return new ResultData()
                .setCode(ServerConstants.RETURN_CODE_SUCCESS)
                .setMessage("操作成功")
                .setSuccess(true);
    }

    public ResultData getParamError() {
        return new ResultData()
                .setCode(ServerConstants.RETURN_CODE_VERIFY_FAILED)
                .setMessage("参数异常")
                .setSuccess(false);
    }


    public ResultData getEmptyData() {
        return new ResultData()
                .setCode(ServerConstants.RETURN_CODE_NULL)
                .setMessage("无数据")
                .setSuccess(false);
    }

    public ResultData getFailure() {
        return new ResultData()
                .setCode(ServerConstants.RETURN_CODE_NULL)
                .setMessage("操作失败")
                .setSuccess(false);
    }

    public ResultData getServerError() {
        return new ResultData()
                .setCode(ServerConstants.RETURN_CODE_SERVER_EXCEPTION)
                .setMessage("服务器繁忙，请稍后重试")
                .setSuccess(false);
    }
}
