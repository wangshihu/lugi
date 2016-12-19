package com.benchu.lu.network.netty.client;

import static com.benchu.lu.network.netty.client.NetException.Code.REQUESTTIMEOUT;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.benchu.lu.network.MsgHandler;
import com.benchu.lu.network.MsgHandlerContext;
import com.benchu.lu.network.Request;
import com.benchu.lu.network.Response;
import com.benchu.lu.network.message.LuMessage;
import com.benchu.lu.network.protocol.ErrorResponse;

/**
 * request时当消息注册器,id--listener,
 * response时,触发listener..
 *
 * @author benchu
 * @version on 15/11/1.
 */
public class ResponseMessageHandler implements ResponseSubscribe<LuMessage<Request>, LuMessage<Response>>,
                                                   MsgHandler<LuMessage<Response>, Void> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseMessageHandler.class);
    private Map<Long, FutureResult<LuMessage<Response>>> listeners = new ConcurrentHashMap<>();
    private ScheduledExecutorService executorService;
    private long requestTimeout;

    public void init() {
        executorService.scheduleAtFixedRate(this::clear, 0, 1000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void messageReceived(LuMessage<Response> message, MsgHandlerContext<Void> ctx) {
        CompletableFuture<LuMessage<Response>> future = this.listeners.remove(message.getRequestId());
        if (future == null) {
            LOGGER.error("can't find listener of {}", message);
        } else {
            Response response = message.getValue();
            if (response instanceof ErrorResponse) {
                future.completeExceptionally(new NetException(response.getStatus(), response.getCause()));
            } else {
                future.complete(message);
            }
        }
    }

    @Override
    public CompletableFuture<LuMessage<Response>> subscribe(LuMessage<Request> message) {
        Long key = message.getRequestId();
        FutureResult<LuMessage<Response>> flag = listeners.get(key);
        if (flag != null) {
            LOGGER.error("override listener of msg,id is {}", key);
        } else {
            flag = new FutureResult<>();
            listeners.put(key, flag);
        }
        return flag;
    }

    public void clear() {
        for (Map.Entry<Long, FutureResult<LuMessage<Response>>> entry : listeners.entrySet()) {
            FutureResult future = entry.getValue();
            if (System.currentTimeMillis() - future.getCreateTime() > requestTimeout) {
                String msg = "client request timeout,requestId" + entry.getKey();
                future.completeExceptionally(new NetException(REQUESTTIMEOUT, msg));
            }
        }

    }

    public void setExecutorService(ScheduledExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setRequestTimeout(long requestTimeout) {
        this.requestTimeout = requestTimeout;
    }
}
