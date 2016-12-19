package com.benchu.lu.network.netty;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.benchu.lu.network.Connection;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.DefaultChannelPromise;
import io.netty.util.concurrent.GenericFutureListener;

class NettyConnection implements Connection {
    private static Logger logger = LoggerFactory.getLogger(NettyConnection.class);
    private static GenericFutureListener writeListener = new GenericFutureListener<DefaultChannelPromise>() {
        @Override
        public void operationComplete(DefaultChannelPromise future) throws Exception {
            if (!future.isSuccess()) {
                logger.error("write or flush error", future.cause());
            }
        }
    };
    private final Channel channel;
    private final Integer id;


    public NettyConnection(Channel channel, Integer id) {
        this.channel = channel;
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void write(Object obj) {
        channel.write(obj);
    }

    @Override
    public void flush() {
        channel.flush();
    }

    @Override
    public void writeAndFlush(Object obj) {
        ChannelFuture future = channel.writeAndFlush(obj);
        future.addListener(writeListener);
    }

    @Override
    public CompletableFuture<Boolean> close() {
        ChannelFuture channelFuture = channel.close();
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        channelFuture.addListener(future -> completableFuture.complete(future.isSuccess()));
        return completableFuture;
    }

    @Override
    public boolean isAvailable() {
        return channel.isActive() && channel.isOpen();
    }

}
