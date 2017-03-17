package com.benchu.lu.common.exception;

/**
 * @author benchu
 * @version 2017/3/10.
 */
public class LuException  extends RuntimeException{
    public LuException(String message) {
        super(message);
    }

    public LuException(String message, Throwable cause) {
        super(message, cause);
    }
}
