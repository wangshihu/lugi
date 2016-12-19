package com.benchu.lu.network.netty.server;

import com.benchu.lu.network.Request;
import com.benchu.lu.network.Response;

/**
 * @author benchu
 * @version 2016/10/31.
 */
public interface ServerMsgHandler<I extends Request,O extends Response>  {
    O messageReceived(I request) ;
}
