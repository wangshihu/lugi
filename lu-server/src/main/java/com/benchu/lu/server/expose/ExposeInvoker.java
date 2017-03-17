package com.benchu.lu.server.expose;

import java.io.IOException;

import com.benchu.lu.common.exception.NetException;
import com.benchu.lu.common.proxy.ProxyFactory;
import com.benchu.lu.core.Invocation;
import com.benchu.lu.core.Result;
import com.benchu.lu.spi.Invoker;

/**
 * @author benchu
 * @version 2016/12/19.
 */
public class ExposeInvoker implements Invoker {
    private Object targetBean;
    private ProxyFactory.DelegateCaller delegateCaller;

    public ExposeInvoker() {

    }

    public ExposeInvoker(Object targetBean, ProxyFactory.DelegateCaller delegateCaller) {
        this.targetBean = targetBean;
        this.delegateCaller = delegateCaller;
    }

    public Result invoke(Invocation invocation) {
        Result result = new Result();
        try {
            Object value= delegateCaller.call(targetBean, invocation.getMethod(), invocation.getArguments(),
                                              invocation.argTypes());
            result.setValue(value);
            result.setStatus(0);
        } catch (Throwable e) {
            //执行异常.封装到 Result中
            result.setErrorMsg(e.getMessage());
            result.setStatus(NetException.Code.SERVER_HANDLE_ERROR.intValue());
        }
        return result;
    }

    @Override
    public void close() throws IOException {

    }
}
