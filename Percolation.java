import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // side length of grid
    private int size;

    // grid
    private int[][] percGrid;

    // number of open squares
    private int nOpen;

    // grid squares plus virtual top/bottom as 1-D QuickFind array
    private WeightedQuickUnionUF sets;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("");
        }
        size = n;
        percGrid = new int[n][n];
        nOpen = 0;
        sets = new WeightedQuickUnionUF(n * n + 2);

    }

    // convert 2-D coordinates to 1-D array index
    private int convert(int row, int col) {
        return row * size + col + 1;
    }

    // check whether row/column is in grid
    private boolean isValid(int row, int col) {
        if ((0 <= row) && (row <= (size - 1)) && (0 <= col) && (col <= (size - 1))) {
            return true;
        }
        return false;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isValid(row, col)) {
            throw new IllegalArgumentException("");
        }

        if (!isOpen(row, col)) {
            percGrid[row][col] = 1;
            nOpen++;

            // only check square above if not in top row, o.w. merge with virtual top
            if (row != 0) {
                if (isOpen(row - 1, col)) {
                    sets.union(convert(row, col), convert(row - 1, col));
                }

            }
            else {
                sets.union(0, convert(row, col));
            }

            // only check square below if not in bottom row,
            // o.w. merge with virtual bottom
            if (row != size - 1) {
                if (isOpen(row + 1, col)) {
                    sets.union(convert(row, col), convert(row + 1, col));
                }

            }
            else {
                sets.union(size * size + 1, convert(row, col));
            }

            // only check square to right if not in rightmost column
            if (col != 0) {
                if (isOpen(row, col - 1)) {
                    sets.union(convert(row, col), convert(row, col - 1));
                }
            }

            // only check square to left if not in leftmost column
            if (col != size - 1) {
                if (isOpen(row, col + 1)) {
                    sets.union(convert(row, col), convert(row, col + 1));
                }
            }


        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isValid(row, col)) {
            throw new IllegalArgumentException("");
        }

        if (percGrid[row][col] == 1) {
            return true;
        }
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isValid(row, col)) {
            throw new IllegalArgumentException("");
        }

        if (sets.find(convert(row, col)) == sets.find(0)) {
            return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return nOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        if (sets.find(0) == sets.find(size * size + 1)) {
            return true;
        }

        return false;
    }

    // display grid (sanity check)
    public void printGrid() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (percGrid[i][j] == 0) {
                    System.out.print(" " + 0 + " ");
                }
                else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // monte carlo until percolates
    public double percSim() {
        while (!(percolates())) {
            int openRow = StdRandom.uniformInt(size);
            int openCol = StdRandom.uniformInt(size);
            while (isOpen(openRow, openCol)) {
                openRow = StdRandom.uniformInt(size);
                openCol = StdRandom.uniformInt(size);
            }
            open(openRow, openCol);
        }

        double propOpen = ((double) nOpen) / ((double) (size * size));
        return propOpen;

    }


    // unit testing (required)
    public static void main(String[] args) {

        Percolation unitTest = new Percolation(2);
        unitTest.open(0, 0);
        unitTest.printGrid();
        System.out.println(unitTest.percolates());
        unitTest.open(1, 0);
        unitTest.printGrid();
        System.out.println(unitTest.percolates());
        System.out.println();

        Percolation simTest = new Percolation(200);
        System.out.println(simTest.percSim());


    }
}
