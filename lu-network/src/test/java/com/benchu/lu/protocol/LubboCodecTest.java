package com.benchu.lu.protocol;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.benchu.lu.entity.Son;
import com.benchu.lu.network.message.LuMessage;
import com.benchu.lu.network.message.MessageType;
import com.benchu.lu.network.message.SerializeType;
import com.benchu.lu.network.serialize.LubboCodec;
import com.benchu.lu.network.serialize.MessageTypeManager;
import com.benchu.lu.network.serialize.SerializationFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;

/**
 * @author benchu
 * @version 16/9/14.
 */
public class LubboCodecTest {
    private LubboCodec lubboCodec = new LubboCodec();
    private long requestId = 123L;
    private String appName = "testApp";
    private Object[] arguments = new Object[] {1, "22"};
    MessageTypeManager messageTypeManager = new MessageTypeManager();
    @Before
    public void before() {
        messageTypeManager.put(new MessageType<>(-1,Son.class));
        lubboCodec.setSerializationFactory(new SerializationFactory());
        lubboCodec.setMessageTypeManager(messageTypeManager);
    }

    @Test
    public void decodeTest() {
        LuMessage<Son> message = lubboCodec.decoder(encodeTest());
        assertEquals(message.getMessageType().getClazz(),Son.class);
        assertEquals(message.getRequestId(), requestId);
        assertEquals(message.getSerializeType(), SerializeType.FAST_JSON);
        Son invocation =  message.getValue();
        Assert.assertEquals(invocation.getName(), appName);
        Object[] args = invocation.getArguments();
        Assert.assertEquals(args.length, 2);
        Assert.assertEquals(args[0], 1);
        Assert.assertEquals(args[1], "22");
    }

    public ByteBuf encodeTest() {
        ByteBuf out = UnpooledByteBufAllocator.DEFAULT.buffer();
        LuMessage<Son> message = new LuMessage<>();
        message.setRequestId(requestId);
        message.setMessageType(messageTypeManager.get(Son.class));
        message.setSerializeType(SerializeType.FAST_JSON);
        Son invocation = new Son();
        invocation.setName(appName);
        invocation.setArguments(arguments);
        message.setValue(invocation);
        lubboCodec.encode(message, out);
        return out;
    }
}
