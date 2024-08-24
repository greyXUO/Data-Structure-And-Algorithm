/**
 * @author Huiyan Xu
 * course: 95771
 * Assignment Number: homework 2 treenode class.
 */
public class TreeNode {
    public double x;
    public double y;
    public String record;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(double x, double y, String record) {
        this.x = x;
        this.y = y;
        this.record = record;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    @Override
    public String toString() {
        return record;
    }
}