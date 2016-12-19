package com.benchu.lu.netty.server;

import java.net.SocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Test;

import com.benchu.lu.AbstractTest;
import com.benchu.lu.entity.response.VoteResponse;
import com.benchu.lu.network.MsgHandlerContext;
import com.benchu.lu.network.Request;
import com.benchu.lu.network.Response;
import com.benchu.lu.network.message.LuMessage;
import com.benchu.lu.network.message.SerializeType;
import com.benchu.lu.network.netty.client.NetException;
import com.benchu.lu.network.protocol.ErrorResponse;

/**
 * @author benchu
 * @version 2016/10/31.
 */
public class ServerMsgRouterTest extends AbstractTest {

    @Test
    public void testNormal() throws InterruptedException {
        LuMessage<Request> message = getLuMessage(22);
        LuMessage<Response> result = getResult(message);
        VoteResponse response = (VoteResponse) checkAndReturn(result);
        Assert.assertEquals(response.getReplayNum(), 22);
        Assert.assertEquals(response.getStatus(), 0);
        Assert.assertEquals(response.getCause(), null);
    }

    @Test
    public void testTimeout() throws InterruptedException, ExecutionException {
        LuMessage<Request> message = getLuMessage(-1);
        LuMessage<Response> result = getResult(message);
        ErrorResponse response = (ErrorResponse) checkAndReturn(result);
        Assert.assertEquals(response.getStatus(), NetException.Code.SERVERHANDLE_TIMEOUT.intValue());
        Assert.assertEquals(response.getCause().getClass(), TimeoutException.class);
    }

    @Test
    public void testError() throws InterruptedException {
        LuMessage<Request> message = getLuMessage(-2);
        LuMessage<Response> result = getResult(message);
        ErrorResponse response = (ErrorResponse) checkAndReturn(result);
        Assert.assertEquals(response.getStatus(), NetException.Code.SERVERHANDLE_ERROR.intValue());
        Assert.assertEquals(response.getCause().getClass(), ExecutionException.class);
    }

    private Response checkAndReturn(LuMessage<Response> result) {
        Assert.assertEquals(result.getRequestId(), 999);
        Assert.assertNotNull(result.getMessageType());
        Assert.assertEquals(result.getSerializeType(), SerializeType.FAST_JSON);
        return result.getValue();
    }

    private LuMessage<Response> getResult(LuMessage<Request> message) throws InterruptedException {
        AtomicReference<LuMessage<Response>> reference = new AtomicReference<>();
        Semaphore semaphore = new Semaphore(0);
        msgRouter.messageReceived(message, new MsgHandlerContext<LuMessage<Response>>() {
            @Override
            public void respond(LuMessage<Response> resp) {
                reference.set(resp);
                semaphore.release();
            }

            @Override
            public SocketAddress remoteAddress() {
                return null;
            }
        });
        semaphore.acquire();
        return reference.get();
    }

}
