package com.fengjunlin.accident.prediction.model.web.tools;

/**
 * @Description
 * @Author fengjl
 * @Date 2019/6/14 15:59
 * @Version 1.0
 **/
public class ResultData implements Cloneable {
    /**
     * 返回码
     */
    private int code;

    /**
     * 返回提示信息
     */
    private String message;

    /**
     * 成功标识
     */
    private boolean success;

    /**
     * 返回数据
     */
    private Object data;

    /**
     * 分页标识，是否还有其他数据
     */
    private boolean hasNext;

    public int getCode() {
        return code;
    }

    public ResultData setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResultData setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public ResultData setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResultData setData(Object data) {
        this.data = data;
        return this;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public ResultData setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
        return this;
    }

    @Override
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

}
