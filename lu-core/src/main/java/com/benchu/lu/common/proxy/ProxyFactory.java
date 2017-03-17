package com.benchu.lu.common.proxy;

import java.lang.reflect.InvocationHandler;

/**
 * @author benchu
 * @version on 15/10/24.
 * @since 0.1.0
 */
public interface ProxyFactory {

    <T> T proxy(Class<T> clazz, InvocationHandler invocationHandler);


    DelegateCaller getDelegateCaller(Class<?> clazz);

    interface DelegateCaller {
        Object call(Object target, String methodName, Object[] args, Class<?>[] argTypes) throws Throwable;
    }

    interface MethodCaller {
        Object invoke(Object target, Object[] args) throws Throwable;
    }
}
