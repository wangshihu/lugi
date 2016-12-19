package com.benchu.lu.network.netty;

import java.io.IOException;

import com.benchu.lu.common.NamedThreadFactory;
import com.benchu.lu.network.Client;
import com.benchu.lu.network.LuConstants;
import com.benchu.lu.network.MsgHandler;
import com.benchu.lu.network.Server;
import com.benchu.lu.network.Transport;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @author benchu
 * @version on 15/11/1.
 */
public class NettyNioTransport implements Transport {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private int workerThreads;
    private int bossThreads;

    private NettyCodecAdaptor adaptor;

    public Server getServer(MsgHandler<?, ?> serverMsgHandler) {
        ensureBossGroup();
        ensureWorkerGroup();
        ChannelProcessor<?, ?> processor = new ChannelProcessor<>(serverMsgHandler);
        ChannelInitializer channelInitializer = new DefaultChannelInitializer(processor, adaptor);
        return new NettyServer(bossGroup, workerGroup, channelInitializer);
    }

    public Client getClient(MsgHandler<?, ?> clientMsgHandler) {
        ensureWorkerGroup();
        ChannelProcessor<?, ?> processor = new ChannelProcessor<>(clientMsgHandler);
        return new NettyClient(new DefaultChannelInitializer(processor, adaptor), workerGroup);
    }

    private void ensureBossGroup() {
        if (this.bossGroup == null) {
            this.bossGroup =
                new NioEventLoopGroup(this.bossThreads, new NamedThreadFactory(LuConstants.BOSS_THREAD_NAME_PREFIX));
        }
    }

    private void ensureWorkerGroup() {
        if (this.workerGroup == null) {
            this.workerGroup = new NioEventLoopGroup(this.workerThreads,
                                                     new NamedThreadFactory(LuConstants.WORKER_THREAD_NAME_PREFIX));
        }
    }

    // ------------ Setters ------------

    public void setWorkerThreads(int workerThreads) {
        if (workerThreads > 0) {
            this.workerThreads = workerThreads;
        }
    }

    public void setBossThreads(int bossThreads) {
        if (bossThreads > 0) {
            this.bossThreads = bossThreads;
        }
    }

    @Override
    public void close() throws IOException {
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
    }

    public NettyNioTransport setAdaptor(NettyCodecAdaptor adaptor) {
        this.adaptor = adaptor;
        return this;
    }
}
