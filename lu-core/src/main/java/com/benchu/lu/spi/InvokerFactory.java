package com.benchu.lu.spi;

/**
 * @author benchu
 * @version 2016/12/20.
 */
public interface InvokerFactory<E> {
    Invoker newInvoker(E e);

}
