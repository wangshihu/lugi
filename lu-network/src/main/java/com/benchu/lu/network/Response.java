package com.benchu.lu.network;

/**
 * @author benchu
 * @version 2016/10/28.
 */
public interface Response {
    int getStatus();

    void setStatus(int status);

    Throwable getCause();

    void setCause(Throwable throwable);

}
