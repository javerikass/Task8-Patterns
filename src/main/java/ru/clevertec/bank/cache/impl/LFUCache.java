package ru.clevertec.bank.cache.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import ru.clevertec.bank.cache.Cache;
import ru.clevertec.bank.jdbc.PropertiesManager;

public class LFUCache<K, V> implements Cache<K, V> {

    private static final int DEFAULT_CAPACITY = Integer.parseInt(
        PropertiesManager.getProperty("capacity"));
    private final int capacity;
    private final Map<K, HitRate> cache;
    private final Map<K, V> storage;

    public LFUCache() {
        this.capacity = DEFAULT_CAPACITY;
        this.cache = new HashMap<>(capacity);
        this.storage = new HashMap<>(capacity);
    }

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>(capacity);
        this.storage = new HashMap<>(capacity);
    }
    @Override
    public void put(K key, V value) {
        V v = storage.get(key);
        if (v == null) {
            if (cache.size() == capacity) {
                K kickedKey = getKickedKey();
                storage.remove(kickedKey);
                cache.remove(kickedKey);
            }
            cache.put(key, new HitRate(key, 1, System.nanoTime()));
        } else {
            HitRate hitRate = cache.get(key);
            hitRate.hitCount += 1;
            hitRate.lastTime = System.nanoTime();
        }
        storage.put(key, value);
    }

    @Override
    public V get(K key) {
        V value = storage.get(key);
        if (value != null) {
            HitRate hitRate = cache.get(key);
            hitRate.hitCount += 1;
            hitRate.lastTime = System.nanoTime();
            return value;
        }
        return null;
    }

    @Override
    public void remove(K key) {
        storage.remove(key);
        cache.remove(key);
    }

    private K getKickedKey() {
        if (cache.isEmpty()) {
            throw new IllegalStateException("Cache is empty");
        }
        HitRate min = Collections.min(cache.values());
        return min.key;
    }

    private class HitRate implements Comparable<HitRate> {

        K key;
        int hitCount;
        long lastTime;

        public HitRate(K key, int hitCount, long lastTime) {
            this.key = key;
            this.hitCount = hitCount;
            this.lastTime = lastTime;
        }

        public int compareTo(HitRate o) {
            int hitCountComparison = Integer.compare(hitCount, o.hitCount);
            if (hitCountComparison != 0) {
                return hitCountComparison;
            }
            return Long.compare(lastTime, o.lastTime);
        }
    }
}
