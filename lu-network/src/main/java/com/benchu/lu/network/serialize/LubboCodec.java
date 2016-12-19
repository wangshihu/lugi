package com.benchu.lu.network.serialize;

import java.nio.charset.Charset;

import com.benchu.lu.network.message.LuMessage;

import io.netty.buffer.ByteBuf;

/**
 * @author benchu
 * @version on 15/10/25.
 */
public class LubboCodec {
    private SerializationFactory serializationFactory;
    private MessageTypeManager messageTypeManager;

    /**
     * 讲LuMessage解析成协议数组
     */
    public void encode(LuMessage message, ByteBuf out) {
        // 内容正文的contentLength不包含自身长度字段的长度，仅表示内容的长度，[0,INT_MAX)
        int dataLength = 0;
        // 表示数据包总长的值包含该字段的长度，表示整体的总长度，[4,INT_MAX)
        int totalLength = 0;

        // ---common header---
        int totalLengthIndex = out.writerIndex();
        out.writeInt(totalLength);// 此处只做占位 待数据写入完毕后修改此处的值
        out.writeByte((byte) message.getMessageType().getId());
        out.writeByte((byte) message.getSerializeType().getId());
        out.writeLong(message.getRequestId());
        byte[] data = serializationFactory.serialize(message.getSerializeType(), message.getValue());
        out.writeInt(data.length);

        out.writeBytes(data);
        int endIndex = out.writerIndex();
        totalLength = endIndex - totalLengthIndex;
        out.setInt(totalLengthIndex, totalLength);// 修改totalLength的值
    }

    /**
     * 讲协议数组解析成LuMessage
     */
    public<E> LuMessage<E> decoder(ByteBuf in) {
        System.out.println(in.capacity());
        byte[] bytes = new byte[in.capacity()];
        in.readBytes(bytes);
        System.out.println(new String(bytes, Charset.forName("utf-8")));
        LuMessage<E> result = new LuMessage<>();
        //获得基本信息
//        int totalLength = in.readInt();
//        MessageType messageType = messageTypeManager.checkAndGet(in.readByte());
//        result.setMessageType(messageType);
//        SerializeType serializeType = SerializeType.get(in.readByte());
//        result.setSerializeType(serializeType);
//        result.setRequestId(in.readLong());
//        ///获得实际内容.
//        int dataLength = in.readInt();
//
//        byte[] data = new byte[dataLength];
//        in.readBytes(data);
//        Object value = serializationFactory.unSerialize(serializeType, data, messageType.getClazz());
//        result.setValue((E) value);
        return result;
    }

    public LubboCodec setSerializationFactory(SerializationFactory serializationFactory) {
        this.serializationFactory = serializationFactory;
        return this;
    }

    public LubboCodec setMessageTypeManager(MessageTypeManager messageTypeManager) {
        this.messageTypeManager = messageTypeManager;
        return this;
    }
}
