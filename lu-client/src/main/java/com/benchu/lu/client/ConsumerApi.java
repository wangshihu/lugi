package com.benchu.lu.client;

import com.benchu.lu.LuLaunch;
import com.benchu.lu.core.entity.ReferConfig;
import com.benchu.lu.core.event.LuEventDispatcher;
import com.benchu.lu.core.event.ReferEvent;

/**
 * @author benchu
 * @version 2017/3/13.
 */
public class ConsumerApi {
    public static <T> T getService(Class<T> clazz) {
        LuLaunch.getInstance().launch();
        ReferEvent event = new ReferEvent(new ReferConfig<>(clazz));
        return LuEventDispatcher.dispatch(event);
    }
}
