package com.benchu.lu.server;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.benchu.lu.LuContext;
import com.benchu.lu.common.NamedThreadFactory;
import com.benchu.lu.core.configuration.Configuration;
import com.benchu.lu.core.event.ExposeEvent;
import com.benchu.lu.core.event.LuEventDispatcher;
import com.benchu.lu.network.Server;
import com.benchu.lu.server.expose.ExposeInvokerManager;
import com.benchu.lu.server.network.ServerMsgRouter;
import com.benchu.lu.spi.Module;

/**
 * @author benchu
 * @version 2017/3/9.
 */
public class ServerModule implements Module {
    private static Logger logger = LoggerFactory.getLogger(ServerModule.class);

    private ExposeInvokerManager exposeInvokerManager;
    private Server server;
    private ExecutorService executorService;

    @Override
    public void init(LuContext context) {
        logger.info("lu server module init....");
        Configuration configuration = context.getConfiguration();
        initExecutorService(configuration);
        exposeInvokerManager = new ExposeInvokerManager();
        exposeInvokerManager.setDeleteInvokerFactory(context.getProxyFactory());
        ServerMsgRouter serverMsgRouter = new ServerMsgRouter();
        serverMsgRouter.setInvokerManager(exposeInvokerManager);
        serverMsgRouter.setServerHandleTimeout(configuration.getServerHandleTimeout());
        serverMsgRouter.setExecutorService(executorService);
        //server

        server = context.getTransport().getServer(serverMsgRouter);
        server.listen(configuration.getPort());
        LuEventDispatcher.register(ExposeEvent.class, event -> exposeInvokerManager.newInvoker(event.getTargetBean()));
        logger.info("lu server module init completed...");

    }

    private void initExecutorService(Configuration configuration) {
        int core = configuration.getServerCoreThreadNum();
        int max = configuration.getServerMaxThreadNum();
        ThreadFactory f = new NamedThreadFactory("lu-server-executor-pool-");
        executorService = new ThreadPoolExecutor(core, max, 60, TimeUnit.SECONDS, new SynchronousQueue<>(), f);
    }


    @Override
    public void close() throws IOException {
        server.close();
        executorService.shutdownNow();
    }
}
