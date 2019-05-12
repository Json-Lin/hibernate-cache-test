package com.foo.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author JasonLin
 * @version V1.0
 * @date 2019/3/12
 */
public class Cache {

    private final Map<Object, Object> cache = new ConcurrentHashMap<>();

    public Object get(Object key) {
        return cache.get(key);
    }

    public boolean contains(Object key) {
        return cache.containsKey(key);
    }

    public void clear() {
        cache.clear();
    }

    public void remove(Object key) {
        cache.remove(key);
    }

    public void put(Object key, Object value) {
        cache.put(key, value);
    }

    @Override
    public String toString() {
        return "Cache{" +
                "cache=" + cache +
                '}';
    }
}
