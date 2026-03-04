package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    public MyHashMap(int size) {
        buckets = new ArrayMap[size];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int i = hash(key);
        if (this.buckets[i].containsKey(key)) {
            return this.buckets[i].get(key);
        } else {
            return null;
        }
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (value == null) {
            remove(key);
            return;
        }
        if (loadFactor() > MAX_LF) {
            resize(2 * buckets.length);
        }
        int i = hash(key);
        if (!buckets[i].containsKey(key)) {
            size += 1;
        }
        this.buckets[i].put(key, value);
    }

    private void resize(int size) {
        MyHashMap<K, V> tmp = new MyHashMap(size);
        for (int i = 0; i < this.buckets.length; i++) {
            for (K key : this.buckets[i]) {
                tmp.put(key, this.buckets[i].get(key));
            }
        }
        this.buckets = tmp.buckets;
        this.size = tmp.size;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return this.size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (int i = 0; i < buckets.length; i++) {
            for (K key : buckets[i]) {
                keys.add(key);
            }
        }
        return keys;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        int i = hash(key);
        if (buckets[i].containsKey(key)) {
            V retVal = buckets[i].get(key);
            buckets[i].remove(key);
            size -= 1;
            return retVal;
        } else {
            return null;
        }
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        int i = hash(key);
        if (buckets[i].containsKey(key)) {
            V val = buckets[i].get(key);
            if (val.equals(value)) {
                buckets[i].remove(key);
                size -= 1;
                return val;
            }
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
