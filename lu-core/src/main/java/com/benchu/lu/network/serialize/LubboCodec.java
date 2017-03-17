package com.benchu.lu.network.serialize;

import com.benchu.lu.core.message.LuMessage;
import com.benchu.lu.core.message.MessageType;

import io.netty.buffer.ByteBuf;

/**
 * @author benchu
 * @version on 15/10/25.
 */
public class LubboCodec {
    private JsonSerialize serialize = new JsonSerialize();
    /**
     * 讲LuMessage解析成协议数组
     */
    public void encode(LuMessage message, ByteBuf out) {

        // 表示数据包总长的值包含该字段的长度，表示整体的总长度，[4,INT_MAX)
        int totalLength = 0;

        // ---common header---
        int totalLengthIndex = out.writerIndex();
        out.writeInt(totalLength);// 此处只做占位 待数据写入完毕后修改此处的值
        out.writeLong(message.getRequestId());
        out.writeByte((byte) message.getMessageType().getId());
        byte[] data = serialize.serialize(message.getValue());
        // 内容正文的contentLength不包含自身长度字段的长度，仅表示内容的长度，[0,INT_MAX)
        int dataLength = data.length;
        out.writeInt(dataLength);
        out.writeBytes(data);

        int endIndex = out.writerIndex();
        totalLength = endIndex - totalLengthIndex;
        out.setInt(totalLengthIndex, totalLength);// 修改totalLength的值
    }

    /**
     * 讲协议数组解析成LuMessage
     */
    public<E> LuMessage<E> decoder(ByteBuf in) {
        LuMessage<E> result = new LuMessage<>();
        //获得基本信息
        int totalLength = in.readInt();
        result.setRequestId(in.readLong());
        MessageType messageType= MessageType.findById(in.readByte());
        result.setMessageType(messageType);
        ///获得实际内容.
        int dataLength = in.readInt();
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        result.setValue((E) serialize.unSerialize(data, messageType.getClazz()));
        return result;
    }

}
