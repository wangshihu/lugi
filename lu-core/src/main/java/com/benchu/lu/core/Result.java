package com.benchu.lu.core;

import com.benchu.lu.common.exception.NetException;

/**
 * @author benchu
 * @version 2016/10/28.
 */
public class Result {
    private int status;

    private String errorMsg;

    private Object value;

    public Result() {
    }

    public Result(NetException.Code code, String errorMsg) {
        this.status = code.intValue();
        this.errorMsg = errorMsg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object recreate() throws Throwable {
        if (errorMsg != null || status != 0) {
            throw new Exception("remote messageReceived error!  msg is :" + errorMsg);
        }
        return value;
    }

}
