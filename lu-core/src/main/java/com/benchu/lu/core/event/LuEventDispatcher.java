package com.benchu.lu.core.event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.base.Preconditions;

/**
 * @author benchu
 * @version 2017/3/10.
 */
public class LuEventDispatcher {
    private static Map<Class<? extends LuEvent>, EventListener> listenerMap = new ConcurrentHashMap<>();

    public static <T extends LuEvent, O> void register(Class<T> clazz, EventListener<T, O> listener) {
        listenerMap.put(clazz, listener);
    }

    public static <T extends LuEvent, O> O dispatch(T event) {
        EventListener<T, O> listener = listenerMap.get(event.getClass());
        Preconditions.checkNotNull(listener, "event:" + event.getClass() + ",has not listener");
        return listener.onEvent(event);
    }
}

