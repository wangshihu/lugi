package com.benchu.lu.network.netty.client;

import java.util.concurrent.CompletableFuture;

import com.benchu.lu.network.Request;
import com.benchu.lu.network.Response;

/**
 * @author benchu
 * @version 2016/10/28.
 */
public interface Sender {

    CompletableFuture<Response> send(Request message);

    CompletableFuture<Boolean> close();
}
