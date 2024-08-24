import java.util.NoSuchElementException;

/**
 * This class define a linked list that will be used to create a hash table.
 * The data would be a (String, String) pair. Be sure to cast the type when storing or withdrawing.
 * @author Xiao Shi
 * @AndrewID xiaoshi
 **/
public class LinkedList {
    private Node head;

    private int size; // Calculate size for linked list to simplify the calculation of the hashset size.

    public LinkedList() {
        head = null;
    }

    /**
     * Insert a new data into the linked list.
     * @param key the new key to insert.
     * @param value corresponding value.
     **/
    public void insert(String key, String value) {
        Node newNode = new Node(key, value,null);
        size = size + 1;
        if (head == null) {
            head = newNode;
            return;
        }
        Node cur = head;
        while(cur.next != null) {
            cur = cur.next;
        }
        cur.next = newNode;
    }

    /**
     * Check if an element has already in the linked list.
     * This method is created for hashset checking.
     * @param key the key to check
     * @return a boolean indicator. If the item is already in the linked list, return true, false otherwise.
     * */
    public boolean contain(String key) {
        Node cur = head;
        if (cur == null) {
            return false;
        }
        while(cur != null) {
            if (cur.key.equals(key)) {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    /**
     * This method return the value given key.
     * @param key the key to search
     * @return value the corresponding key.
     * */
    public String valueOf(String key) {
        Node cur = head;
        if (cur == null) {
            throw new NoSuchElementException("The list is empty.");
        }
        while(cur != null) {
            if (cur.key.equals(key)) {
                return cur.value;
            }
            cur = cur.next;
        }
        throw new NoSuchElementException("Key not found in the list.");
    }

    public int getSize() {
        return size;
    }

    /**
     * A nested class defines the node of linked list.
     * */
    private static class Node {
        private String key;
        private String value;
        private Node next;

        Node(String k, String v, Node n) {
            key = k;
            value = v;
            next = n;
        }
    }

}
