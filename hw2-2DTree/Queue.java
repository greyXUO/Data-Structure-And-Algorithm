/**
 * @author Huiyan Xu
 * course: 95771
 * Assignment Number: homework 2 queue class.
 */
public class Queue<T> {
    /**
     * nested class for node in linkedlist structure.
     */
    private static class QueueNode<T> {
        private T data;
        private QueueNode<T> next;

        public QueueNode(T data) {
            this.data = data;
        }
    }
    /**
     * the head node of the queue.
     */
    private QueueNode<T> head;
    /**
     * the rear node of the queue.
     */
    private QueueNode<T> rear;
    /**
     * initial the empty queue.
     */
    public Queue() {
        head = null;
        rear = null;
    }
    /**
     * store a data with queuenode.
     * @param data an object, usually the tree node.
     */
    public void add(T data) {
        QueueNode<T> newNode = new QueueNode<T>(data);
        if (rear != null) {
           rear.next = newNode; 
        }
        rear = newNode;
        if (head == null) {
            head = rear;
        }
    }
    /**
     * return the data that get in the queue first.
     * @return the object.
     */
    public T remove() {
        if (head == null) {
            return null;
        }
        T data = head.data;
        head = head.next;
        return data;
    }
    /**
     * get the least recent data without deleting it.
     * @return the least recent object.
     */
    public T peek() {
        return (head == null) ? null : head.data;
    }
    /**
     * check if the queue is empty.
     * @return a boolean.
     */
    public boolean isEmpty() {
        return head == null;
    }
    
}
