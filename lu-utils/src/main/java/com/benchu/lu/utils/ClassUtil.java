package com.benchu.lu.utils;

/**
 * @author benchu
 * @version 2016/12/19.
 */
public class ClassUtil {
    public static String arrayToString(Class[] classes) {
        StringBuilder builder = new StringBuilder();
        for (Class clazz : classes) {
            builder.append(clazz.getName() + ",");
        }
        return builder.toString();
    }
}
