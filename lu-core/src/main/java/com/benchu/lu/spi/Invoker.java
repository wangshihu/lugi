package com.benchu.lu.spi;

import java.io.Closeable;

import com.benchu.lu.core.Invocation;
import com.benchu.lu.core.Result;

/**
 * @author benchu
 * @version 2016/12/19.
 */
public interface Invoker extends Closeable{
    Result invoke(Invocation invocation);


}
