package com.benchu.lu.server.network;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.benchu.lu.common.exception.NetException;
import com.benchu.lu.core.Invocation;
import com.benchu.lu.core.Result;
import com.benchu.lu.core.message.LuMessage;
import com.benchu.lu.core.message.MessageType;
import com.benchu.lu.network.MsgHandler;
import com.benchu.lu.network.MsgHandlerContext;
import com.benchu.lu.spi.Invoker;
import com.benchu.lu.spi.InvokerManager;

/**
 * @author benchu
 * @version 2016/10/31.
 */
public class ServerMsgRouter implements MsgHandler<LuMessage<Invocation>, LuMessage<Result>> {
    private ExecutorService executorService;
    private long serverHandleTimeout;
    private InvokerManager invokerManager;

    @Override
    public void messageReceived(LuMessage<Invocation> request, MsgHandlerContext<LuMessage<Result>> ctx) {
        Future<Result> future = executorService.submit(() -> {
            Invocation invocation = request.getValue();
            Invoker invoker = invokerManager.getInvoker(invocation);
            return invoker.invoke(invocation);
        });
        handleResponse(request, future, ctx);
    }

    private void handleResponse(LuMessage<Invocation> request, Future<Result> future,
                                MsgHandlerContext<LuMessage<Result>> ctx) {
        executorService.execute(() -> {
            LuMessage<Result> message = new LuMessage<>();
            Result result;
            try {
                result = future.get(serverHandleTimeout, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                result = new Result();
                if (e instanceof TimeoutException) {//其他的异常全部在proxyInvoker里封装了
                    future.cancel(true);//future.cancel????
                    result.setStatus(NetException.Code.SERVER_HANDLE_TIMEOUT.intValue());
                    result.setErrorMsg(e.getMessage());
                }
            }
            message.setMessageType(MessageType.result);
            message.setRequestId(request.getRequestId());
            message.setValue(result);
            ctx.respond(message);
        });
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setInvokerManager(InvokerManager invokerManager) {
        this.invokerManager = invokerManager;
    }

    public void setServerHandleTimeout(long serverHandleTimeout) {
        this.serverHandleTimeout = serverHandleTimeout;
    }

}
