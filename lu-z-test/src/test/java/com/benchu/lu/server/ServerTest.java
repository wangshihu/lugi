package com.benchu.lu.server;

import org.junit.Test;

import com.benchu.lu.test.impl.EchoServiceImpl;

/**
 * @author benchu
 * @version 2017/3/10.
 */
public class ServerTest {
    @Test
    public void test() throws InterruptedException {
        ExposeApi.expose(new EchoServiceImpl());
        Thread.sleep(200000);
    }
}
