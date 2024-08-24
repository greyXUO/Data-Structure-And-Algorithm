import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
/**
 * @author Huiyan Xu
 * course: 95771
 * Assignment Number: homework 2 2-d tree class.
 */
public class TwoDTree {
    /**
     * the index of the tree node;
     */
    private TreeNode root;
    /**
     * how many nodes have been checked during search.
     */
    public int searchCount;
    /**
     * total number of nodes.
     */
    public int counts = 0;

    /**
     * initialize the 2-d tree.
     * @precondition: The String crimeDataLocation contains the path to a file formatted in the exact same way as CrimeLatLonXY.csv 
     * @postcondition: The 2d tree is constructed and may be printed or queried.
     */
    public TwoDTree() {
        root = null;
    }
    /**
     * @precondition file record should contain double locations.
     * @postcondition a 2-d tree should be constructed with the records in the file.
     * add records into the tree.
     * @param filePath
     * @param tree
     */
    public void constructTree(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip the header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, ",");
                double x = Double.parseDouble(st.nextToken());
                double y = Double.parseDouble(st.nextToken());
                // Rest of the record as a single string for simplicity
                String record = line;
                add(x, y, record);
                counts++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @precondition tree exists.
     * @postcondition a node is created and added to the tree.
     * insert crime records.
     * @param x the latitude.
     * @param y the longtitude.
     * @param record crime information.
     */
    public void add(double x, double y, String record) {
        boolean compareX = true;
        boolean isLeft = true;
        TreeNode newNode = new TreeNode(x, y, record);
        if (root == null) {
            root = newNode;
            return;
        }
        TreeNode curr = root;
        TreeNode par = root;
        while (curr != null) {
            if (compareX) { // compare x
                if (curr.x > x) {
                    par = curr;
                    curr = curr.left;
                    isLeft = true;
                } else {
                    par = curr;
                    curr = curr.right;
                    isLeft = false;
                }
            } else {  // compare y
                if (curr.y > y) {
                    par = curr;
                    curr = curr.left;
                    isLeft = true;
                } else {
                    par = curr;
                    curr = curr.right;
                    isLeft = false;
                }
            }
            compareX = !compareX;
        }
        if (isLeft) {
            par.left = newNode;
        } else {
            par.right = newNode;
        }
    }

