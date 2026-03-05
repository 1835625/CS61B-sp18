package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.HashSet;

public class Percolation {

    private boolean[][] sites;
    private int n;
    private WeightedQuickUnionUF Unions;
    private int numOfOpenSites;

    public Percolation(int N) {
        this.n = N;
        this.sites = new boolean[n][n];
        this.Unions = new WeightedQuickUnionUF(n * n);
        numOfOpenSites = 0;
    }

    public void open(int row, int col) {
        if (sites[row][col]) {
            return;
        }
        sites[row][col] = true;
        numOfOpenSites += 1;
        connectNeighbors(row, col);
    }

    private int xyTo1D(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            return -1;
        }
        return row * n + col;
    }

    /* Checks all neighbors of a site and connect it to the open ones. */
    private void connectNeighbors(int row, int col) {
        connect(row, col - 1, row, col);
        connect(row, col + 1, row, col);
        connect(row - 1, col, row, col);
        connect(row + 1, col, row, col);
    }

    /* Checks if siteA is open and connect siteB to siteA. */
    private void connect(int siteAr, int siteAc, int siteBr, int siteBc) {
        int siteA = xyTo1D(siteAr, siteAc);
        int siteB = xyTo1D(siteBr, siteBc);
        if (siteA != -1 && isOpen(siteAr, siteAc)) {
            Unions.union(siteA, siteB);
        }
    }

    public boolean isOpen(int row, int col) {
        return sites[row][col];
    }

    public boolean isFull(int row, int col)  {
        if (!isOpen(row, col)) {
            return false;
        }
        int index = xyTo1D(row, col);
        for (int i = 0; i < n; i++) {
            int topIndex = xyTo1D(0, i);
            if (Unions.connected(topIndex, index)) {
                return true;
            }
        }
        return false;
    }

    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    public boolean percolates() {
        for (int i = 0; i < n; i++) {
            if (isFull(n - 1, i)) {
                return true;
            }
        }
        return false;
    }
}
