package hungarianAlgorithm;

public class HungarianTest {
    public static void main(String[] args) {
        // m1 input values
        int[][] m1 = { { 0, 1, 0, 1, 1 }, { 1, 1, 0, 1, 1 }, { 1, 0, 0, 0, 1 },
                { 1, 1, 0, 1, 1 }, { 1, 0, 0, 1, 0 } };

        // int[][] m1 = { {13,14,0,8},
        // {40,0,12,40},
        // {6,64,0,66},
        // {0,1,90,0}};

        // int[][] m1 = { {0,0,100},
        // {50,100,0},
        // {0,50,50}};

        // m2 max(horizontal,vertical) values, with negative number for
        // horizontal, positive for vertical
        int[][] m2 = new int[m1.length][m1.length];

        // m3 where the line are drawen
        int[][] m3 = new int[m1.length][m1.length];

        // loop on zeroes from the input array, and sotre the max num of zeroes
        // in the m2 array
        for (int row = 0; row < m1.length; row++) {
            for (int col = 0; col < m1.length; col++) {
                if (m1[row][col] == 0)
                    m2[row][col] = hvMax(m1, row, col);
            }
        }

        // print m1 array (Given input array)
        System.out.println("Given input array");
        for (int row = 0; row < m1.length; row++) {
            for (int col = 0; col < m1.length; col++) {
                System.out.print(m1[row][col] + "\t");
            }
            System.out.println();
        }

        // print m2 array 
        System.out
                .println("\nm2 array (max num of zeroes from horizontal vs vertical) (- for horizontal and + for vertical)");
        for (int row = 0; row < m1.length; row++) {
            for (int col = 0; col < m1.length; col++) {
                System.out.print(m2[row][col] + "\t");
            }
            System.out.println();
        }

        // Loop on m2 elements, clear neighbours and draw the lines
        for (int row = 0; row < m1.length; row++) {
            for (int col = 0; col < m1.length; col++) {
                if (Math.abs(m2[row][col]) > 0) {
                    clearNeighbours(m2, m3, row, col);
                }
            }
        }

        // prinit m3 array (Lines array)
        System.out.println("\nLines array");
        for (int row = 0; row < m1.length; row++) {
            for (int col = 0; col < m1.length; col++) {
                System.out.print(m3[row][col] + "\t");
            }
            System.out.println();
        }
    }

    // max of vertical vs horizontal at index row col
    public static int hvMax(int[][] m1, int row, int col) {
        int vertical = 0;
        int horizontal = 0;

        // check horizontal
        for (int i = 0; i < m1.length; i++) {
            if (m1[row][i] == 0)
                horizontal++;
        }

        // check vertical
        for (int i = 0; i < m1.length; i++) {
            if (m1[i][col] == 0)
                vertical++;
        }

        // negative for horizontal, positive for vertical
        return vertical > horizontal ? vertical : horizontal * -1;
    }

    // clear the neighbors of the picked largest value, the sign will let the
    // app decide which direction to clear
    public static void clearNeighbours(int[][] m2, int[][] m3, int row, int col) {
        // if vertical
        if (m2[row][col] > 0) {
            for (int i = 0; i < m2.length; i++) {
                if (m2[i][col] > 0)
                    m2[i][col] = 0; // clear neigbor
                m3[i][col] = 1; // draw line
            }
        } else {
            for (int i = 0; i < m2.length; i++) {
                if (m2[row][i] < 0)
                    m2[row][i] = 0; // clear neigbor
                m3[row][i] = 1; // draw line
            }
        }

        m2[row][col] = 0;
        m3[row][col] = 1;
    }
}