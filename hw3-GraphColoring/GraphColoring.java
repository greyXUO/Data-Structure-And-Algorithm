/**
 * @author Huiyan (Zoey) Xu
 * andrew id: huiyanx
 * course: 95771
 * Assignment Number: homework 3 maxtrix class.
 */
public class GraphColoring {
    /**
     * the graph, same to a matrix.
     */
    private int[][] graph;
    /**
     * the color number for all courses.
     */
    private int[] colors;

    public GraphColoring(int[][] graph) {
        this.graph = graph;
        this.colors = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            colors[i] = -1;
        }
    }
    /**
     * greedy search for a coloring way that is sub-optimal.
     */
    public void greedyColoring() {
        for (int v = 0; v < graph.length; v++) {
            if (colors[v] == -1) { 
                int cr;
                for (cr = 0; cr < graph.length; cr++) {
                    boolean found = true;
                    for (int i = 0; i < graph.length; i++) {
                        if (graph[v][i] == 1 && cr == colors[i]) {
                            found = false;
                            break;
                        }
                    }
                    if (found) {
                        break;
                    }
                }
                colors[v] = cr;
            }
        }
    }
    
    public int[] getColors() {
        return colors;
    }

    /**
     * return the results in string with enters.
     * @param list the list stores course name and index.
     * @param c the number of a color
     * @return a string of courses that is assigned to this color.
     */
    public String printResult(SinglyLinkedList list, int c) {
        StringBuffer re = new StringBuffer();
        for (int i = 0; i < graph.length; i++) {
            if (colors[i] == c) {
                re.append("   " + list.getNthElement(i) + "\n");
            }
        }
        return re.toString();
    }
    /**
     * return the results in string without enters.
     * @param list the list stores course name and index.
     * @param c the number of a color
     * @return a string of courses that is assigned to this color.
     */
    public String getResult(SinglyLinkedList list, int c) {
        StringBuffer re = new StringBuffer();
        for (int i = 0; i < graph.length; i++) {
            if (colors[i] == c) {
                re.append(list.getNthElement(i) + " ");
            }
        }
        return re.toString();
    }
}
