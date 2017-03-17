package com.benchu.lu.common.constans;

import java.nio.charset.Charset;

/**
 * @author benchu
 * @version 16/9/12.
 */
public interface CommonConstants {

    Charset DEFAULT_CHARSET = Charset.forName("utf-8");

    String BOSS_THREAD_NAME_PREFIX = "netty-boss";

    String WORKER_THREAD_NAME_PREFIX = "netty-worker";

}
