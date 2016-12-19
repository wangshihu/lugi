package com.benchu.lu.network.netty.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.benchu.lu.network.MsgHandler;
import com.benchu.lu.network.MsgHandlerContext;
import com.benchu.lu.network.Request;
import com.benchu.lu.network.Response;
import com.benchu.lu.network.message.LuMessage;
import com.benchu.lu.network.message.MessageType;
import com.benchu.lu.network.netty.client.NetException;
import com.benchu.lu.network.protocol.ErrorResponse;
import com.benchu.lu.network.serialize.MessageTypeManager;

/**
 * @author benchu
 * @version 2016/10/31.
 */
public class ServerMsgRouter implements MsgHandler<LuMessage<Request>, LuMessage<Response>> {
    private ExecutorService executorService;
    private MessageTypeManager messageTypeManager;
    private long serverHandleTimeout;

    @Override
    public void messageReceived(LuMessage<Request> request, MsgHandlerContext<LuMessage<Response>> ctx) {
        Future<Response> future = executorService.submit(() -> {
            ServerMsgHandler handler = messageTypeManager.checkAndGetHandler(request.getMessageType());
            return handler.messageReceived(request.getValue());
        });
        handleResponse(request, future, ctx);
    }

    private void handleResponse(LuMessage<Request> request, Future<Response> future,
                                MsgHandlerContext<LuMessage<Response>> ctx) {
        executorService.execute(() -> {
            LuMessage<Response> message = new LuMessage<>();
            Response response;
            try {
                response = future.get(serverHandleTimeout, TimeUnit.MILLISECONDS);
                message.setMessageType(messageTypeManager.checkAndGet(response.getClass()));
            } catch (Throwable e) {
                response = new ErrorResponse();
                if (e instanceof TimeoutException) {//future.cancel????
                    future.cancel(true);
                    response.setStatus(NetException.Code.SERVERHANDLE_TIMEOUT.intValue());
                } else {
                    response.setStatus(NetException.Code.SERVERHANDLE_ERROR.intValue());
                }
                response.setCause(e);
            }
            message.setSerializeType(request.getSerializeType());
            MessageType messageType = messageTypeManager.checkAndGet(response.getClass());
            message.setMessageType(messageType);
            message.setRequestId(request.getRequestId());
            message.setValue(response);
            ctx.respond(message);
        });
    }

    public void setMessageTypeManager(MessageTypeManager messageTypeManager) {
        this.messageTypeManager = messageTypeManager;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setServerHandleTimeout(long serverHandleTimeout) {
        this.serverHandleTimeout = serverHandleTimeout;
    }
}
