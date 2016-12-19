package com.benchu.lu.network.serialize;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.benchu.lu.network.message.MessageType;
import com.benchu.lu.network.netty.server.ServerMsgHandler;
import com.benchu.lu.network.protocol.ErrorResponse;
import com.google.common.base.Preconditions;

/**
 * @author benchu
 * @version 2016/10/28.
 */
public class MessageTypeManager {

    private Map<Integer, MessageType> idType = new ConcurrentHashMap<>();
    private Map<Class, MessageType> clazzType = new ConcurrentHashMap<>();
    private Map<MessageType, ServerMsgHandler> handlerMap = new ConcurrentHashMap<>();

    public MessageTypeManager() {
        put(new MessageType<>(Byte.MIN_VALUE, ErrorResponse.class));
    }

    public MessageType get(byte id) {
        return idType.get((int) id);
    }

    public MessageType get(Class clazz) {
        return clazzType.get(clazz);
    }

    public MessageType checkAndGet(Class clazz) {
        return checkNotNull("clazz:" + clazz + " cannot find messageType", get(clazz));
    }

    public MessageType checkAndGet(byte id) {
        return checkNotNull("id:" + id + " cannot find messageType", get(id));
    }

    private <T> T checkNotNull(String msg, T obj) {
        Preconditions.checkNotNull(obj, msg);
        return obj;
    }

    public synchronized <E> void put(MessageType<E> messageType) {
        int id = messageType.getId();
        Class clazz = messageType.getClazz();
        String msg = "id=" + id + ", clazz=" + clazz.getName() + ", has been put in MessageTypeManager";
        Preconditions.checkArgument(idType.get(id) == null && clazzType.get(clazz) == null, msg);
        idType.put(messageType.getId(), messageType);
        clazzType.put(messageType.getClazz(), messageType);
    }

    public ServerMsgHandler getHandler(MessageType messageType) {
        return handlerMap.get(messageType);
    }

    public ServerMsgHandler checkAndGetHandler(MessageType type) {
        return checkNotNull("class:" + type.getClazz().getName() + " meessage, doesn't has serverMsgHandler",
                            getHandler(type));
    }

    public void putHandler(Class clazz, ServerMsgHandler handler) {
        putHandler(get(clazz), handler);
    }

    public void putHandler(MessageType type, ServerMsgHandler handler) {
        handlerMap.put(type, handler);
    }
}
