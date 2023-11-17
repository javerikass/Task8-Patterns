package ru.clevertec.bank.cache.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.bank.cache.Cache;

class LRUCacheTest {

    private Cache<String, Integer> cache;

    @BeforeEach
    void setUp() {
        cache = new LRUCache<>();
    }

    @Test
    void testPutAndGetShouldPutAnGetRightValue() {
        cache.put("key1", 1);
        cache.put("key2", 2);
        cache.put("key3", 3);

        assertEquals(1, cache.get("key1"));
        assertEquals(2, cache.get("key2"));
        assertEquals(3, cache.get("key3"));
    }

    @Test
    void testPutShouldDoEvictionWhenCacheOverflowed() {
        cache = new LRUCache<>(2);

        cache.put("key1", 1);
        cache.put("key2", 2);
        cache.put("key3", 3);

        assertNull(cache.get("key1"));
        assertEquals(2, cache.get("key2"));
        assertEquals(3, cache.get("key3"));
    }

    @Test
    void testGetShouldNotReturnValue() {
        assertNull(cache.get("nonExistentKey"));
    }

    @Test
    void testRemoveShouldDeleteElement() {
        cache.put("key1", 1);
        cache.put("key2", 2);

        cache.remove("key1");

        assertNull(cache.get("key1"));
        assertEquals(2, cache.get("key2"));
    }

}