//package com.benchu.lu;
//
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ForkJoinPool;
//import java.util.concurrent.Future;
//import java.util.concurrent.TimeoutException;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//import org.junit.Test;
//
///**
// * @author benchu
// * @version 2016/10/31.
// */
//public class TTest {
//    @Test
//    public void test() throws InterruptedException {
//        CompletableFuture<Void> result = new CompletableFuture<>();
//        result.thenCompose(v -> CompletableFuture.supplyAsync(() -> {
//            System.out.println("compose");
//            sleep(2000);
//            return "123";
//        })).whenComplete((s, throwable) -> {
//            System.out.println(s);
//            throwable.printStackTrace();
//        });
//        System.out.println(result.isDone());
//        System.out.println(result.isCompletedExceptionally());
//        result.complete(null);
//        Thread.sleep(200000);
//    }
//
//    void sleep(long mill) {
//        try {
//            Thread.sleep(mill);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            System.out.println("中断");
//        }
//    }
//
//    @Test
//    public void test2() throws InterruptedException, TimeoutException, ExecutionException {
//        CompletableFuture<Date> result = CompletableFuture.supplyAsync(() -> {
//            sleep(1000);
//            return new Date();
//        }, Executors.newCachedThreadPool());
//
//        CompletableFuture<Long> timeFuture = result.thenCompose(date -> {
//            System.out.println(Thread.currentThread().getName());
//            sleep(1000);
//            CompletableFuture<Long> r = CompletableFuture.completedFuture(date.getTime());
//            return r;
//        });
//        //        timeFuture.get(500, TimeUnit.MILLISECONDS);
//        timeFuture.whenComplete((aLong, throwable) -> {
//            System.out.println("complete");
//            System.out.println("time:" + aLong);
//        });
//        System.out.println(ForkJoinPool.getCommonPoolParallelism());
//        Thread.sleep(200000);
//    }
//
//    class MyTask {
//        private final int duration;
//
//        public MyTask(int duration) {
//            this.duration = duration;
//        }
//
//        public int calculate() {
//            System.out.println(Thread.currentThread().getName());
//            try {
//                Thread.sleep(duration * 1000);
//            } catch (final InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            return duration;
//        }
//    }
//
//    List<MyTask> tasks = IntStream.range(0, 10).mapToObj(i -> new MyTask(1)).collect(Collectors.toList());
//    CompletableFuture<Date> result = CompletableFuture.supplyAsync(() -> {
//        sleep(1000);
////        System.out.println("result1:" + Thread.currentThread().isInterrupted());
//        return new Date(1000);
//    }, Executors.newCachedThreadPool());
//    CompletableFuture<String> result3 = CompletableFuture.supplyAsync(() -> {
//        sleep(2000);
////        System.out.println("result3:" + Thread.currentThread().isInterrupted());
//        return "123";
//    });
//
//    @Test
//    public void test3() throws InterruptedException, ExecutionException {
//
//        CompletableFuture<String> g = result.thenCombine(result3, (date, msg) -> {
//            System.out.println("combine");
//            sleep(1000);
//            return msg + date.getTime();
//        });
//        g.whenComplete((s, throwable) -> {
//            System.out.println(s);
//            throwable.printStackTrace();
//
//        });
//        //        g.cancel(false);
//        Thread.sleep(20000);
//    }
//
//    @Test
//    public void tes2t() throws ExecutionException, InterruptedException {
////        result.cancel(true);
//        CompletableFuture<?> all = CompletableFuture.allOf(result, result3);
//        all.whenComplete((o, e) -> {
//            System.out.println(o);
//            e.printStackTrace();
//        });
//        Thread.sleep(20000);
//
//    }
//
//    @Test
//    public void test33() {
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        for (int i = 0; i < 100000; i++) {
//            final int finalI = i;
//
//            CompletableFuture<String> completableFuture = wrapFuture(executorService.submit(() -> {
//                System.out.println("运行:" + finalI);
//                sleep(2000000000);
//                System.out.println("完成:" + finalI);
//                return "123";
//            }));
//            sleep(500);
//            completableFuture.cancel(true);
//        }
//    }
//
//    @Test
//    public void test34() throws InterruptedException {
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        CompletableFuture<String> completableFuture = wrapFuture(executorService.submit(() -> {
//            System.out.println("运行:" );
//            System.out.println("完成:");
//            return "123";
//        }));
//        completableFuture.thenAccept(s-> {
//            System.out.println("accept:"+s);
//        });
////        completableFuture.cancel(true);
//        System.out.println(completableFuture.isDone());
//        System.out.println(completableFuture.isCancelled());
//        System.out.println(completableFuture.isCompletedExceptionally());
//        Thread.sleep(200000);
//    }
//    public static <T> CompletableFuture<T> wrapFuture(Future<T> future) {
//
//        return new CompletableFuture<T>(){
//            public boolean cancel(boolean mayInterruptIfRunning) {
//                super.cancel(mayInterruptIfRunning);
//                return future.cancel(mayInterruptIfRunning);
//            }
//        };
//    }
//
//
//}
