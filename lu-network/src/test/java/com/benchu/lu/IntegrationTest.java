package com.benchu.lu;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.benchu.lu.entity.response.VoteResponse;
import com.benchu.lu.network.Request;
import com.benchu.lu.network.Response;
import com.benchu.lu.network.Server;
import com.benchu.lu.network.message.LuMessage;
import com.benchu.lu.network.netty.client.NetException;
import com.benchu.lu.network.netty.client.Sender;

/**
 * @author benchu
 * @version 2016/11/2.
 */
public class IntegrationTest extends AbstractTest {
    private Server server;
    private Sender sender;

    @Before
    public void before() {
        super.before();
        server = context.getTransport().getServer(msgRouter);
        server.listen(defaultPort);
        sender = context.getSenderFactory().newSender(new InetSocketAddress("127.0.0.1", defaultPort));

    }

    @Test
    public void testNormal() throws ExecutionException, InterruptedException {
        LuMessage<Request> message = getLuMessage(22);
        CompletableFuture<Response> future = sender.send(message.getValue());
        check(future, (r, throwable) -> {
            VoteResponse response = (VoteResponse) r;
            Assert.assertEquals(response.getReplayNum(), 22);
            Assert.assertEquals(response.getStatus(), 0);
            Assert.assertEquals(response.getCause(), null);
            return true;
        });
    }

    @Test
    public void testServerHandleTimeout() throws InterruptedException, ExecutionException {
        LuMessage<Request> message = getLuMessage(-1);
        CompletableFuture<Response> future = sender.send(message.getValue());
        check(future, (response, throwable) -> {
            Assert.assertTrue(throwable.getCause() instanceof NetException);
            NetException e = (NetException) throwable.getCause();
            Assert.assertNull(response);
            Assert.assertEquals(e.code(), NetException.Code.SERVERHANDLE_TIMEOUT);
            return true;
        });
    }

    @Test
    public void testServerHandleError() throws InterruptedException {
        LuMessage<Request> message = getLuMessage(-2);
        CompletableFuture<Response> future = sender.send(message.getValue());
        check(future, (response, throwable) -> {
            Assert.assertTrue(throwable.getCause() instanceof NetException);
            NetException e = (NetException) throwable.getCause();
            e.printStackTrace();
            Assert.assertNull(response);
            Assert.assertEquals(e.code(), NetException.Code.SERVERHANDLE_ERROR);
            return true;
        });
    }

    private void check(CompletableFuture<Response> future, BiFunction<Response, Throwable, Boolean> consumer)
        throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        AtomicReference<Boolean> result = new AtomicReference<>();
        future.whenComplete((response, throwable) -> {
            try {
                result.set(consumer.apply(response, throwable));
            } finally {
                semaphore.release();
            }
        });
        semaphore.acquire();
        Assert.assertTrue(result.get());
    }

    @After
    public void after() {
        server.close();
        sender.close();
    }
}
