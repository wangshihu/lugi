package com.benchu.lu.core.configuration;

import java.util.HashMap;
import java.util.Map;

import com.benchu.lu.common.constans.ConfConstants;

/**
 * @author benchu
 * @version 2017/3/10.
 */
public enum ConfEntity implements ConfConstants {
    none(null, null),
    bossThreads(KEY_BOSS_THREADS, DEFAULT_BOSS_THREADS),
    workerThreads(KEY_WORKER_THREADS, DEFAULT_WORKER_THREADS),
    connectionTimeout(KEY_CONNECTION_TIMEOUT, DEFAULT_CONNECTION_TIMEOUT),
    requestTimeout(KEY_REQUEST_TIMEOUT, DEFAULT_REQUEST_TIMEOUT),
    serverHandleTimeout(KEY_SERVERHANDLE_TIMEOUT, DEFAULT_SERVERHANDLE_TIMEOUT),
    serverMaxThreadNum(KEY_SERVER_THREADNUM_MAX,DEFAULT_SERVER_THREADNUM_MAX),
    serverCoreThreadNum(KEY_SERVER_THREADNUM_CORE,DEFAULT_SERVER_THREADNUM_CORE),
    port(KEY_SERVER_PORT,DEFAULT_SERVER_PORT)
    ;

    private static Map<String, ConfEntity> map = new HashMap<>();

    static {
        for (ConfEntity entity : ConfEntity.values()) {
            map.put(entity.name(), entity);
        }
    }

    public static ConfEntity getEntityByName(String name) {
        return map.get(name);
    }

    private final String key;
    private final Object defaultValue;

    public String getKey() {
        return key;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    ConfEntity(String key, Object defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

}
