package com.benchu.lu.server.expose;

import java.util.HashMap;
import java.util.Map;

import com.benchu.lu.api.Lu;
import com.benchu.lu.common.exception.LuException;
import com.benchu.lu.common.proxy.ProxyFactory;
import com.benchu.lu.core.Invocation;
import com.benchu.lu.spi.Invoker;
import com.benchu.lu.spi.InvokerFactory;
import com.benchu.lu.spi.InvokerManager;
import com.benchu.lu.utils.StringUtils;

/**
 * @author benchu
 * @version 2016/12/19.
 */
public class ExposeInvokerManager implements InvokerFactory<Object>, InvokerManager {
    public Map<String, Invoker> proxyInvokerMap = new HashMap<>();

    private ProxyFactory deleteInvokerFactory;

    public Invoker newInvoker(Object targetBean) {
        Class<?> serviceClass = targetBean.getClass();
        Lu lu = serviceClass.getDeclaredAnnotation(Lu.class);
        if (lu == null) {
            throw new LuException("targetBean class:" + serviceClass.getName() + " has not Lu Annotation declared  ");
        }
        String service = getInterfaceName(lu,serviceClass);
        ProxyFactory.DelegateCaller delegateCaller = deleteInvokerFactory.getDelegateCaller(serviceClass);
        Invoker invoker = new ExposeInvoker(targetBean, delegateCaller);
        proxyInvokerMap.put(service, invoker);
        return invoker;
    }

    private String getInterfaceName(Lu lu, Class serviceClass) {
        Class<?>[] interfaces = serviceClass.getInterfaces();
        String luInterface = lu.interfaceName();
        String msg = "class:" + serviceClass.getName();
        if (StringUtils.isEmpty(luInterface)) {
            int length = interfaces.length;
            if (length != 1) {
                throw new LuException(msg + " has " + length + " interface, but annotation has not interfaceName())");
            } else {
                return interfaces[0].getName();
            }
        } else {
            for (Class<?> ai : interfaces) {
                if (ai.getName().equals(luInterface)) {
                    return luInterface;
                }
            }
            throw new LuException("annotation interfaceName=" + luInterface + ",but" + msg + " don't have interface");
        }
    }

    public Invoker getInvoker(Invocation invocation) {
        return proxyInvokerMap.get(invocation.getService());
    }

    public void setDeleteInvokerFactory(ProxyFactory deleteInvokerFactory) {
        this.deleteInvokerFactory = deleteInvokerFactory;
    }
}
