/**
 * @author Huiyan (Zoey) Xu
 * andrew id: huiyanx
 * course: 95771
 * Assignment Number: homework 3 Singlylinkedlist class.
 */
class Node {
    String data;
    int value;
    Node next;

    public Node(String data, int value) {
        this.data = data;
        this.value = value;
        this.next = null;
    }
}

public class SinglyLinkedList {
    private Node head;
    private Node pointer;

    public SinglyLinkedList() {
        this.head = null;
    }

    public void add(String data, int value) {
        Node newNode = new Node(data, value);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public String getNthElement(int n) {
        Node current = head;
        int count = 0;

        while (current != null) {
            if (count == n) {
                return current.data;
            }
            count++;
            current = current.next;
        }

        return null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void reset() {
        pointer = head;
    }

    public int getNext() {
        int v = pointer.value;
        pointer = pointer.next;
        return v;
    }

    public void display() {
        Node current = head;
        while (current != null) {
            System.out.println(current.value + " -> " + current.data);
            current = current.next;
        }
    }
}
