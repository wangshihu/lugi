package com.benchu.lu.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Assert;
import org.junit.Test;

import com.benchu.lu.AbstractTest;
import com.benchu.lu.entity.VoteRequest;
import com.benchu.lu.network.Server;
import com.benchu.lu.network.message.LuMessage;
import com.benchu.lu.network.netty.client.Sender;

/**
 * @author benchu
 * @version 2016/10/31.
 */
public class SenderTest extends AbstractTest {

    @Test
    public void testNormalSender() throws InterruptedException, IOException {
        int num = 100;
        int senderNum = 10;
        CountDownLatch latch = new CountDownLatch(num);
        List<Long> resultList = new ArrayList<>();
        Server server = context.getTransport().getServer((message, ctx) -> {
            latch.countDown();
            LuMessage<VoteRequest> luMessage = (LuMessage<VoteRequest>) message;
            resultList.add(luMessage.getValue().getId());
        });
        server.listen(3030);
        Thread.sleep(200000000);
        List<Sender> senders = new ArrayList<>();
        for (int i = 0; i < senderNum; i++) {
            senders.add(context.getSenderFactory().newSender(new InetSocketAddress("127.0" + ".0.1", 3030)));
        }
        for (int i = 0; i < num; i++) {
            int senderIndex = random.nextInt(senderNum);
            senders.get(senderIndex).send(new VoteRequest(i, senderIndex));
        }
        server.close();
        latch.await();
        resultList.sort((o1, o2) -> (int) (o1-o2));
        for (long i = 0; i < 100; i++) {
            Assert.assertEquals(i,(long)resultList.get((int) i));
        }
        Assert.assertEquals(resultList.size(),100);
    }

    @Test
    public void test() {
    }

}
