package com.benchu.lu.network;

import java.io.Closeable;

/**
 * @author benchu
 * @version 2016/10/30.
 */
public interface Transport extends Closeable{
    Server getServer(MsgHandler<?, ?> handler);

    Client getClient(MsgHandler<?, ?> handler);
}
