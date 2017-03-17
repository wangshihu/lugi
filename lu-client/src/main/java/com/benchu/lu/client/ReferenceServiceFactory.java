package com.benchu.lu.client;

import java.lang.reflect.InvocationHandler;
import java.net.InetSocketAddress;

import com.benchu.lu.LuContext;
import com.benchu.lu.client.invoker.ClientInvokerFactory;
import com.benchu.lu.core.entity.ReferConfig;
import com.benchu.lu.spi.Invoker;

/**
 * @author benchu
 * @version 2017/3/9.
 */
public class ReferenceServiceFactory {

    private ClientInvokerFactory invokerFactory;
    private LuContext context;

    public void setInvokerFactory(ClientInvokerFactory invokerFactory) {
        this.invokerFactory = invokerFactory;
    }

    public void setContext(LuContext context) {
        this.context = context;
    }

    public <T> T getService(ReferConfig<T> config) {
        Class<T> service = config.getService();
        InetSocketAddress address = context.getDirectory().find(config);
        Invoker invoker = invokerFactory.newInvoker(address);
        InvocationHandler handler = new InvokeInvocationHandler(service.getName(), invoker);
        return context.getProxyFactory().proxy(service, handler);
    }
}
