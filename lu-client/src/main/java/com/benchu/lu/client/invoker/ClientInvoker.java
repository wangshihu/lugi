package com.benchu.lu.client.invoker;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

import com.benchu.lu.client.network.ResponseSubscribe;
import com.benchu.lu.common.exception.NetException;
import com.benchu.lu.core.Invocation;
import com.benchu.lu.core.Result;
import com.benchu.lu.core.message.LuMessage;
import com.benchu.lu.core.message.MessageType;
import com.benchu.lu.network.Connection;
import com.benchu.lu.spi.Invoker;

/**
 * @author benchu
 * @version 2016/12/20.
 */
public class ClientInvoker implements Invoker {
    private Connection connection;
    private static AtomicLong seq = new AtomicLong();
    private ResponseSubscribe<LuMessage<Invocation>, Result> responseSubscribe;

    public ClientInvoker() {
    }

    @Override
    public Result invoke(Invocation invocation) {
        if (!connection.isAvailable()) {//todo 想一想重连机制
            Result result = new Result();
            result.setStatus(NetException.Code.CONNECTION_LOSS.intValue());
            result.setErrorMsg("connection is not available");
            return result;
        }
        LuMessage<Invocation> message = new LuMessage<>();
        message.setValue(invocation);
        message.setRequestId(seq.incrementAndGet());
        message.setMessageType(MessageType.invocation);
        CompletableFuture<Result> rawFuture = responseSubscribe.subscribe(message);
        connection.writeAndFlush(message);
        try {
            return rawFuture.get();
        } catch (Exception e) {
            return new Result(NetException.Code.RUNTIME_ERROR, e.getMessage());
        }
    }

    @Override
    public void close() {
        connection.close();
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setResponseSubscribe(ResponseSubscribe<LuMessage<Invocation>, Result> responseSubscribe) {
        this.responseSubscribe = responseSubscribe;
    }

}
