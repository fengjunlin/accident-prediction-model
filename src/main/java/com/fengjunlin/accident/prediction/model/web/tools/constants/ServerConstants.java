package com.fengjunlin.accident.prediction.model.web.tools.constants;

/**
 * @Description
 * @Author fengjl
 * @Date 2019/6/14 16:06
 * @Version 1.0
 **/
public class ServerConstants {
    /**
     * 服务器返回码：成功
     */
    public final static int RETURN_CODE_SUCCESS = 200;

    /**
     * 服务器返回码：参数校验错误
     */
    public final static int RETURN_CODE_VERIFY_FAILED = 300;

    /**
     * 服务器返回码：查询数据为空或操作失败
     */
    public final static int RETURN_CODE_NULL = 400;

    /**
     * 服务器返回码：服务器异常，前端提醒时提示“服务器繁忙，请稍后重试”
     * 若要区分不同的参数，可使用 50x－5xx ，具体值可有有开发人员协定，如：
     */
    public final static int RETURN_CODE_SERVER_EXCEPTION = 500;

    /**
     * 系统默认每页记录数
     */
    public final static int PAGE_SIZE = 10;
}
