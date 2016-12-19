package com.benchu.lu.network;

import java.util.concurrent.CompletableFuture;

/**
 * @author benchu
 * @version 2016/10/28.
 */
public interface Connection {
    Integer getId();

    <T> void write(T obj);

    void flush();

    <T> void writeAndFlush(T obj);

    boolean isAvailable();

    CompletableFuture<Boolean> close();
}
