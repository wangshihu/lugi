package com.benchu.lu.network;

/**
 * @author benchu
 * @version on 15/11/1.
 */
public final class LuConstants {
    /**
     * 私有构造函数
     */
    private LuConstants() {
    }

    /**
     * 网络事件派发线程数
     */
    public static final int DEFAULT_BOSS_THREADS = 1;

    public static final int DEFAULT_WORKER_THREADS = Runtime.getRuntime().availableProcessors();

    public static final String BOSS_THREAD_NAME_PREFIX = "netty-boss";
    public static final String WORKER_THREAD_NAME_PREFIX = "netty-worker";

    public static final int DEFAULT_CONNECT_TIMEOUT_MILLS = 1000;

    public static final int IDEL_TIME_OUT = 80;

    public static final int DEFAULT_READ_IDEL_TIME_OUT = 40;

    public static final int DEFAULT_WRITE_IDEL_TIME_OUT = 20;

    /**
     * 空余读,写默认时间.
     */
    public static int DEFAULT_READTIMEOUT = 0;

    public static int DEFAULT_WRITETIMEOUT = 10;

    public static int DEFAULT_IDLETIMEOUT = 0;
    //服务端操作超时时间
    public static long DEFAULT_SERVERHANDLE_TIMEOUT = 2000;
    //客户端请求超时时间
    public static long DEFAULT_REQUEST_TIMEOUT = 5000;
    //线程池大小
    public static int DEFAULT_EXECUTOR_THREADNUM = 500;

}
