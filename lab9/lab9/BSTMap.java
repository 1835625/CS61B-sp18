package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>, Iterable<K> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        int cmp = p.key.compareTo(key);
        if (cmp < 0) {
            return getHelper(key, p.right);
        } else if (cmp > 0) {
            return getHelper(key, p.left);
        } else {
            return p.value;
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size += 1;
            return new Node(key, value);
        }
        int cmp = p.key.compareTo(key);
        if (cmp < 0) {
            p.right =  putHelper(key, value, p.right);
        } else if (cmp > 0) {
            p.left = putHelper(key, value, p.left);
        } else {
            p.value = value;
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return keySetHelper(root);
    }

    /* Returns a Set of the keys contained in the subtree rooted in p. */
    private Set<K> keySetHelper(Node p) {
        if (p != null) {
            Set<K> keys = new HashSet<>();
            keys.addAll(keySetHelper(p.left));
            keys.addAll(keySetHelper(p.right));
            keys.add(p.key);
            return keys;
        } else {
            return new HashSet<>();
        }
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        Node ptr = root;
        while (ptr != null) {
            int cmp = ptr.key.compareTo(key);
            if (cmp < 0) {
                ptr = ptr.right;
            } else if (cmp > 0) {
                ptr = ptr.left;
            } else {
                break;
            }
        }

        if (ptr == null) {
            return null;
        }

        V retVal = ptr.value;
        removeHelper(ptr);
        size -= 1;
        return retVal;
    }

    /* Removes Node p from the tree. */
    private void removeHelper(Node p) {
        if (p == null) {
            return;
        }
        Node parent = parentOf(p);
        if (p.left == null && p.right == null) { // p has no children.
            if (parent == null) {
                root = null;
            }else if (parent.left == p) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        } else if (p.left == null) { // p has a right child.
            if (parent == null) {
                root = p.right;
            }else if (parent.left == p) {
                parent.left = p.right;
            } else {
                parent.right = p.right;
            }
        } else if (p.right == null) { // p has a left child.
            if (parent == null) {
                root = p.left;
            }else if (parent.left == p) {
                parent.left = p.left;
            } else {
                parent.right = p.left;
            }
        } else { // p has two children.
            Node biggestNSmaller = getBiggestNSmaller(p);
            removeHelper(biggestNSmaller);
            p.key = biggestNSmaller.key;
            p.value = biggestNSmaller.value;
        }
    }

    /* Returns the biggest node that is smaller tha p. */
    private Node getBiggestNSmaller(Node p) {
        Node ptr = p.left;
        while (ptr.right != null) {
            ptr = ptr.right;
        }
        return ptr;
    }

    /* Returns the parent node of p. */
    private Node parentOf(Node p) {
        if (p == root) {
            return null;
        }
        Node ptr = root;
        while (ptr.left != p && ptr.right != p) {
            int cmp = ptr.key.compareTo(p.key);
            if (cmp < 0) {
                ptr = ptr.right;
            } else {
                ptr = ptr.left;
            }
        }
        return ptr;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        Node ptr = root;
        while (ptr != null) {
            int cmp = ptr.key.compareTo(key);
            if (cmp < 0) {
                ptr = ptr.right;
            } else if (cmp > 0) {
                ptr = ptr.left;
            } else {
                break;
            }
        }

        if (ptr == null) {
            return null;
        }

        if (!Objects.equals(ptr.value, value)) {
            return null;
        }
        return remove(key);
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
