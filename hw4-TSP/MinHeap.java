/**
 * @author Huiyan (Zoey) Xu
 * andrew id: huiyanx
 * course: 95771
 * Assignment Number: homework 4 TSP.
 * Min heap class.
 * part of the code is from 17683.
 */
class Edge {
    public int start;
    public int end;
    private double distance;

    public Edge(int s, int e, double d) {
        this.start = s;
        this.end = e;
        this.distance = d;
    }

    @Override
    public String toString() {
        return "Edge {" +
        "start=" + start +
        ", end=" + end +
        ", distance=" + distance +
        '}';
    }

    public double getDistance() {
        return distance;
    }
}
public class MinHeap{
    /**
     * the min heap of edges
     */
    private Edge[] heap;
    /**
     * current num of edges in the heap.
     */
    public int currentSize;
    /**
     * initial the minheap with given size
     * @param initialCapacity an integer
     */
    public MinHeap(int initialCapacity) {
        heap = new Edge[initialCapacity];
        currentSize = 0;
    }

    /**
     * double the size of the heap.
     */
    public void doubleSize() {
        int curCapacity = heap.length;
        Edge[] newHeap = new Edge[curCapacity *2];
        for (int i = 0; i < curCapacity - 1; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    /**
     * insert an edge into the heap
     * @param edge 
     */
    public void insert(Edge edge) {
        // if heap not big enough
        if (currentSize == heap.length) {
            doubleSize();
        }
        // put the new edge at the end of array.
        heap[currentSize] = edge;
        // check heap order from the end.
        heapifyUp(currentSize);
        //increase size;
        currentSize++;
    }

    /**
     * check heap order from the end.
     * @param index the index of the child node
     */
    private void heapifyUp(int index) {
        Edge end = heap[index];
        int parent = (index - 1) / 2;
        // while the child is smaller than its parent
        while(index > 0 && heap[parent].getDistance() > end.getDistance()) {
            heap[index] = heap[parent];
            index = parent;
            parent = (index - 1) / 2;
        }
        heap[index] = end;
    }

    /**
     * get the shorest edge we have.
     * @return an edge
     */
    public Edge deleteMin() {
        // shortest edge is always the head
        Edge root = heap[0];
        currentSize--;
        // put the tail edge to the head
        heap[0] = heap[currentSize];
        // empty the tail position
        heap[currentSize] = null;
        // check minheap rules from the top
        heapifyDown(0);
        return root;
    }

    /**
     * check minheap rules from the top.
     * @param index always the top 0
     */
    private void heapifyDown(int index) {
        Edge top = heap[index];
        while (index < currentSize / 2) {
            int leftChild = index * 2 + 1;
            int rightChild = leftChild + 1;
            int smaller;
            if (heap[rightChild] != null && heap[leftChild].getDistance() > heap[rightChild].getDistance()) {
                smaller = rightChild;
            } else {
                smaller = leftChild;
            }
            if (heap[smaller].getDistance() >= top.getDistance()) {
                break;
            }
            heap[index] = heap[leftChild];
            index = smaller;
        }
        heap[index] = top;
    }
}