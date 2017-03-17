package com.benchu.lu.spi;

import java.io.Closeable;

import com.benchu.lu.LuContext;

/**
 * @author benchu
 * @version 2017/3/9.
 */
public interface Module extends Closeable {
    void init(LuContext context);
}
