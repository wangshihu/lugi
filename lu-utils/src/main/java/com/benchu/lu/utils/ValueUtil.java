package com.benchu.lu.utils;

import java.util.function.Function;

/**
 * @author benchu
 * @version 2017/3/10.
 */
public class ValueUtil {

    public static <T> T getNotNullValue(T value, T defualt) {
        return value != null ? value : defualt;
    }

    public static <T, R> R doNotNull(T value, Function<T, R> function) {
        if (value == null) {
            return null;
        }
        return function.apply(value);
    }

}
