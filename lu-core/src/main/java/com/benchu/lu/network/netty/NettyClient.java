package com.benchu.lu.network.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.benchu.lu.network.Client;
import com.benchu.lu.network.Connection;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author benchu
 * @version on 15/11/1.
 */
public class NettyClient implements Client {
    private Bootstrap bootstrap;

    private EventLoopGroup workerGroup;
    //todo
    private int connectTimeoutMills ;

    private ChannelInitializer<SocketChannel> channelInitializer;

    private final AtomicInteger channelSeq = new AtomicInteger();

    private final Map<Integer, Connection> connectionMap = new ConcurrentHashMap<>();

    NettyClient(ChannelInitializer<SocketChannel> channelInitializer, EventLoopGroup workerGroup) {
        this.channelInitializer = channelInitializer;
        this.workerGroup = workerGroup;
        this.bootstrap = this.prepareBootstrap();
    }

    @Override
    public Connection connect(InetSocketAddress remoteAddress) {
        //复用bootstrap
        io.netty.channel.Channel channel = this.bootstrap.connect(remoteAddress).syncUninterruptibly().channel();
        NettyConnection nettyConnection = new NettyConnection(channel, this.channelSeq.incrementAndGet());
        connectionMap.put(nettyConnection.getId(), nettyConnection);
        return nettyConnection;
    }

    @Override
    public Connection getConnection(Integer id) {
        return connectionMap.get(id);
    }

    private Bootstrap prepareBootstrap() {
        Bootstrap b = new Bootstrap();
        b.group(workerGroup);
        b.option(ChannelOption.TCP_NODELAY, true).option(ChannelOption.SO_KEEPALIVE, true);
        b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, this.connectTimeoutMills);
        b.channel(NioSocketChannel.class).handler(this.channelInitializer);
        return b;
    }

    @Override
    public void close() throws IOException {
        this.workerGroup.shutdownGracefully();
    }


}
