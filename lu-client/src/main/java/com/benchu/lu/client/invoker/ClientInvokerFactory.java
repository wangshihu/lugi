package com.benchu.lu.client.invoker;

import java.net.InetSocketAddress;

import com.benchu.lu.client.network.ResponseSubscribe;
import com.benchu.lu.core.Invocation;
import com.benchu.lu.core.Result;
import com.benchu.lu.core.message.LuMessage;
import com.benchu.lu.network.Client;
import com.benchu.lu.network.Connection;
import com.benchu.lu.spi.Invoker;
import com.benchu.lu.spi.InvokerFactory;

/**
 * @author benchu
 * @version 2016/12/20.
 */
public class ClientInvokerFactory implements InvokerFactory<InetSocketAddress> {

    protected Client client;
    protected ResponseSubscribe<LuMessage<Invocation>, Result> responseSubscribe;

    @Override
    public Invoker newInvoker(InetSocketAddress address) {
        Connection connection = client.connect(address);
        ClientInvoker exchanger = new ClientInvoker();
        exchanger.setConnection(connection);
        exchanger.setResponseSubscribe(responseSubscribe);
        return exchanger;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setResponseSubscribe(ResponseSubscribe<LuMessage<Invocation>, Result> responseSubscribe) {
        this.responseSubscribe = responseSubscribe;
    }

}
