package com.benchu.lu.common.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author  benchu
 * @since 0.1.0
 * @version on 15/10/24.
 */
public class JavaProxyFactory extends AbstractProxyFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> T proxy(Class<T> clazz, InvocationHandler invocationHandler) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz }, invocationHandler);
    }

    @Override
    protected MethodCaller getMethodCaller(Method method,Class<?>clazz) {
        return method::invoke;
    }
}
