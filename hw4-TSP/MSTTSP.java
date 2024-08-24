import java.util.LinkedList;
import java.util.List;

/**
 * @author Huiyan (Zoey) Xu
 * andrew id: huiyanx
 * course: 95771
 * Assignment Number: homework 4
 * minimum scanning tree tsp
 */
public class MSTTSP {
    // matrix for graph
    private double[][] dist;
    // number of vertexs
    private int numOfVertices;
    // the min heap for edges
    private MinHeap heap;
    // the sub optimal distance
    private double totalDistance;
    // the array to store parent relationship
    int[] parent;

    String result;
    
    public MSTTSP(int len, double[][] matrix) {
        this.numOfVertices = len;
        this.dist = matrix;
        this.heap = new MinHeap(len*len);
    }

    public void createMST() {
        // the parent list to store each vertex's parent.
        parent = new int[numOfVertices];
        // whether the vertex is visited
        boolean[] visited = new boolean[numOfVertices];

        // mark every vertex as unvisited
        for (int i = 0; i < numOfVertices; i++) {
            visited[i] = false;
        }
        // start from index 0 
        parent[0] = -1;

        // add all edges starting from 0 into the heap.
        for (int i = 1; i < numOfVertices; i++) {
            heap.insert(new Edge(0, i, dist[0][i]));
        }
        //mark 0 as visited
        visited[0] = true;

        // the MST loop
        // continue the loop until every vertex is visited
        while (!allVisited(visited)) {
            // choose the shortest edge from the heap.
            Edge shortestEdge = heap.deleteMin();
            int start = shortestEdge.start;
            int end = shortestEdge.end;
            // System.out.println("checking:" + shortestEdge);

            // check if the edge can be used
            if (visited[end] == true) { // if the end vertex is already visited, look for next edge
                continue;
            } else { //else, choose this edge and move our location to the next vertex
                // System.out.println("walking" + shortestEdge);
                // remember parent
                parent[end] = start;
                //mark end as visited.
                visited[end] = true;
                // add edges from the new starting point
                for (int i = 0; i < numOfVertices; i++) {
                    if (i != end && visited[i] == false) {
                        // only edges that lead to not visited vertexs.
                        heap.insert(new Edge(end, i, dist[end][i]));
                    }
                }
            }
        }
    }
    /**
     * check if all vertexs are visited
     * @param visited a boolean array
     * @return a boolean
     */
    private static boolean allVisited(boolean[] visited) {
        for (boolean s: visited) {
            if (s == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * get the preorder in String
     * @return a string
     */
    public String getPreorder() {
        List<Integer> l = preorder();
        StringBuffer sb = new StringBuffer();

        // add 0 to the end of list
        for (int i : l) {
            sb.append(i).append(" ");
        }

        //calculate the total distance
        for (int i = 0; i < numOfVertices; i++) {
            int s = l.get(i);
            int e = l.get(i+1);
            totalDistance += dist[s][e];
        }
        return sb.toString();
    }

    /**
     * get the optimal path in linkedlist
     * @return a linkedlist
     */
    public LinkedList<Integer> preorder() {
        LinkedList<Integer> l = new LinkedList<Integer>();
        boolean[] visit = new boolean[numOfVertices];
        preorder(-1, parent, visit, l);
        // add the starting point at the end to make it a loop
        l.add(0);
        return l;
    }

    /**
     * recursively get the vertex in preorder
     * @param index the parent node index (-1 is the root)
     * @param p the parent array
     * @param visit a boolean array stores whether visited here
     * @param l preorder print result
     */
    public static void preorder(int index, int[] p, boolean[] visit, List<Integer> l) {
        if (allVisited(visit)) {
            return;
        }
        int n = p.length;
        for (int i = 0; i < n; i++) {
            if (p[i] == index) {
                l.add(i);
                visit[i] = true;
                preorder(i, p, visit,l);
            }
        }

    }

    /**
     * get the optimal distance in miles
     * @return a double.
     */
    public double getDis() {
        return totalDistance * 0.00018939;
    }
}
