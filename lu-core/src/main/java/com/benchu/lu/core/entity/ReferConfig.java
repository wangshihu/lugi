package com.benchu.lu.core.entity;

/**
 * @author benchu
 * @version 2017/3/13.
 */
public class ReferConfig<T> {
    private Class<T> service;

    public ReferConfig(Class<T> service) {
        this.service = service;
    }

    public Class<T> getService() {
        return service;
    }
}
