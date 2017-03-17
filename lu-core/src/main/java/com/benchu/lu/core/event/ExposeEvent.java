package com.benchu.lu.core.event;

/**
 * @author benchu
 * @version 2017/3/10.
 */
public class ExposeEvent implements LuEvent{
    private Object targetBean;

    public ExposeEvent(Object targetBean) {
        this.targetBean = targetBean;
    }

    public Object getTargetBean() {
        return targetBean;
    }
}
