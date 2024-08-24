/**
 * @author Huiyan (Zoey) Xu
 * andrew id: huiyanx
 * course: 95771
 * Assignment Number: homework 3 red black tree class.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *  Reb Black Trees
 A red-black tree is a binary search tree with an extra bit of storage per node.
 The extra bit represents the color of the node. It's either red or black.
 Each node contains the fields: color, key, left, right, and p. Any nil
 pointers are regarded as pointers to external nodes (leaves) and key bearing
 nodes are considered as internal nodes of the tree.
 */
public class RedBlackTree {
    public RedBlackNode root;
    public int count;
    public SinglyLinkedList courses;
    public static final RedBlackNode nil = new RedBlackNode(null,-1, 0, null, null, null);

    public RedBlackTree() {
        root = nil;
        count = 0;
        courses = new SinglyLinkedList();
    }
    /**
     * @precondition file record should contain double locations.
     * @postcondition a tree should be constructed with the records in the file.
     * add records into the tree.
     * @param filePath
     * @param tree
     */
    public void constructTree(String filePath) {
        try { 
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String line;
            line = in.readLine();
            while(line != null) {
                processLine(line); 
                line = in.readLine();
            }
            in.close();
        }
        catch(IOException e) {
            System.out.println("IO Exception");
        }
    }

    public void processLine(String line) {
        StringTokenizer st = new StringTokenizer(line, " \t");
        st.nextToken();
        //System.out.println(student);
        while (st.hasMoreTokens()) {
            String courseName = st.nextToken();
            if (courseName.length() > 1) {
                //System.out.print(courseName + ' ');
                insert(courseName);
                //printTree();
            }
        } 
    }

    public void printTree() {
        System.out.println("----------tree start -------------");
        System.out.println(root);
        // first
        System.out.print(root.getLc() + "    ");
        System.out.println(root.getRc());
        // second 
        if (root.getLc() != nil) {
            System.out.print(root.getLc().getLc() + "    ");
            System.out.println(root.getLc().getRc());
        }
        if (root.getRc() != nil) {
            System.out.print(root.getRc().getLc() + "    ");
            System.out.println(root.getRc().getRc());
        }
        System.out.println("----------tree end -------------");
    }
    

    public boolean contains(String s) {
        RedBlackNode current = root;

        while (current != nil) {
            int comparison = s.compareTo(current.getCourse());
    
            if (comparison == 0) {
                return true;
            }
            else if (comparison < 0) {
                current = current.getLc();
            }
            else {
                current = current.getRc();
            }
        }
        return false;
    }

    public int getIndex(String s) {
        RedBlackNode current = root;

        while (current != nil) {
            int comparison = s.compareTo(current.getCourse());
    
            if (comparison == 0) {
                return current.value;
            }
            else if (comparison < 0) {
                current = current.getLc();
            }
            else {
                current = current.getRc();
            }
        }
        return -1;
    }

    public int getSize() {
        return count;
    }

    public void insert(String s) {
        // if already exist, skip insert.
        if (contains(s)) {
            return;
        }
        int value = count++;
        RedBlackNode newNode = new RedBlackNode(s, value, RedBlackNode.RED, nil, nil, nil);
        courses.add(s, value);
        if (root == nil) {
            root = newNode;
            RBInsertFixup(newNode);
            return;
        }
        RedBlackNode y = nil;
        RedBlackNode x = root;
        while (x != nil) {
            y = x;
            if (s.compareTo(x.getCourse()) < 0) {
                x = x.getLc();
            } else {
                x = x.getRc();
            }
        }
        newNode.setP(y);
        if (y == nil) {
            root = newNode;
        } else {
            if (s.compareTo(y.getCourse()) < 0) {
                y.setLc(newNode);
            } else {
                y.setRc(newNode);
            }
        }
        //printTree();
        RBInsertFixup(newNode);
    }
    /**
     *  pre: right[x] != nil[T]
     *  pre: root's parent is nill[T]
     * @param x
     */
    public void leftRotate(RedBlackNode x) {
        RedBlackNode y = x.getRc();
        x.setRc(y.getLc());
        y.getLc().setP(x);
        y.setP(x.getP());

        if (x.getP() == nil) {
            root = y;
        } else {
            if (x == x.getP().getLc()) {
                x.getP().setLc(y);
            } else {
                x.getP().setRc(y);
            }
        }
        y.setLc(x);
        x.setP(y);
    }

    public void RBInsertFixup(RedBlackNode z) {
        if (root == z) {
            z.setColor(0);
            return;
        }
        while (z.getP().getColor() == 1) {
            if (z.getP() == z.getP().getP().getLc()) {
                RedBlackNode y = z.getP().getP().getRc(); // uncle node
                if (y.getColor() == 1) {
                    z.getP().setColor(0);
                    y.setColor(0);
                    z.getP().getP().setColor(1);
                    z = z.getP().getP();
                } else {
                    if (z == z.getP().getRc()) { // z is the right child.
                        z = z.getP();
                        leftRotate(z);
                    }
                    z.getP().setColor(0);
                    z.getP().getP().setColor(1);
                    rightRotate(z.getP().getP());
                }
            } else {
                RedBlackNode y = z.getP().getP().getLc(); // left uncle node.
                if (y.getColor() == 1) {
                    z.getP().setColor(0);
                    y.setColor(0);
                    z.getP().getP().setColor(1);
                    z = z.getP().getP();
                } else {
                    if (z == z.getP().getLc()) {
                        z = z.getP();
                        rightRotate(z);
                    }
                    z.getP().setColor(0);
                    z.getP().getP().setColor(1);
                    leftRotate(z.getP().getP());
                }
            }
        }
        root.setColor(0);
    }
    /**
     *  pre: left[x] != nil[T]
     *  pre: root's parent is nill[T]
     * @param x the node.
     */
    public void rightRotate(RedBlackNode x) {
        RedBlackNode y = x.getLc();  // y now points to node to left of x
        x.setLc(y.getRc());          // y's right subtree becomes x's left subtree
        y.getRc().setP(x);           // right subtree of y gets a new parent
        y.setP(x.getP());            // y's parent is now x's parent

        // if x is at root then y becomes new root.
        if (x.getP() == nil) {
            root = y;
        } else {
            // if x is a left child then adjust x's parent's left child or..

            if (x == x.getP().getLc()) {
                x.getP().setLc(y);
            } else {
                // adjust x's parent's right child.
                x.getP().setRc(y);
            }
        }
        // the right child of y is now x
        y.setRc(x);
        // the parent of x is now y
        x.setP(y);
    }

    public void printInOrder() {
        if (root == nil) {
            return;
        }
        courses.display();
    }
}
