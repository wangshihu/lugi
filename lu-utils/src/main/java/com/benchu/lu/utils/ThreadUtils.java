package com.benchu.lu.utils;

import java.util.concurrent.TimeUnit;

/**
 * @author benchu
 * @version 2016/11/1.
 */
public class ThreadUtils {
    public static void sleepQuitely(long mills) {
        try {
            TimeUnit.MILLISECONDS.sleep(mills);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
