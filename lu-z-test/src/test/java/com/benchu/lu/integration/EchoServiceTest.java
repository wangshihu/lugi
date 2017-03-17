package com.benchu.lu.integration;

import java.net.InetSocketAddress;

import org.junit.Assert;
import org.junit.Test;

import com.benchu.lu.LuLaunch;
import com.benchu.lu.client.ConsumerApi;
import com.benchu.lu.common.constans.ConfConstants;
import com.benchu.lu.server.ExposeApi;
import com.benchu.lu.test.impl.EchoServiceImpl;
import com.benchu.lu.test.service.EchoService;

/**
 * @author benchu
 * @version 2017/3/13.
 */
public class EchoServiceTest {
    @Test
    public void test() {
        ExposeApi.expose(new EchoServiceImpl());
        LuLaunch.instance.getContext()
            .setDirectory(clazz -> new InetSocketAddress("localhost", ConfConstants.DEFAULT_SERVER_PORT));
        EchoService service = ConsumerApi.getService(EchoService.class);
        Assert.assertEquals(service.echoString("test"), "test");
    }
}
