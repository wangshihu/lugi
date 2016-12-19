package com.benchu.lu;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executors;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.benchu.lu.entity.VoteRequest;
import com.benchu.lu.entity.response.VoteResponse;
import com.benchu.lu.handler.VoteMsgHandler;
import com.benchu.lu.network.LuContext;
import com.benchu.lu.network.Request;
import com.benchu.lu.network.message.LuMessage;
import com.benchu.lu.network.message.MessageType;
import com.benchu.lu.network.netty.server.ServerMsgRouter;
import com.benchu.lu.network.serialize.MessageTypeManager;

/**
 * @author benchu
 * @version 2016/10/28.
 */
public class AbstractTest {
    public static int defaultPort = 3030;

    protected long requestId = 999;
    protected Random random = new Random(47);

    protected static LuContext context;
    protected static MessageTypeManager messageTypeManager;
    protected static ServerMsgRouter msgRouter = new ServerMsgRouter();

    @BeforeClass
    public static void beforeClass() {
        context = LuContext.builder().create();
        messageTypeManager = context.getMessageTypeManager();
        initMessageType();
        msgRouter.setServerHandleTimeout(500);
        msgRouter.setMessageTypeManager(messageTypeManager);
        msgRouter.setExecutorService(Executors.newCachedThreadPool());
    }

    @Before
    public void before() {

    }

    private static void initMessageType() {
        messageTypeManager.put(new MessageType<>(1, VoteRequest.class));
        messageTypeManager.put(new MessageType<>(-1, VoteResponse.class));
        messageTypeManager.putHandler(VoteRequest.class, new VoteMsgHandler());
    }

    protected LuMessage<Request> getLuMessage(long requestId, long senderNum) {
        LuMessage<Request> message = new LuMessage<>();
        message.setRequestId(requestId);
        message.setValue(new VoteRequest(12, senderNum));
        message.setMessageType(messageTypeManager.checkAndGet(VoteRequest.class));
        return message;
    }

    protected LuMessage<Request> getLuMessage(long senderNum) {
        return getLuMessage(requestId, senderNum);
    }

    @AfterClass
    public static void afterClass() throws IOException {
        context.getTransport().close();
    }

}