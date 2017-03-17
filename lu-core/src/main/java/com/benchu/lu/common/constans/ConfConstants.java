package com.benchu.lu.common.constans;

/**
 * @author benchu
 * @version 2017/3/10.
 */
public interface ConfConstants {
    //网络事件派发线程数
    String KEY_BOSS_THREADS = "lu.netty.boss.Threads";
    int DEFAULT_BOSS_THREADS = 1;

    String KEY_WORKER_THREADS = "lu.netty.worker.Threads";
    int DEFAULT_WORKER_THREADS = Runtime.getRuntime().availableProcessors();

    //连接超时时间
    String KEY_CONNECTION_TIMEOUT = "lu.network.connectionTimeout";
    int DEFAULT_CONNECTION_TIMEOUT = 1000;

    //客户端请求超时时间
    String KEY_REQUEST_TIMEOUT = "lu.client.requestTimeout";
    long DEFAULT_REQUEST_TIMEOUT = 5000;

    //服务端操作超时时间
    String KEY_SERVERHANDLE_TIMEOUT = "lu.server.HandleTimeout";
    long DEFAULT_SERVERHANDLE_TIMEOUT = 2000;

    //server executor 最大线程池大小
    String KEY_SERVER_THREADNUM_MAX = "lu.server.threadNum.max";
    int DEFAULT_SERVER_THREADNUM_MAX = 1000;

    //server executor 初始化线程池大小
    String KEY_SERVER_THREADNUM_CORE = "lu.server.threadNum.core";
    int DEFAULT_SERVER_THREADNUM_CORE= 50;


    //server port
    String KEY_SERVER_PORT = "lu.server.port";
    int DEFAULT_SERVER_PORT= 3030;

}
