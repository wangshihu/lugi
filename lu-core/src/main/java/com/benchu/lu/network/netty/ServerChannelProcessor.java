/*
 * Copyright 2011-2015 Mogujie Co.Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.benchu.lu.network.netty;

import java.io.Closeable;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.benchu.lu.network.MsgHandler;
import com.benchu.lu.network.MsgHandlerContext;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Channel消息处理类
 *
 */
@Sharable
class ServerChannelProcessor<I, O> extends SimpleChannelInboundHandler<I> implements Closeable {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    private final AtomicInteger seq = new AtomicInteger();

    private MsgHandler<I, O> msgHandler;

    private ConnectionManager connectionManager;

    @SuppressWarnings("unchecked")
    public ServerChannelProcessor(MsgHandler<I, O> msgHandler) {
        super((Class<? extends I>) Object.class);
        this.msgHandler = msgHandler;
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, I msg) throws Exception {
        this.logger.debug("msg " + msg + " received from remoteAddress:" + ctx.channel().remoteAddress());
        this.msgHandler.messageReceived(msg, new MsgHandlerContext<O>() {
            @Override
            public void respond(O resp) {
                try {
                    ctx.writeAndFlush(resp);
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }

            @Override
            public InetSocketAddress remoteAddress() {
                return (InetSocketAddress) ctx.channel().remoteAddress();
            }
        });

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        connectionManager.put(channel);
        logger.warn("channel active. channel：" + channel);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.warn("channel closed." + ctx.channel() + " close");
        connectionManager.remove(ctx.channel());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("netty exception occur!remoteAddress=" +ctx.channel().remoteAddress(), cause);
        ctx.fireExceptionCaught(cause);
    }

    @Override
    public void close() {
        for (Channel channel : connectionManager.getConnectionMap().keySet()) {
            channel.close().syncUninterruptibly();
        }
    }

}
