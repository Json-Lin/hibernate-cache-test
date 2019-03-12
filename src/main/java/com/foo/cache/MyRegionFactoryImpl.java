package com.foo.cache;

import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cache.cfg.spi.DomainDataRegionBuildingContext;
import org.hibernate.cache.cfg.spi.DomainDataRegionConfig;
import org.hibernate.cache.spi.support.DomainDataStorageAccess;
import org.hibernate.cache.spi.support.RegionFactoryTemplate;
import org.hibernate.cache.spi.support.StorageAccess;
import org.hibernate.engine.spi.SessionFactoryImplementor;

import java.util.Map;

/**
 * RegionFactory实现类
 */
public class MyRegionFactoryImpl extends RegionFactoryTemplate {
    private static final long serialVersionUID = -401479360060731148L;

    private volatile CacheManager cacheManager;

    @Override
    protected StorageAccess createQueryResultsRegionStorageAccess(String regionName, SessionFactoryImplementor sessionFactory) {
        return new MyDomainDataStorageAccess(getOrCreateCache(regionName));
    }

    private Cache getOrCreateCache(String regionName) {
        Cache cache = cacheManager.getCache(regionName);
        if (cache == null) {
            return createCache(regionName);
        }
        return cache;
    }

    private Cache createCache(String regionName) {
        cacheManager.addCache(regionName);
        return cacheManager.getCache(regionName);
    }

    @Override
    protected StorageAccess createTimestampsRegionStorageAccess(String regionName, SessionFactoryImplementor sessionFactory) {
        return new MyDomainDataStorageAccess(getOrCreateCache(regionName));
    }

    /**
     * 该方法必须覆盖RegionFactoryTemplate的方法，RegionFactoryTemplate默认实现是抛出异常
     *
     * @param regionConfig
     * @param buildingContext
     * @return
     */
    @Override
    protected DomainDataStorageAccess createDomainDataStorageAccess(DomainDataRegionConfig regionConfig, DomainDataRegionBuildingContext buildingContext) {
        return new MyDomainDataStorageAccess(getOrCreateCache(regionConfig.getRegionName()));
    }

    @Override
    protected void prepareForUse(SessionFactoryOptions settings, Map configValues) {
        this.cacheManager = new CacheManager();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println(cacheManager);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }

    @Override
    protected void releaseFromUse() {
        cacheManager = null;
    }
}