package com.benchu.lu.core.event;

import com.benchu.lu.core.entity.ReferConfig;

/**
 * @author benchu
 * @version 2017/3/13.
 */
public class ReferEvent implements LuEvent {
    private ReferConfig config;

    public ReferEvent() {
    }

    public ReferEvent(ReferConfig config) {
        this.config = config;
    }

    public void setConfig(ReferConfig config) {
        this.config = config;
    }

    public ReferConfig getConfig() {
        return config;
    }
}
