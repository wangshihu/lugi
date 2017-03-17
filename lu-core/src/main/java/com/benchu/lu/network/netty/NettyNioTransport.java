package com.benchu.lu.network.netty;

import static com.benchu.lu.common.constans.CommonConstants.BOSS_THREAD_NAME_PREFIX;

import java.io.IOException;

import com.benchu.lu.common.NamedThreadFactory;
import com.benchu.lu.common.constans.CommonConstants;
import com.benchu.lu.core.configuration.Configuration;
import com.benchu.lu.network.Client;
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
    private NettyCodecAdaptor adaptor;
    private Configuration configuration;

    public NettyNioTransport(NettyCodecAdaptor adaptor, Configuration configuration) {
        this.adaptor = adaptor;
        this.configuration = configuration;
    }

    public Server getServer(MsgHandler<?, ?> serverMsgHandler) {
        ensureBossGroup();
        ensureWorkerGroup();
        ChannelProcessor<?, ?> processor = new ChannelProcessor<>(serverMsgHandler);
        ChannelInitializer channelInitializer = new DefaultChannelInitializer(processor, adaptor);
        return new NettyServer(bossGroup, workerGroup, channelInitializer, configuration.getConnectionTimeout());
    }

    public Client getClient(MsgHandler<?, ?> clientMsgHandler) {
        ensureWorkerGroup();
        ChannelProcessor<?, ?> processor = new ChannelProcessor<>(clientMsgHandler);
        return new NettyClient(new DefaultChannelInitializer(processor, adaptor), workerGroup);
    }

    private void ensureBossGroup() {
        if (this.bossGroup == null) {
            NamedThreadFactory f = new NamedThreadFactory(BOSS_THREAD_NAME_PREFIX);
            this.bossGroup = new NioEventLoopGroup(configuration.getBossThreads(), f);
        }
    }

    private void ensureWorkerGroup() {
        if (workerGroup == null) {
            NamedThreadFactory f = new NamedThreadFactory(CommonConstants.WORKER_THREAD_NAME_PREFIX);
            workerGroup = new NioEventLoopGroup(configuration.getWorkerThreads(), f);
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
}
