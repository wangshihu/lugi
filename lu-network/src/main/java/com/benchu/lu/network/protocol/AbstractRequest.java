package com.benchu.lu.network.protocol;

import com.benchu.lu.network.Request;

/**
 * @author benchu
 * @version 2016/10/28.
 */
public abstract class AbstractRequest implements Request {
    private long id;

    public AbstractRequest() {
    }

    public AbstractRequest(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
