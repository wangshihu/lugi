package com.benchu.lu.network.netty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.benchu.lu.network.Connection;

import io.netty.channel.Channel;

/**
 * @author benchu
 * @version 2017/3/17.
 */
public class ConnectionManager {
    private Map<Channel, Connection> connectionMap = new ConcurrentHashMap<>();

    private AtomicInteger seq = new AtomicInteger();

    public Connection put(Channel channel) {
        Integer id = seq.incrementAndGet();
        Connection connection = new NettyConnection(channel, id);
        connectionMap.put(channel, connection);
        return connection;
    }

    public Connection get(Integer id) {
        return connectionMap.get(id);

    }

    public Connection remove(Channel channel){
        return connectionMap.remove(channel);
    }

    public Map<Channel, Connection> getConnectionMap() {
        return connectionMap;
    }
}

