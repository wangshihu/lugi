package com.benchu.lu.client.network;

import java.util.concurrent.CompletableFuture;

/**
 * @author benchu
 * @version on 15/11/1.
 */
public interface ResponseSubscribe<K,E> {
    CompletableFuture<E> subscribe(K key);
}
