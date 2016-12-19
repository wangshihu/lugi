package com.benchu.lu.network.netty.client;

import java.util.HashMap;
import java.util.Map;

/**
 * @author benchu
 * @version 2016/10/31.
 */
public class NetException extends Exception {
    private Code code;

    public NetException(Code code, String msg) {
        super(msg);
        this.code = code;
    }

    public NetException(int status, Throwable e) {
        super(e);
        this.code = Code.findByIntValue(status);
    }

    public Code code() {
        return code;
    }

    public enum Code {
        OK(0),
        CONNECTIONLOSS(-1),
        REQUESTTIMEOUT(-2),
        RUNTIMEERROR(-3),
        SERVERHANDLE_TIMEOUT(-4),
        SERVERHANDLE_ERROR(-5);

        private static Map<Integer, Code> intValueMap = new HashMap<>();

        static {
            for (Code code : Code.values()) {
                intValueMap.put(code.intValue(), code);
            }
        }

        public static Code findByIntValue(int value) {
            return intValueMap.get(value);
        }

        int code;

        Code(int code) {
            this.code = code;
        }

        public int intValue() {
            return code;
        }
    }
}
