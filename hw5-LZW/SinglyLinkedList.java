/**
 * @author Huiyan (Zoey) Xu
 * andrew id: huiyanx
 * course: 95771
 * Assignment Number: homework 5
 */
public class SinglyLinkedList {
    private Node head;
    private Node tail;
    public int size;

    public SinglyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * check if the ll is empty
     * @return a boolean
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * add pair value into the ll
     * @param value a 12-bit chunk
     * @param key a string
     */
    public void addNodeEnd(Integer value, String key) {
        Node temp = new Node(value, key, null);
        size++;
        if (head == null) { //empty
            head = temp;
            tail = temp;
        } else {
            tail.setNextNode(temp);
            tail = temp;
        }
    }

    /**
     * get the size of this ll.
     * @return an int
     */
    public int getSize() {
        return size;
    }

    public void printList() {
        Node current = head;
        if (head == null) {
            return;
        }
        while (current != null) {
            System.out.println(current);
            current = current.getNextNode();
        }
    }

    /**
     * if the key exists in the ll
     * @param key a string
     * @return a boolean
     */
    public boolean containsKey(String key) {
        Node current = head;
        while (current != null) {
            if (current.getKey() != null && current.getKey().equals(key)) {
                return true;
            }
            current = current.getNextNode();
        }
        return false;
    }

    /**
     * get the value of given string in the ll.
     * @param key a string
     * @return an int; if not found return -1
     */
    public int getValue(String key) {
        Node current = head;
        while (current != null) {
            if (current.getKey() != null && current.getKey().equals(key)) {
                return current.getValue();
            }
            current = current.getNextNode();
        }
        //System.out.println("no such string");
        return -1;
    }

    // Testing function
    public static void main(String[] args) {
        SinglyLinkedList list = new SinglyLinkedList();

        list.addNodeEnd(1, "A");
        list.addNodeEnd(2, "B");
        list.addNodeEnd(3, "C");

        System.out.println("Is the list empty? " + list.isEmpty()); // false

        System.out.println("Size of the list: " + list.getSize()); // 3
        System.out.println("Does the list contain key 'D'? " + list.containsKey("D")); // false
        System.out.println("Does the list contain key 'C'? " + list.containsKey("C")); // false
    }
}
