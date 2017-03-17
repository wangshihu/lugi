package com.benchu.lu.spi;

import java.net.InetSocketAddress;

import com.benchu.lu.core.entity.ReferConfig;

/**
 * @author benchu
 * @version 2017/3/13.
 */
public interface Directory {
    InetSocketAddress find(ReferConfig clazz);
}
