package com.benchu.lu.client.network;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.benchu.lu.common.exception.NetException;
import com.benchu.lu.core.Invocation;
import com.benchu.lu.core.Result;
import com.benchu.lu.core.message.LuMessage;
import com.benchu.lu.network.MsgHandler;
import com.benchu.lu.network.MsgHandlerContext;

/**
 * request时当消息注册器,id--listener,
 * response时,触发listener..
 *
 * @author benchu
 * @version on 15/11/1.
 */
public class ResponseMessageHandler implements ResponseSubscribe<LuMessage<Invocation>, Result>,
                                                   MsgHandler<LuMessage<Result>, Void> {
    private static Logger logger = LoggerFactory.getLogger(ResponseMessageHandler.class);
    private Map<Long, FutureResult<Result>> listeners = new ConcurrentHashMap<>();
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private long requestTimeout;

    public void init() {
        executorService.scheduleAtFixedRate(this::clear, 0, 1000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void messageReceived(LuMessage<Result> message, MsgHandlerContext<Void> ctx) {
        CompletableFuture<Result> future = this.listeners.remove(message.getRequestId());
        if (future == null) {
            logger.error("can't find listener of {}", message);
        } else {
            future.complete(message.getValue());
        }
    }

    @Override
    public CompletableFuture<Result> subscribe(LuMessage<Invocation> message) {
        Long key = message.getRequestId();
        FutureResult<Result> flag = listeners.get(key);
        if (flag != null) {
            logger.error("override listener of msg,id is {}", key);
        } else {
            flag = new FutureResult<>();
            listeners.put(key, flag);
        }
        return flag;
    }

    public void clear() {
        for (Map.Entry<Long, FutureResult<Result>> entry : listeners.entrySet()) {
            FutureResult<Result> future = entry.getValue();
            if (System.currentTimeMillis() - future.getCreateTime() > requestTimeout) {
                String msg = "client request timeout, requestId" + entry.getKey();
                Result result = new Result(NetException.Code.REQUEST_TIMEOUT,msg);
                future.complete(result);
            }
        }

    }

    public void setRequestTimeout(long requestTimeout) {
        this.requestTimeout = requestTimeout;
    }
}
