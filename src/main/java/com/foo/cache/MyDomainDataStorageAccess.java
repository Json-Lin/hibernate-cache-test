package com.foo.cache;

import org.hibernate.cache.spi.support.DomainDataStorageAccess;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

/**
 * @author JasonLin
 * @version V1.0
 * @date 2019/3/11
 */
public class MyDomainDataStorageAccess implements DomainDataStorageAccess {
    private Cache cache;

    public MyDomainDataStorageAccess(Cache cache) {
        this.cache = cache;
    }

    @Override
    public Object getFromCache(Object key, SharedSessionContractImplementor session) {
        return cache.get(key);
    }

    @Override
    public void putIntoCache(Object key, Object value, SharedSessionContractImplementor session) {
        cache.put(key, value);
    }

    @Override
    public boolean contains(Object key) {
        return cache.contains(key);
    }

    @Override
    public void evictData() {
        cache.clear();
    }

    @Override
    public void evictData(Object key) {
        cache.remove(key);
    }

    @Override
    public void release() {
        cache = null;
    }
}
