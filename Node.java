/**
 * Node class for SinglyLinkedList
 */
class Node {
    private Integer value;
    private String key;
    private Node nextNode;

    // Constructor
    public Node(Integer value, String key, Node nextNode) {
        this.value = value;
        this.key = key;
        this.nextNode = nextNode;
    }

    // Getters and setters
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    // Override toString method for printing node information
    @Override
    public String toString() {
        return "(" + value + ", " + key + ")";
    }
}
