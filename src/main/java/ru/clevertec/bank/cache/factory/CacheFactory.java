package ru.clevertec.bank.cache.factory;

import ru.clevertec.bank.cache.Cache;

public interface CacheFactory<K, V> {
    Cache<K, V> createCache();
}