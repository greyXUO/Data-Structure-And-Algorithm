import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
/**
 * @author Huiyan (Zoey) Xu
 * andrew id: huiyanx
 * course: 95771
 * Assignment Number: homework 4
 * class to run case tests
 */
public class CaseTest {
    String start;
    String end;
    LinkedList<Crime> caseCrimes;
    LinkedList<Crime> full;
    FileWriter writer;
    
    public CaseTest(LinkedList<Crime> full, String start, String end, FileWriter writer) {
        this.start = start;
        this.end = end;
        this.full = full;
        this.writer = writer;
        this.caseCrimes = new LinkedList<>();
    }

    public void run() {
        // select data
        for (Crime crime : full) {
            if ((crime.date.compareTo(start) >= 0) && (crime.date.compareTo(end) <= 0)) {
                caseCrimes.add(crime);
            }
        }
        Crime[] crimeArray = caseCrimes.toArray(new Crime[0]);
        int len = crimeArray.length;
        DistanceMatrix mx = new DistanceMatrix(crimeArray);

        // minimium scanning tree
        MSTTSP tsp = new MSTTSP(len, mx.matrix);
        tsp.createMST();
        try {
            writer.write("Hamiltonian Cycle\n");
            writer.write(tsp.getPreorder()+"\n");
            writer.write("Length\n");
            writer.write(tsp.getDis()+"\n");

            // brute path finding
            BruteTSP tsp2 = new BruteTSP(len, mx.matrix);
            tsp2.findShortestLoop(0);
            writer.write("Optimum Path\n");
            writer.write(tsp2.getShortestPath()+"\n");
            writer.write("Length\n");
            writer.write(tsp2.getShortestPathLength()+"\n\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
