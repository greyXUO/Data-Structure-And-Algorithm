/**
 * @author Huiyan Xu
 * course: 95771
 * Assignment Number: homework 2 neighbor class.
 */
public class Neighbor {
    /**
     *  a pointer into the 2-d tree.
     */
    private TreeNode neighbor;
    /**
     * distance between node and neighbor.
     */
    private double distance;

    public Neighbor(TreeNode node, TreeNode neighbor) {
        this.neighbor = neighbor;
        double xDiff = node.getX() - neighbor.getX();
        double yDiff = node.getY() - neighbor.getY();
        this.distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }
    /**
     * get the distance of neighbor.
     * @return a double.
     */
    public double getDistance() {
        return distance;
    }
    /**
     * get the neighbor reference.
     * @return a treenode.
     */
    public TreeNode getNode() {
        return neighbor;
    }
    @Override
    public String toString() {
        return neighbor.record;
    }
}
