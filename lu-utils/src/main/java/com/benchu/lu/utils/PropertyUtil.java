package com.benchu.lu.utils;

import java.lang.reflect.Type;
import java.util.Properties;

/**
 * @author benchu
 * @version 2017/3/10.
 */
public class PropertyUtil {

    public static Long getLong(Properties properties, String key) {
        String value = properties.getProperty(key);
        return ValueUtil.doNotNull(value, Long::parseLong);
    }

    public static Integer getInt(Properties properties, String key) {
        String value = properties.getProperty(key);
        return ValueUtil.doNotNull(value, Integer::parseInt);
    }

    public static Object getValueByType(Properties properties, String key, Type type) {
        String name = type.getTypeName();
        switch (name) {
            case "long":
                return getLong(properties, key);
            case "int":
                return getInt(properties, key);
        }
        throw new UnsupportedOperationException("xxxx");
    }



}
