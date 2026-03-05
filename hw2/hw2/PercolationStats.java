package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private double[] fractions;
    private int T;
    private int N;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        fractions = new double[T];
        this.N = N;
        this.T = T;
        for (int i = 0; i < T; i++) {
            Percolation P = pf.make(N);
            while (!P.percolates()) {
                int randomNum = StdRandom.uniform(N * N);
                while (P.isOpen(randomNum / N, randomNum % N)) {
                    randomNum = StdRandom.uniform(N * N);
                }
                P.open(randomNum / N, randomNum % N);
            }
            fractions[i] = (double) P.numberOfOpenSites() / (N * N);
        }
    }

    public double mean() {
        return StdStats.mean(fractions);
    }

    public double stddev() {
        return StdStats.stddev(fractions);
    }

    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

}
