package com.benchu.lu.spi;

import com.benchu.lu.core.Invocation;

/**
 * @author benchu
 * @version 2016/12/19.
 */
public interface InvokerManager {
    Invoker getInvoker(Invocation invocation);
}
