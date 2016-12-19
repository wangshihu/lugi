package com.benchu.lu.network.netty.client;

import java.net.InetSocketAddress;

/**
 * @author benchu
 * @version on 15/11/1.
 */
public interface SenderFactory {
    Sender newSender(InetSocketAddress address);
}
