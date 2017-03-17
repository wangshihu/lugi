package com.benchu.lu.core.message;

import java.util.HashMap;
import java.util.Map;

import com.benchu.lu.core.Invocation;
import com.benchu.lu.core.Result;

/**
 * @author benchu
 * @version on 15/10/21.
 */
public enum MessageType {
    invocation(1, Invocation.class), result(2, Result.class);

    private static Map<Integer, MessageType> idMap = new HashMap<>();

    static {
        for (MessageType messageType : MessageType.values()) {
            idMap.put(messageType.getId(), messageType);
        }
    }

    public static MessageType findById(int id) {
        return idMap.get(id);
    }
    private int id;
    private Class<?> clazz;

    MessageType(int id, Class clazz) {
        this.id = id;
        this.clazz = clazz;
    }

    public int getId() {
        return id;
    }

    public Class getClazz() {
        return clazz;
    }



}
