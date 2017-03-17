package com.benchu.lu.network;

import org.junit.Assert;
import org.junit.Test;

import com.benchu.lu.core.Invocation;
import com.benchu.lu.core.message.LuMessage;
import com.benchu.lu.core.message.MessageType;
import com.benchu.lu.network.serialize.LubboCodec;
import com.benchu.lu.test.entity.Son;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;

/**
 * @author benchu
 * @version 16/9/14.
 */
public class LubboCodecTest {
    private LubboCodec lubboCodec = new LubboCodec();
    private long requestId = 123L;
    private String sonName = "testApp";
    private Object[] arguments = new Object[] { "22"};

    @Test
    public void decodeTest() {
        LuMessage<Invocation> message = lubboCodec.decoder(encodeTest());
        Assert.assertEquals(message.getMessageType().getClazz(),Invocation.class);
        Assert.assertEquals(message.getRequestId(), requestId);
        Invocation invocation =  message.getValue();
        Assert.assertEquals(invocation.getMethod(), "testMethod");
        Assert.assertEquals(invocation.getService(), "testService");
        Object[] args = invocation.getArguments();
        Assert.assertEquals(args[0],1L);
        Assert.assertEquals(args[1],"hello");
        Assert.assertEquals(args[1].getClass(),String.class);
        Son son = (Son) args[2];
        Assert.assertEquals(son.getName(), sonName);
        args = son.getArguments();
        Assert.assertEquals(args[0], 1);
        Assert.assertEquals(args[1], "22");
    }

    public ByteBuf encodeTest() {
        ByteBuf out = UnpooledByteBufAllocator.DEFAULT.buffer();
        LuMessage<Invocation> message = new LuMessage<>();
        message.setRequestId(requestId);
        message.setMessageType(MessageType.invocation);
        message.setValue(getInvocation());
        lubboCodec.encode(message, out);
        return out;
    }


    public Invocation getInvocation(){
        Invocation invocation = new Invocation();
        Son son = new Son();
        son.setName(sonName);
        son.setArguments(arguments);
        Object[] arguments = new Object[3];
        arguments[0] = 1L;
        arguments[1] = "hello";
        arguments[2] = son;
        invocation.setMethod("testMethod");
        invocation.setService("testService");
        invocation.setArguments(arguments);
        return invocation;

    }
}
