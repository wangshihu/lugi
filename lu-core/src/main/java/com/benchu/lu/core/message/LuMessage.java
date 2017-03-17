package com.benchu.lu.core.message;

import com.google.common.base.MoreObjects;

/**
 * @author benchu
 * @version on 15/10/22.
 */
public class LuMessage<E> {
    private MessageType messageType;
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                   .add("messageType", messageType)
                   .add("requestId", requestId)
                   .add("value", value)
                   .toString();
    }
}
