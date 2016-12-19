package com.benchu.lu.network;

import static com.benchu.lu.network.LuConstants.DEFAULT_EXECUTOR_THREADNUM;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.benchu.lu.common.NamedThreadFactory;
import com.benchu.lu.network.netty.NettyCodecAdaptor;
import com.benchu.lu.network.netty.NettyNioTransport;
import com.benchu.lu.network.netty.client.SenderFactory;
import com.benchu.lu.network.netty.client.LuSenderFactory;
import com.benchu.lu.network.netty.client.ResponseMessageHandler;
import com.benchu.lu.network.netty.server.ServerMsgRouter;
import com.benchu.lu.network.serialize.LubboCodec;
import com.benchu.lu.network.serialize.MessageTypeManager;
import com.benchu.lu.network.serialize.SerializationFactory;

/**
 * @author benchu
 * @version 2016/10/30.
 */
public class LuContext {
    private LuContext() {

    }

    private Transport transport;
    private MessageTypeManager messageTypeManager;
    private SenderFactory senderFactory;
    private Client client;
    private ExecutorService executorService;
    private Server server;

    public Transport getTransport() {
        return transport;
    }

    public MessageTypeManager getMessageTypeManager() {
        return messageTypeManager;
    }

    public SenderFactory getSenderFactory() {
        return senderFactory;
    }

    public Client getClient() {
        return client;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public Server getServer() {
        return server;
    }

    public static Builder builder() {
        Builder builder = new Builder();
        builder.result = new LuContext();
        return builder;
    }

    public static class Builder {

        private int workerThreads = LuConstants.DEFAULT_WORKER_THREADS;
        private int bossThreads = LuConstants.DEFAULT_BOSS_THREADS;

        private long requestTimeout =  LuConstants.DEFAULT_REQUEST_TIMEOUT;
        private long serverHandleTimeout = LuConstants.DEFAULT_SERVERHANDLE_TIMEOUT;
        private ScheduledExecutorService executorService;
        private LuContext result;

        public Builder withRequestTimeout(long requestTimeout) {
            this.requestTimeout = requestTimeout;
            return this;
        }

        public Builder withServerHandleTimeout(long serverHandleTimeout) {
            this.serverHandleTimeout = serverHandleTimeout;
            return this;
        }

        public Builder withExecutorService(ScheduledExecutorService executorService) {
            this.executorService = executorService;
            return this;
        }

        private void ensure() {
            if (result == null) {
                this.result = new LuContext();
            }
            if (executorService == null) {
                this.executorService = new ScheduledThreadPoolExecutor(DEFAULT_EXECUTOR_THREADNUM,
                                                                       new NamedThreadFactory("lu-executor-pool-"));
                result.executorService = this.executorService;
            }

        }

        public LuContext create() {
            ensure();
            MessageTypeManager messageTypeManager = new MessageTypeManager();
            result.messageTypeManager = messageTypeManager;

            //serialize
            SerializationFactory serializationFactory = new SerializationFactory();
            LubboCodec codec = new LubboCodec();
            NettyCodecAdaptor adaptor = new NettyCodecAdaptor();
            codec.setMessageTypeManager(messageTypeManager).setSerializationFactory(serializationFactory);
            adaptor.setCodec(codec);
            //transport
            NettyNioTransport transport = new NettyNioTransport();
            transport.setAdaptor(adaptor);
            transport.setBossThreads(bossThreads);
            transport.setWorkerThreads(workerThreads);
            result.transport =transport;
                //client
            LuSenderFactory senderFactory = new LuSenderFactory();
            ResponseMessageHandler clientMsgHandler = new ResponseMessageHandler();
            clientMsgHandler.setExecutorService(executorService);
            clientMsgHandler.setRequestTimeout(requestTimeout);
            clientMsgHandler.init();
            result.client = result.transport.getClient(clientMsgHandler);
            senderFactory.setClient(result.client);
            senderFactory.setResponseSubscribe(clientMsgHandler);
            senderFactory.setMessageTypeManager(messageTypeManager);
            result.senderFactory = senderFactory;
            //server
            ServerMsgRouter serverMsgRouter = new ServerMsgRouter();
            serverMsgRouter.setMessageTypeManager(messageTypeManager);
            serverMsgRouter.setExecutorService(executorService);
            serverMsgRouter.setServerHandleTimeout(serverHandleTimeout);
            result.server = result.transport.getServer(serverMsgRouter);
            return result;
        }

    }

}
