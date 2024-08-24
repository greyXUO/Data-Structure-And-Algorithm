/**
 * @author Huiyan (Zoey) Xu
 * andrew id: huiyanx
 * course: 95771
 * Assignment Number: homework 4 TSP.
 * Min heap class.
 */
class Vertex {
    int index;
    int key;
    Vertex(int i, int k) {
        this.index = i;
        this.key = k;
    }
}
public class Min {
    private int size;
    private int[] items;

    public Min(int capacity) {
        this.size = 0;
        this.items = new int[capacity];
    }

    /*
     * if heap is empty return true. 
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * get the smallesr value from the heap
     * @return root value
     */
    public int deleteMin() {
        if (size == 0) throw new IllegalStateException();
        int item = items[0]; // get value of root
        items[0] = items[size - 1];  // bring the last value to the root
        size--;
        heapifyDown();
        return item;
    }

    /**
     * insert a new value into the heap
     * @param item an int.
     */
    public void insert(int item) {
        items[size] = item;
        size++;
        heapifyUp();
    }

    /**
     * make sure the recent insert value is at the right position.
     */
    private void heapifyUp() {
        // the recent insert index
        int index = size - 1;
        // compare with its parent
        while (hasParent(index) && parent(index) > items[index]) {
            // lift the recent index if index larger than it
            swap(getParentIndex(index), index);
            // update the index position
            index = getParentIndex(index);
            // continue until the parent is no longer larger than the index we have
        }
    }

    /**
     * after deleting the smallest value, make sure the min heap rules are kept.
     */
    private void heapifyDown() {
        // start from the root.
        int index = 0;
        // while root has left child
        while (hasLeftChild(index)) {
            int smallerChildIndex = getLeftChildIndex(index);
            // get the smaller one from two children if exists
            if (hasRightChild(index) && rightChild(index) < leftChild(index)) {
                smallerChildIndex = getRightChildIndex(index);
            }

            // if both children larger than root, finish
            if (items[index] < items[smallerChildIndex]) {
                break;
            } else {
                // else, bring up the smaller one
                swap(index, smallerChildIndex);
            }
            index = smallerChildIndex;
        }
    }

    /*
     * get the child/ parent index
     */
    private int getLeftChildIndex(int i) { 
        return 2 * i + 1; 
    }

    private int getRightChildIndex(int i) { 
        return 2 * i + 2; 
    }

    private int getParentIndex(int i) { 
        return (i - 1) / 2; 
    }
    /*
     * check if the current index has child / parent
     */
    private boolean hasLeftChild(int i) { 
        return getLeftChildIndex(i) < size; 
    }
    private boolean hasRightChild(int i) { 
        return getRightChildIndex(i) < size; 
    }
    private boolean hasParent(int i) { 
        return getParentIndex(i) >= 0; 
    }

    /*
     * get the value of its child / parent.
     */
    private int leftChild(int index) { 
        return items[getLeftChildIndex(index)]; 
    }
    private int rightChild(int index) { 
        return items[getRightChildIndex(index)]; 
    }
    private int parent(int index) { 
        return items[getParentIndex(index)]; 
    }

    /*
     * change the value of two positions in array
     */
    private void swap(int indexOne, int indexTwo) {
        int temp = items[indexOne];
        items[indexOne] = items[indexTwo];
        items[indexTwo] = temp;
    }
}
