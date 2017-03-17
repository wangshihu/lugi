package com.benchu.lu.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.benchu.lu.core.Invocation;
import com.benchu.lu.core.Result;
import com.benchu.lu.spi.Invoker;

/**
 * @author benchu
 * @version on 15/10/24.
 */
public class InvokeInvocationHandler implements InvocationHandler {

    private String service;

    private Invoker invoker;

    public InvokeInvocationHandler(String service, Invoker invoker) {
        this.service = service;
        this.invoker = invoker;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if (args == null) {
            args = new Object[0];
        }
        // 处理Object中常见的方法
        if (args.length == 0) {
            if ("toString".equals(methodName)) {
                return invoker.toString();
            } else if ("hashCode".equals(methodName)) {
                return invoker.hashCode();
            }
        } else if (args.length == 1) {
            if ("equals".equals(methodName)) {
                return invoker.equals(args[0]);
            }
        }

        Invocation invocation = new Invocation(service, methodName, args);

        Result result = invoker.invoke(invocation);

        Object value = result.recreate();
        if (method.getReturnType().isPrimitive()) {
            // 为返回类型为基本数据类型，而返回值为null时，做适配
            if (value == null) {
                if (Boolean.TYPE.equals(method.getReturnType())) {
                    value = Boolean.FALSE;
                } else if (Character.TYPE.equals(method.getReturnType())) {
                    value = (char) 0;
                } else if (Byte.TYPE.equals(method.getReturnType())) {
                    value = (byte) 0;
                } else if (Short.TYPE.equals(method.getReturnType())) {
                    value = (short) 0;
                } else if (Integer.TYPE.equals(method.getReturnType())) {
                    value = 0;
                } else if (Long.TYPE.equals(method.getReturnType())) {
                    value = 0L;
                } else if (Float.TYPE.equals(method.getReturnType())) {
                    value = 0F;
                } else if (Double.TYPE.equals(method.getReturnType())) {
                    value = 0d;
                }
            }
        }
        return value;
    }
}
