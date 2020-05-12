package com.fengjunlin.accident.prediction.model.web.controller.v1.exception;

import com.fengjunlin.accident.prediction.model.web.tools.ResultData;

/**
 * @Description
 * @Author fengjl
 * @Date 2019/6/14 16:02
 * @Version 1.0
 **/
public class ServerException extends RuntimeException {
    private static final long serialVersionUID = 1001L;
    /**
     * 返回对象，该对象中包含异常原因，异常码，成功等信息
     */
    private ResultData resultData;

    /**
     * 公共类，初始化返回对象
     */
    public ServerException () {
        this.resultData = new ResultData();
        this.resultData.setSuccess(false)
                .setHasNext(false)
                .setMessage(null)
                .setData(null)
                .setCode(0);
    }

    /**
     * 返回异常对象信息
     * @return
     */
    public ResultData getResultData() {
        return resultData;
    }

    /**
     * 设置返回码
     * @return
     */
    public ServerException setCode(Integer code) {
        if (code != null) {
            this.resultData.setCode(code);
        }
        return this;
    }

    /**
     * 设置返回信息
     * @return
     */
    public ServerException setMessage(String message) {
        if (message != null) {
            this.resultData.setMessage(message);
        }
        return this;
    }

    /**
     * 设置返回成功标识
     * @return
     */
    public ServerException setSuccess(Boolean success) {
        if (success != null) {
            this.resultData.setSuccess(success);
        }
        return this;
    }


}
