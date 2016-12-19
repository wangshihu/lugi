package com.benchu.lu.utils;

import java.util.Map;
import java.util.function.Supplier;

import com.google.common.base.Preconditions;

/**
 * @author benchu
 * @version 16/9/30.
 */
public class MapUtils {

    public static  <K, V> V putIfNull(Map<K, V> map, K key, Supplier<V> loader) {
        V v = map.get(key);
        if (v == null) {
            v = loader.get();
            Preconditions.checkNotNull(v,"Supplier get value cannot be null");
            map.putIfAbsent(key, v);
            return v;
        }
        return v;
    }

}
