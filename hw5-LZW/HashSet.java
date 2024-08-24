import java.util.NoSuchElementException;

/**
 * A class defines hashset.
 * Array-Linked list structure is using here.
 * A hash set shall not contain data already in the set.
 * @author Xiao Shi
 * @AndrewID xiaoshi
 * */
public class HashSet {
    private LinkedList[] base;

    private static final int arraySize = 127; // Given by the writeup. 127 is a prime number.

    public HashSet() {
        base = new LinkedList[arraySize];
        for (int i = 0; i < arraySize; i++) {
            base[i] = new LinkedList();
        }
    }

    /**
     * Insert a (key, value) pair into the hashset.
     * Repeated key is not allowed.
     * @param key the key to insert.
     * @param value the value to insert
     * */
    public void insert(String key, String value) {
        int index = hashIndex(key);
        if (!contain(key)) {
            base[index].insert(key, value);
        }
    }

    /**
     * Check if a key has already in the set.
     * @param key the key to check.
     * @return if the key is in the hash table, return true. Return false otherwise.
     * */
    public boolean contain(String key) {
        int index = hashIndex(key);
        return base[index].contain(key);
    }

    /**
     * Return the value given a key.
     * @param key the key to search
     * @throws NoSuchElementException if the key is not found
     **/
    public String valueOf(String key) {
        int index = hashIndex(key);
        if (contain(key)) {
            return base[index].valueOf(key);
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Get the size of the hashset. The result is total number of pairs stored in the set.
     * @return the total size of pairs.
     * */
    public int getSize() {
        int totalSize = 0;
        for (int i = 0; i < arraySize; i++) {
            totalSize = totalSize + base[i].getSize();
        }
        return totalSize;
    }

    /**
     * Private helper method firstly calculates the hash value of a key.
     * Then, finds the index of that key in the array.
     * @param key the key to hash.
     * @return the index of that key in the array.
     * */
    private int hashIndex (String key) {
        return Math.abs(key.hashCode()) % arraySize;
    }

}
