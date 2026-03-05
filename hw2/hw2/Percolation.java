package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.HashSet;

public class Percolation {

    private boolean[][] sites;
    private int n;
    private int numOfOpenSites;
    private int virtualTop;
    private int virtualBottom;
    private WeightedQuickUnionUF ufPerc; // top + bottom
    private WeightedQuickUnionUF ufFull; // only top, no bottom

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = N;
        this.sites = new boolean[n][n];
        numOfOpenSites = 0;
        virtualTop = n * n;
        virtualBottom = n * n + 1;
        this.ufFull = new WeightedQuickUnionUF(n * n + 1);
        this.ufPerc = new WeightedQuickUnionUF(n * n + 2);
    }

    public void open(int row, int col) {
        validate(row, col);
        if (sites[row][col]) {
            return;
        }
        sites[row][col] = true;
        numOfOpenSites += 1;
        int index = xyTo1D(row, col);
        if (row == 0) { // If the site is at the first row, connect it to the virtual top site first.
            ufPerc.union(virtualTop, index);
            ufFull.union(virtualTop, index);
        } else if (row == n - 1) { // If the site is at the bottom and has connected to the top, connect it to the virtual bottom.
            ufPerc.union(virtualBottom, index);
        }

        connectIfOpen(row, col, row - 1, col);
        connectIfOpen(row, col, row + 1, col);
        connectIfOpen(row, col, row, col - 1);
        connectIfOpen(row, col, row, col + 1);
    }

    private void validate(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int xyTo1D(int row, int col) {
        return row * n + col;
    }

    /* Checks if siteA is open and connect siteB to siteA. */
    private void connectIfOpen(int r1, int c1, int r2, int c2) {
        if (r2 < 0 || r2 >= n || c2 < 0 || c2 >= n) {
            return;
        }
        if (!sites[r2][c2]) {
            return;
        }
        int a = xyTo1D(r1, c1);
        int b = xyTo1D(r2, c2);
        ufPerc.union(a, b);
        ufFull.union(a, b);
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return sites[row][col];
    }

    public boolean isFull(int row, int col)  {
        validate(row, col);
        if (!isOpen(row, col)) {
            return false;
        }
        int index = xyTo1D(row, col);
//        for (int i = 0; i < n; i++) {
//            int topIndex = xyTo1D(0, i);
//            if (Unions.connected(topIndex, index)) {
//                return true;
//            }
//        }
//        return false;
        return ufFull.connected(index, virtualTop);
    }

    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    public boolean percolates() {
//        for (int i = 0; i < n; i++) {
//            if (isFull(n - 1, i)) {
//                return true;
//            }
//        }
//        return false;
        return ufPerc.connected(virtualTop, virtualBottom);
    }
}
