package com.little.pang.boring.code.serivce;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


/**
 * Created by jaky on 3/21/16.
 */
@Service
public class SpringCacheAnnotation {
    private static Logger logger = LoggerFactory.getLogger(SpringCacheAnnotation.class);

    @Cacheable(cacheNames = "simpleCacheImpl")
    public String load(String key) {
        logger.info("load for key {}", key);
        return String.format("value for key [%s]", key);
    }

    @CacheEvict(cacheNames = "simpleCacheImpl", key = "#key")
    public void evict(String key) {
        logger.info("evict for key {}", key);
    }

}
