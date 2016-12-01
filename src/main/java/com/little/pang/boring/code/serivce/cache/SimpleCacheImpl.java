package com.little.pang.boring.code.serivce.cache;

import com.google.common.collect.Maps;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by jaky on 3/21/16.
 */
@Service("simpleCacheImpl231231231")
public class SimpleCacheImpl implements Cache {

    public Map<Object, String> memoryCache = Maps.newHashMap();



    @Override
    public String getName() {
        return "simpleCacheImpl";
    }

    @Override
    public Object getNativeCache() {
        return null;
    }

    @Override
    public ValueWrapper get(final Object o) {
        if (memoryCache.get(o) != null) {
            return new SimpleValueWrapper(memoryCache.get(o));
        }
        return null;
    }

    @Override
    public <T> T get(Object o, Class<T> tClass) {
        return null/*(T)memoryCache.get(o)*/;
    }

    @Override
    public void put(Object o, Object o2) {
        memoryCache.put(o, (String)o2);
    }

    @Override
    public ValueWrapper putIfAbsent(final Object o, Object o2) {
        if (memoryCache.get(o) == null) {
            memoryCache.put(o, (String)o2);

        }

        return new ValueWrapper() {
            @Override
            public Object get() {
                return memoryCache.get(o);
            }
        };
    }

    @Override
    public void evict(Object o) {
        memoryCache.remove(o);
    }

    @Override
    public void clear() {
        memoryCache.clear();
    }

}
