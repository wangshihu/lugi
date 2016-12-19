/*
 * Copyright 2011-2014 Mogujie Co.Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.benchu.lu.network.netty.client;

import java.net.InetSocketAddress;

import com.benchu.lu.network.Client;
import com.benchu.lu.network.Connection;
import com.benchu.lu.network.Request;
import com.benchu.lu.network.Response;
import com.benchu.lu.network.message.LuMessage;
import com.benchu.lu.network.serialize.MessageTypeManager;

/**
 * @author benchu
 */
public class LuSenderFactory implements SenderFactory {

    protected Client client;

    protected ResponseSubscribe<LuMessage<Request>, LuMessage<Response>> responseSubscribe;
    private MessageTypeManager messageTypeManager;
    @Override
    public Sender newSender(InetSocketAddress address) {
        Connection connection = client.connect(address);
        LuSender exchanger = new LuSender();
        exchanger.setConnection(connection);
        exchanger.setResponseSubscribe(responseSubscribe);
        exchanger.setMessageTypeManager(messageTypeManager);
        return exchanger;
    }


    public void setClient(Client client) {
        this.client = client;
    }

    public void setResponseSubscribe(ResponseSubscribe<LuMessage<Request>, LuMessage<Response>> responseSubscribe) {
        this.responseSubscribe = responseSubscribe;
    }

    public void setMessageTypeManager(MessageTypeManager messageTypeManager) {
        this.messageTypeManager = messageTypeManager;
    }
}
