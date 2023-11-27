package ru.clevertec.bank.cache.impl;

import java.util.HashMap;
import java.util.Map;
import ru.clevertec.bank.cache.Cache;
import ru.clevertec.bank.jdbc.PropertiesManager;

public class LRUCache<K, V> implements Cache<K, V> {

    private class Node {

        Node prev;
        Node next;
        K key;
        V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.prev = null;
            this.next = null;
        }

    }

    private static final int DEFAULT_CAPACITY = Integer.parseInt(
        PropertiesManager.getProperty("capacity"));
    private final int capacity;
    private final Map<K, Node> cache;
    private final Node head;
    private final Node tail;

    public LRUCache() {
        this.capacity = DEFAULT_CAPACITY;
        this.cache = new HashMap<>();
        this.head = new Node(null, null);
        this.tail = new Node(null, null);
        head.next = tail;
        tail.prev = head;
    }

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.head = new Node(null, null);
        this.tail = new Node(null, null);
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public V get(K key) {
        Node node = cache.get(key);
        if (node == null) {
            return null;
        }
        moveToTail(node);
        return node.value;
    }

    @Override
    public void put(K key, V value) {
        Node node = cache.get(key);
        if (node != null) {
            node.value = value;
            moveToTail(node);
        } else {
            if (cache.size() >= capacity) {
                removeNode(head.next);
            }
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addToTail(newNode);
        }
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
    }

    private void moveToTail(Node node) {
        removeNode(node);
        addToTail(node);
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        cache.remove(node.key);
    }

    private void addToTail(Node node) {
        node.prev = tail.prev;
        tail.prev = node;
        node.prev.next = node;
        node.next = tail;
        cache.put(node.key, node);
    }

}

