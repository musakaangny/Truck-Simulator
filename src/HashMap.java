/**
 * A simple implementation of a generic HashMap using separate chaining
 * with linked lists for collision resolution.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class HashMap<K, V> {

    /**
     * An entry in the hash map, representing a key-value pair.
     */
    static class Entry<K, V> {
        K key; // The key of the entry
        V value; // The value associated with the key
        Entry<K, V> next; // Reference to the next entry in the chain (for collisions)

        /**
         * Constructs a new Entry with the specified key and value.
         *
         * @param key   the key of the entry
         * @param value the value associated with the key
         */
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private LinkedList<Entry<K, V>>[] table; // The array of linked lists (buckets)
    private int capacity; // The capacity of the table
    private int size; // The number of key-value pairs in the map
    private final float loadFactor = 0.75f; // The load factor threshold for resizing

    /**
     * Constructs a new HashMap with an initial capacity of 16.
     */
    public HashMap() {
        capacity = 16; // Initial size of the array
        table = new LinkedList[capacity];
        size = 0;
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>();
        }
    }

    /**
     * Computes the index for a given key using its hash code.
     *
     * @param key the key to be hashed
     * @return the index in the table
     */
    private int hash(K key) {
        return Math.abs(key.hashCode() % capacity);
    }

    /**
     * Adds a key-value pair to the map. If the key already exists, its value is updated.
     *
     * @param key   the key to be added or updated
     * @param value the value to be associated with the key
     */
    public void put(K key, V value) {
        int index = hash(key);
        LinkedList<Entry<K, V>> entries = table[index];

        // Check if the key already exists and update the value
        for (Entry<K, V> entry : entries) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        // If key doesn't exist, add a new entry
        entries.add(new Entry<>(key, value));
        size++;

        // Check if resizing is needed
        if (size >= capacity * loadFactor) {
            resize();
        }
    }

    /**
     * Retrieves the value associated with a given key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the key, or null if the key is not found
     */
    public V get(K key) {
        int index = hash(key);
        LinkedList<Entry<K, V>> entries = table[index];

        for (Entry<K, V> entry : entries) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null; // Key not found
    }

    /**
     * Removes the key-value pair associated with the specified key from the map.
     *
     * @param key the key to be removed
     */
    public void remove(K key) {
        int index = hash(key);
        LinkedList<Entry<K, V>> entries = table[index];

        // Iterate and remove the entry if found
        for (Entry<K, V> entry : entries) {
            if (entry.key.equals(key)) {
                entries.remove(entry);
                size--;
                return;
            }
        }
    }

    /**
     * Checks if the map contains a mapping for the specified key.
     *
     * @param key the key whose presence in the map is to be tested
     * @return true if the map contains a mapping for the specified key, false otherwise
     */
    public boolean containsKey(K key) {
        int index = hash(key);
        LinkedList<Entry<K, V>> entries = table[index];

        for (Entry<K, V> entry : entries) {
            if (entry.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Resizes the table when the number of key-value pairs exceeds the load factor threshold.
     * The capacity of the table is doubled, and all entries are rehashed.
     */
    private void resize() {
        int newCapacity = capacity * 2;
        LinkedList<Entry<K, V>>[] newTable = new LinkedList[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            newTable[i] = new LinkedList<>();
        }

        // Rehash all entries and put them into the new table
        for (int i = 0; i < capacity; i++) {
            for (Entry<K, V> entry : table[i]) {
                int newIndex = Math.abs(entry.key.hashCode() % newCapacity);
                newTable[newIndex].add(new Entry<>(entry.key, entry.value));
            }
        }

        table = newTable;
        capacity = newCapacity;
    }

    /**
     * Returns the number of key-value pairs in the map.
     *
     * @return the number of key-value pairs in the map
     */
    public int size() {
        return size;
    }
}
