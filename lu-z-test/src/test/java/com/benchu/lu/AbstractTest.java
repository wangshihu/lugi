package com.benchu.lu;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

/**
 * @author benchu
 * @version 2017/3/10.
 */
public class AbstractTest {
    public static int defaultPort = 30300;
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 100, 60, TimeUnit.SECONDS,new LinkedBlockingQueue<>
                                                                                               (100));
        AtomicInteger counter = new AtomicInteger();
        for (int i = 0; i < 20000; i++) {
            executor.execute(() -> {
                try {
                    System.out.println(counter.incrementAndGet());
                    Thread.sleep(222222222);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(22222222);
    }

    @Test
    public void test() throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture future  =new CompletableFuture();
        future.get(100, TimeUnit.SECONDS);
    }
}
