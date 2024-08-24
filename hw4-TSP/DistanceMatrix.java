/**
 * @author Huiyan (Zoey) Xu
 * andrew id: huiyanx
 * course: 95771
 * Assignment Number: homework 4.
 * Distance calculate matrix.
 */

public class DistanceMatrix {
    /*
     * the matrix of distanc between crime points.
     */
    double[][] matrix;

    public DistanceMatrix(Crime[] crimeArray) {
        // get the num of points
        int len = crimeArray.length;
        // create a matrix to represent all possible links
        matrix = new double[len][len];

        for (int i = 0; i < len; i++) {
            for (int j = i; j < len; j++) {
                // for points to themselves, make distance extremely large.
                if (i == j) {
                    matrix[i][i] = Double.MAX_VALUE;
                    continue;
                }
                // else, give value to a->b and b->a the same value
                Crime crime1 = crimeArray[i];
                Crime crime2 = crimeArray[j];
                double sq = Math.pow(crime1.x - crime2.x, 2) + Math.pow(crime1.y - crime2.y, 2);
                double distance = Math.sqrt(sq);
                matrix[i][j] = distance;
                matrix[j][i] = distance;
            }
        }
        // for (double[] row : matrix) {
        //     for (double val : row) {
        //         System.out.printf("%10.2f", val); 
        //     }
        //     System.out.println(); 
        // }
    }
}
