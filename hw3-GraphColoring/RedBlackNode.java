/**
* @author Huiyan (Zoey) Xu
* andrew id: huiyanx
* course: 95771
* Assignment Number: homework 3 red black tree node.
*/
public class RedBlackNode {
    /**
     * Constant representing the color black.
     */
    public static final int BLACK = 0;

    /**
     * Constant representing the color red.
     */
    public static final int RED = 1;

    public String courseName;
    public int value;
    private int color;
    private RedBlackNode p;
    private RedBlackNode lc;
    private RedBlackNode rc;

    /**
     * Constructs a RedBlackNode with the specified data, color, parent, left child, and right child.
     * @param c the course name.
     * @param v the given integer.
     * @param color The color of the node, either RED or BLACK.
     * @param p The parent node.
     * @param lc The left child node.
     * @param rc The right child node.
     */
    public RedBlackNode(String c, int v,  int color, RedBlackNode p, RedBlackNode lc, RedBlackNode rc) {
        this.courseName = c;
        this.value = v;
        this.color = color;
        this.p = p;
        this.lc = lc;
        this.rc = rc;
    }

    /**
     * Retrieves the color of the node.
     * @return The color of the node.
     */
    public int getColor() {
        return color;
    }

    /**
     * Retrieves the data stored in the node.
     * @return The data stored in the node.
     */
    public String getCourse() {
        return courseName;
    }

    /**
     * Retrieves the parent node.
     * @return The parent node.
     */
    public RedBlackNode getP() {
        return p;
    }

    /**
     * Retrieves the left child node.
     * @return The left child node.
     */
    public RedBlackNode getLc() {
        return lc;
    }

    /**
     * Retrieves the right child node.
     * @return The right child node.
     */
    public RedBlackNode getRc() {
        return rc;
    }

    /**
     * Sets the color of the node.
     * @param color The color to be set.
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Sets the parent of the node.
     * @param p the parent pointer.
     */
    public void setP(RedBlackNode p) {
        this.p = p;
    }

    /**
     * Sets the left child node.
     * @param n The node to be set as the left child.
     */
    public void setLc(RedBlackNode n) {
        this.lc = n;
    }

    /**
     * Sets the right child node.
     * @param n The node to be set as the right child.
     */
    public void setRc(RedBlackNode n) {
        this.rc = n;
    }

    /**
     * Returns the string representation of the data in the node.
     * @return The string representation of the data in the node.
     */
    @Override
    public String toString() {
        return courseName + " " + value;
    }
}

