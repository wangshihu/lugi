package com.benchu.lu.network.protocol;

import com.benchu.lu.network.Response;

/**
 * @author benchu
 * @version 2016/10/28.
 */
public abstract class AbstractResponse  implements Response {
    private int status;
    private Throwable cause;

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    @Override
    public void setCause(Throwable cause) {
        this.cause = cause;
    }
}
