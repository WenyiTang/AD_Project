package com.example.adproject.api;

import lombok.Data;

@Data
public class ResultJson<T> {
    private int code;
    private String msg;
    private T data;
    /**     * Operation is successful     */    public static final ResultJson SUCCESS_RESULT = new ResultJson(0, "Success!!!");
    /**     * system error     */    public static final ResultJson SYSTEM_ERROR_RESULT = new ResultJson(1, "System error, please try again later!!!");
    /**     * login error     */    public static final ResultJson LOGIN_ERROR_RESULT = new ResultJson(2, "The login information is invalid. Please log in again!!!");
    /**     * parameters error     */    public static final ResultJson PARAM_ERROR_RESULT = new ResultJson(3, "The request parameters are abnormal. Please try again!!!");
    /**     * Operation failed     */    public static final ResultJson FAIL_RESULT = new ResultJson(4, "Operation failed. Please try again!!!");

    /**     * default error code     */    public static final int ERROR = 9;
    public ResultJson() {    }


    public ResultJson(T data) {        this.code = ResultJson.SUCCESS_RESULT.getCode();        this.msg = ResultJson.SUCCESS_RESULT.getMsg();        this.data = data;    }
    public ResultJson(int code, String msg) {        this.code = code;        this.msg = msg;    }
    public ResultJson(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public ResultJson(ResultJson param, T data) {        this.code = param.getCode();        this.msg = param.getMsg();        this.data = data;    }
    public static <T> ResultJson<T> error(String message) {        return (ResultJson<T>) new ResultJson(ERROR, message);    }
    public static <T> ResultJson<T> data(T data) {        return (ResultJson<T>) new ResultJson(SUCCESS_RESULT, data);    }
    public int getCode() {        return code;    }
    public void setCode(int code) {        this.code = code;    }
    public String getMsg() {        return msg;    }
    public void setMsg(String msg) {        this.msg = msg;    }
    public T getData() {        return data;    }
    public void setData(T data) {        this.data = data;    }

}
