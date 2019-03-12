package com.foo.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author JasonLin
 * @version V1.0
 * @date 2019/3/12
 */
public class CacheManager {
    private final Map<String, Cache> caches = new ConcurrentHashMap<>();

    public Cache getCache(String cacheName) {
        return caches.get(cacheName);
    }

    public void addCache(String cacheName, Cache cache) {
        caches.put(cacheName, cache);
    }

    public void addCache(String cacheName) {
        caches.put(cacheName, new Cache());
    }

    @Override
    public String toString() {
        return "CacheManager{" +
                "caches=" + caches +
                '}';
    }
}
