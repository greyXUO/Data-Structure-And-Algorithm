/**
 * @author Huiyan Xu
 * course: 95771
 * Assignment Number: homework 2 stack class.
 */
public class Stack<T> {
    /**
     * nested class for node in linkedlist structure.
     */
    private static class StackNode<T> {
        private T data;
        private StackNode<T> next;

        public StackNode(T data) {
            this.data = data;
        }
    }
    /**
     * the head node of the stack.
     */
    private StackNode<T> top;
    /**
     * initial the empty stack.
     */
    public Stack() {
        top = null;
    }
    /**
     * store a data with stacknode.
     * @param data the tree node.
     */
    public void push(T data) {
        StackNode<T> newNode = new StackNode<>(data);
        newNode.next = top;
        top = newNode;
    }
    /**
     * pop the data that get in the stack most recently.
     * @return the most recent object.
     */
    public T pop() {
        if (top == null) {
            return null;
        }
        T data = top.data;
        top = top.next;
        return data;
    }
    /**
     * get the most recent data without deleting it.
     * @return the most recent object.
     */
    public T peek() {
        return (top == null) ? null : top.data;
    }
    /**
     * check if the stack is empty.
     * @return a boolean.
     */
    public boolean isEmpty() {
        return top == null;
    }
    
}
