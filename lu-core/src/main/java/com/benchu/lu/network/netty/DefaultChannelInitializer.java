package com.benchu.lu.network.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author benchu
 * @version on 15/11/1.
 */
public class DefaultChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final ChannelHandler channelHandler;
    private NettyCodecAdaptor adaptor;

    public DefaultChannelInitializer(ChannelHandler channelHandler, NettyCodecAdaptor adaptor) {
        this.channelHandler = channelHandler;
        this.adaptor = adaptor;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("decoder", adaptor.decoder());
        pipeline.addLast("encoder", adaptor.encoder());
        //         --- 处理消息 ---
        pipeline.addLast("channlHandler", channelHandler);
    }

}
