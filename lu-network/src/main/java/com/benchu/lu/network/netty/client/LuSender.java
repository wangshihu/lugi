package com.benchu.lu.network.netty.client;

import static com.benchu.lu.network.netty.client.NetException.Code.CONNECTIONLOSS;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

import com.benchu.lu.network.Connection;
import com.benchu.lu.network.Request;
import com.benchu.lu.network.Response;
import com.benchu.lu.network.message.LuMessage;
import com.benchu.lu.network.message.MessageType;
import com.benchu.lu.network.serialize.MessageTypeManager;
import com.google.common.base.Preconditions;

/**
 * @author benchu
 * @version 2016/10/28.
 */
public class LuSender implements Sender {
    private Connection connection;
    private static AtomicLong seq = new AtomicLong();
    private MessageTypeManager messageTypeManager;
    private ResponseSubscribe<LuMessage<Request>, LuMessage<Response>> responseSubscribe;

    public LuSender() {
    }

    @Override
    public CompletableFuture<Response> send(Request request) {
        CompletableFuture<Response> result = new CompletableFuture<>();
        if (!connection.isAvailable()) {//想一想重连机制
            result.completeExceptionally(new NetException(CONNECTIONLOSS, "connection  is not available"));
            return result;
        }
        LuMessage<Request> message = new LuMessage<>();
        message.setValue(request);
        message.setRequestId(seq.incrementAndGet());
        MessageType messageType = messageTypeManager.checkAndGet(request.getClass());
        Preconditions.checkNotNull(messageType,"cannot find messageType ,class:"+request.getClass());
        message.setMessageType(messageType);
        CompletableFuture<LuMessage<Response>> rawFuture= responseSubscribe.subscribe(message);
        connection.writeAndFlush(message);
        return rawFuture.thenApply(LuMessage::getValue);
    }


    @Override
    public CompletableFuture<Boolean> close() {
        return connection.close();
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setResponseSubscribe(ResponseSubscribe<LuMessage<Request>, LuMessage<Response>> responseSubscribe) {
        this.responseSubscribe = responseSubscribe;
    }

    public void setMessageTypeManager(MessageTypeManager messageTypeManager) {
        this.messageTypeManager = messageTypeManager;
    }
}
