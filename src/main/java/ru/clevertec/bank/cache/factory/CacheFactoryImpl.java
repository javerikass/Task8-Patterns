package ru.clevertec.bank.cache.factory;

import ru.clevertec.bank.cache.Cache;
import ru.clevertec.bank.cache.impl.LFUCache;
import ru.clevertec.bank.cache.impl.LRUCache;
import ru.clevertec.bank.jdbc.PropertiesManager;

public class CacheFactoryImpl<K, V> implements CacheFactory<K, V> {

    private static final String ALGORITHM = PropertiesManager.getProperty("cache");

    @Override
    public Cache<K, V> createCache() {
        if (ALGORITHM != null) {
            if ("LRU".equalsIgnoreCase(ALGORITHM)) {
                return new LRUCache<>();
            } else if ("LFU".equalsIgnoreCase(ALGORITHM)) {
                return new LFUCache<>();
            }
        }
        throw new IllegalArgumentException("Invalid or unspecified cache algorithm");
    }

}