    /**
     * @precondition: The 2d tree has been constructed. 
     * @postcondition: The 2d tree is displayed with a pre-order traversal. 
     */
    public void preOrderPrint() {
        if (root == null) {
            return;
        }
        printPreOrder(root);
    }
    private void printPreOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.println(node);
        printPreOrder(node.left);
        printPreOrder(node.right);
    }
    /**
     * @precondition: The 2d tree has been constructed.
     * @postcondition: The 2d tree is displayed with an in-order traversal.
     * Theta O = n.
     * Because each node would visit once. So the time complexity is N (nums of nodes). O(N) is O (N).
     */
    public void inOrderPrint() {
        if (root == null) {
            return;
        }
        printInOrder(root);
    }
    private void printInOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        printInOrder(node.left);
        System.out.println(node);
        printInOrder(node.right);
    }
    /**
     * @precondition: The 2d tree has been constructed.
     * @postcondition: The 2d tree is displayed with a post-order traversal.
     */
    public void postOrderPrint() {
        if (root == null) {
            return;
        }
        printPostOrder(root);
    }
    private void printPostOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        printPostOrder(node.left);
        printPostOrder(node.right);
        System.out.println(node);
    }
    /**
     * @precondition: The 2d tree has been constructed.
     * @postcondition: The 2d tree is displayed with a level-order traversal. 
     */
    public void levelOrderPrint() {
        if (root == null) {return;}
        Queue<TreeNode> queue = new Queue<TreeNode>();
        int curr;
        int next;
        queue.add(root);
        curr = 1;
        next = 0;
        while (!queue.isEmpty()) {
            TreeNode currentNode = queue.remove();
            System.out.print(currentNode + " ");
            curr--;

            if (currentNode.left != null) {
                queue.add(currentNode.left);
                next++;
            }
            if (currentNode.right != null) {
                queue.add(currentNode.right);
                next++;
            }
            if (curr == 0) {
                System.out.println();
                curr = next;
                next = 0;
            }
        }
    }
    /**
     * @precondition: The 2d tree has been constructed.
     * @postcondition: The 2d tree is displayed with a reverse level-order traversal.
     * Theta O = n.
     * Because each node would be pushed and poped from the queue once, and the stack would store the node and remove the node later on.
     * So the time complexity is a constant times N (nums of nodes). O(cN) is O (N). 
     */
    public void reverseLevelOrderPrint() {
        if (root == null) {return;}
        Queue<TreeNode> queue = new Queue<TreeNode>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode currentNode = queue.remove();
            stack.push(currentNode);

            if (currentNode.right != null) {
                queue.add(currentNode.right);
            }
            if (currentNode.left != null) {
                queue.add(currentNode.left);
            }
        }
        while (!stack.isEmpty()) {
            System.out.println(stack.pop() + " ");
        }
    }
    /**
     * @precondition: The 2d tree has been constructed.
     * @postcondition: A list of 0 or more crimes is returned. 
     * @param x1 left bottom point x.
     * @param y1 left bottom point y.
     * @param x2 top right point x.
     * @param y2 top right point y.
     * @return
     */
    public ListOfCrimes findPointsInRange(double x1, double y1, double x2, double y2) {
        ListOfCrimes crimeList = new ListOfCrimes();
        searchCount = 0;
        findPointsInRange(root, x1, y1, x2, y2, 0, crimeList);
        return crimeList;
    }

    /**
     * revursively search for the points within a range in a 2-d tree.
     * @precondition locations should be valid and node is within the tree. Tree should be a correct 2-d tree.
     * @postcondition the points are added to the list if meet the requirement.
     * @param node current node to check if within the range.
     * @param x1 left bottom point x.
     * @param y1 left bottom point y.
     * @param x2 top right point x.
     * @param y2 top right point y.
     * @param depth current depth in the tree.
     * @param list the ListofCrimes, a singly linked list stores crime info.
     */
    public void findPointsInRange(TreeNode node, double x1, double y1, double x2, double y2, int depth, ListOfCrimes list) {
        if (node == null) {
            return;
        }
        searchCount++;
        if (checkWithin(node, x1, y1, x2, y2)) {
            list.add(node.record);
        }
        if (depth % 2 == 0) {
            if (x1 < node.getX()) {
                findPointsInRange(node.left, x1, y1, x2, y2, depth + 1, list); 
            }
            if (x2 >= node.getX()) {
                findPointsInRange(node.right, x1, y1, x2, y2, depth + 1, list); 
            }
        } else {
            if (y1 < node.getY()) {
                findPointsInRange(node.left, x1, y1, x2, y2, depth + 1, list);
            }
            if (y2 >= node.getY()) {
                findPointsInRange(node.right, x1, y1, x2, y2, depth + 1, list);
            }
        }
    }
    /**
     * a static method to check if a node is in a rectangle range.
     * @param node the node.
     * @param x1 left bottom x.
     * @param y1 left bottom y.
     * @param x2 right top x.
     * @param y2 right top y.
     * @return a boolean, if within return true.
     */
    public static boolean checkWithin(TreeNode node, double x1, double y1, double x2, double y2) {
        double x = node.x;
        double y = node.y;
        if (x >= x1 && x <= x2 && y >= y1 && y <= y2) {
            return true;
        }
        return false;
    }

    /**
     * @precondition: the 2d tree has been constructed.
     * @postcondition: the distance in feet to the nearest node and that node are returned.
     * @param x1  The (x1,y1) pair represents a point in space near Pittsburgh and in the state plane coordinate system.
     * @param y1  see above.
     * @return returned in Neighbor. In addition, the Neighbor object contains a reference to the nearest neighbor in the tree. 
     */
    public Neighbor nearestNeighbor(double x1, double y1) {
        if (root == null) {return null;}
        searchCount = 0;
        TreeNode target = new TreeNode(x1, y1, null);
        return nearestNeighbor(root, target, 0, null);
    }

    /**
     * @precondition: the 2d tree has been constructed.
     * @postcondition: the distance in feet to the nearest node and that node are returned.
     * @param node current comparing node.
     * @param target the place we search around.
     * @param depth current node depth.
     * @param best the best neighbor that is cloested to the target.
     * @return a neighbor object containing node and distance.
     * LLM is used for code structure of this method.
     */
    private Neighbor nearestNeighbor(TreeNode node, TreeNode target, int depth, Neighbor best) {
        if (node == null) {
            return best;
        }
        searchCount++;
        double distance = calculateDistance(node, target); // calculate the dis between target and current node.
        if (best == null || best.getDistance() > distance) { // if current is closer than best history, replace.
            best = new Neighbor(target, node);
        }
        // goes down to the brance. if depth is odd, compare y, vice versa.
        boolean isLeftOrDown = depth % 2 == 0 ? target.x < node.getX() : target.y < node.getY();
        TreeNode first = isLeftOrDown ? node.left : node.right;
        TreeNode second = isLeftOrDown ? node.right : node.left;

        best = nearestNeighbor(first, target, depth + 1, best);

        // calculate the distance between target point and previous seperate line. If larger than the best case, no need to search in this branch.
        double disPrime = depth % 2 == 0 ? Math.abs(node.getX() - target.x) : Math.abs(node.getY() - target.y);
        if (best == null || best.getDistance() > disPrime) {
            best = nearestNeighbor(second, target, depth + 1, best);
        }
        return best;
    }
    /**
     * a static method to calculate the distance between two nodes.
     * @precondition: two nodes exists.
     * @postcondition: the distance between two nodes is returned in double format.
     * @param node1 node 1.
     * @param node2 another node.
     * @return double distance.
     */
    public static double calculateDistance(TreeNode node1, TreeNode node2) {
        double xDiff = node1.getX() - node2.getX();
        double yDiff = node1.getY() - node2.getY();
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }
}
