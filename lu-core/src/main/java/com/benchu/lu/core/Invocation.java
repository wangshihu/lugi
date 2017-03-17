package com.benchu.lu.core;

import com.google.common.base.MoreObjects;

/**
 * @author benchu
 * @version 2016/10/28.
 */
public class Invocation {
    private String service;
    private String method;
    private Object[] arguments;

    public Invocation() {
    }

    public Invocation(String service, String method, Object[] arguments) {
        this.service = service;
        this.method = method;
        this.arguments = arguments;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public Class[] argTypes(){
        Class[] argTypes=new Class[arguments.length];
        for(int i=0;i<argTypes.length;i++){
            argTypes[i]=arguments[i].getClass();
        }
        return argTypes;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                   .add("service", service)
                   .add("method", method)
                   .add("arguments", arguments)
                   .toString();
    }
}
