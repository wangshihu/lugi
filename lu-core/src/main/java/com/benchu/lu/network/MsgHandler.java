package com.benchu.lu.network;

/**
 * @author  benchu
 * @version on 15/10/25.
 */
public interface MsgHandler<I,O> {

     void messageReceived(I message, MsgHandlerContext<O> ctx) ;
}
