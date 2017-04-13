package com.benchu.lu.client.network;

import java.util.concurrent.CompletableFuture;

/**
 * 参照futureTask实现的future监听器类.
 * 去掉run方法,修改set方法.
 * set方法类似触发器,解除阻塞
 *
 * @author benchu
 * @version on 15/10/10.
 */
public class FutureResult<T> extends CompletableFuture<T>  {
    private long createTime;

    public FutureResult() {
        this.createTime = System.currentTimeMillis();
    }

    public long getCreateTime() {
        return createTime;
    }

}
