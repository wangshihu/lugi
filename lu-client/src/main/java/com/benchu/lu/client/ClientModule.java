package com.benchu.lu.client;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.benchu.lu.LuContext;
import com.benchu.lu.client.invoker.ClientInvokerFactory;
import com.benchu.lu.client.network.ResponseMessageHandler;
import com.benchu.lu.core.configuration.Configuration;
import com.benchu.lu.core.event.EventListener;
import com.benchu.lu.core.event.LuEventDispatcher;
import com.benchu.lu.core.event.ReferEvent;
import com.benchu.lu.network.Client;
import com.benchu.lu.spi.Module;

/**
 * @author benchu
 * @version 2017/3/9.
 */
public class ClientModule implements Module {
    private static Logger logger = LoggerFactory.getLogger(ClientModule.class);

    private ClientInvokerFactory clientInvokerFactory;
    private ReferenceServiceFactory referenceServiceFactory;

    public void init(LuContext context) {
        logger.info("lu client module init....");
        Configuration configuration = context.getConfiguration();
        ResponseMessageHandler clientMsgHandler = new ResponseMessageHandler();
        clientMsgHandler.setRequestTimeout(configuration.getRequestTimeout());
        clientMsgHandler.init();

        this.clientInvokerFactory = new ClientInvokerFactory();
        Client client = context.getTransport().getClient(clientMsgHandler);
        clientInvokerFactory.setClient(client);
        clientInvokerFactory.setResponseSubscribe(clientMsgHandler);

        //referenceService
        referenceServiceFactory = new ReferenceServiceFactory();
        referenceServiceFactory.setInvokerFactory(clientInvokerFactory);
        referenceServiceFactory.setContext(context);
        LuEventDispatcher.register(ReferEvent.class, new EventListener<ReferEvent, Object>() {
            @Override
            public Object onEvent(ReferEvent event) {
                return referenceServiceFactory.getService(event.getConfig());
            }
        });

        logger.info("lu client module init completed....");

    }

    @Override
    public void close() throws IOException {
    }
}
