package com.benchu.lu.network.netty.client;

import com.benchu.lu.network.Response;

/**
 * @author benchu
 * @version 2016/10/31.
 */
public interface NetFuture {
    Response result();

    Response get();

    void whenComplete(Response response, NetException exception);





}
