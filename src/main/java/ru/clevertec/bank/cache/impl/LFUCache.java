package ru.clevertec.bank.cache.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import ru.clevertec.bank.cache.Cache;
import ru.clevertec.bank.jdbc.PropertiesManager;

public class LFUCache<K, V> implements Cache<K, V> {

    private static final int CAPACITY = Integer.parseInt(PropertiesManager.getProperty("capacity"));
    private final Map<K, HitRate> cache = new HashMap<>();
    private final Map<K, V> KV = new HashMap<>();


    public void put(K key, V value) {
        V v = KV.get(key);
        if (v == null) {
            if (cache.size() == CAPACITY) {
                K k = getKickedKey();
                KV.remove(k);
                cache.remove(k);
            }
            cache.put(key, new HitRate(key, 1, System.nanoTime()));
        } else {
            HitRate hitRate = cache.get(key);
            hitRate.hitCount += 1;
            hitRate.lastTime = System.nanoTime();
        }
        KV.put(key, value);
    }

    public V get(K key) {
        V v = KV.get(key);
        if (v != null) {
            HitRate hitRate = cache.get(key);
            hitRate.hitCount += 1;
            hitRate.lastTime = System.nanoTime();
            return v;
        }
        return null;
    }

    @Override
    public void remove(K key) {
        KV.remove(key);
    }

    private K getKickedKey() {
        HitRate min = Collections.min(cache.values());
        return min.key;
    }

    class HitRate implements Comparable<HitRate> {

        K key;
        Integer hitCount;
        Long lastTime;

        public HitRate(K key, Integer hitCount, Long lastTime) {
            this.key = key;
            this.hitCount = hitCount;
            this.lastTime = lastTime;
        }

        public int compareTo(HitRate o) {
            int hr = hitCount.compareTo(o.hitCount);
            return hr != 0 ? hr : lastTime.compareTo(o.lastTime);
        }

    }

}
