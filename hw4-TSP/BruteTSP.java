import java.util.List;
import java.util.LinkedList;
/**
 * @author Huiyan (Zoey) Xu
 * andrew id: huiyanx
 * course: 95771
 * Assignment Number: homework 4
 * brute tsp
 */
public class BruteTSP {
    /**
     * number of vertexs
     */
    int len;
    /**
     * the matrix stores the distance info.
     */
    double[][] dist;
    /**
     * if a vertex is visited
     */
    boolean[] visited;
    /**
     * the current path we go through
     */
    LinkedList<Integer> currentPath;
    /**
     * the shortest path length we have ever walked
     */
    double shortestPathLength = Double.MAX_VALUE;
    /**
     * the shorestest path we ever walked
     */
    LinkedList<Integer> shortestPath;

    public BruteTSP(int len, double[][] matrix) {
        this.len = len;
        this.dist = matrix;
        this.visited = new boolean[len];
        this.currentPath = new LinkedList<>();
        this.shortestPath = new LinkedList<>();
    }

    /**
     * recursively find every possible path.
     * @param start the vertex that we start the path searching.
     */
    public void findShortestLoop(int start) {
        // mark start as visited
        visited[start] = true;
        // add the start vertex to list
        currentPath.add(start);

        // if all vertex visited
        if (currentPath.size() == len) { 
            // add the length with last vertex and origin
            double totalLength = calculatePathLength(currentPath) + dist[currentPath.get(len - 1)][currentPath.get(0)];
            // if the length is less than the shortest path we met before, update
            if (totalLength < shortestPathLength) {
                shortestPathLength = totalLength;
                shortestPath = new LinkedList<>(currentPath);
                shortestPath.add(0); // add the origin
            }
        } else { // if still vertex not visited, visit those in order.
            for (int i = 0; i < len; i++) {
                if (!visited[i]) {
                    findShortestLoop(i);
                }
            }
        }
        // mark vertex as not visited so other path can go through
        visited[start] = false;
        currentPath.remove(currentPath.size() - 1);
    }

    /**
     * calculate the path length given the list
     * @param path a list has the vertex visiting in order
     * @return a double
     */
    private double calculatePathLength(List<Integer> path) {
        double length = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            length += dist[path.get(i)][path.get(i + 1)];
        }
        return length;
    }

    /**
     * get the shortest path
     * @return the shortest path string
     */
    public String getShortestPath() {
        StringBuffer sb = new StringBuffer();
        for (int i: shortestPath) {
            sb.append(i).append(" ");
        }
        return sb.toString();
    }

    /**
     * get the shortest path
     * @return the shortest path list
     */
    public LinkedList<Integer> getShortestPathList() {
        shortestPath.add(0);
        return shortestPath;
    }

    /**
     * get the shortest path in miles
     * @return a double
     */
    public double getShortestPathLength() {
        return shortestPathLength * 0.00018939;
    }
}