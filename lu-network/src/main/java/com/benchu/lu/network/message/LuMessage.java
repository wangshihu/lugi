package com.benchu.lu.network.message;

/**
 * @author benchu
 * @version on 15/10/22.
 */
public class LuMessage<E> {
    private MessageType messageType;
    private SerializeType serializeType  = SerializeType.FAST_JSON;
    private long requestId;
    private E value;

    public LuMessage() {
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public SerializeType getSerializeType() {
        return serializeType;
    }

    public void setSerializeType(SerializeType serializeType) {
        this.serializeType = serializeType;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }


    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

}
