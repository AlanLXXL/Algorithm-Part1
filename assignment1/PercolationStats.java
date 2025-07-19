package assignment1;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    // Instance variables will go here
    private static final double CONFIDENCE_95 = 1.96;
    private final double mean;
    private final double stddev;
    private final int trialsCount;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        // 1. Validate the arguments
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must be greater than 0");
        }
        this.trialsCount = trials;
        double[] results = new double[trials];

        // 2. Run the simulation T times
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                }
            }
            // Record the results of the trial
            results[i] = (double) p.numberOfOpenSites() / (n * n);
        }

        // 3. Calculate and store the final statistics
        this.mean = StdStats.mean(results);
        this.stddev = StdStats.stddev(results);
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - (CONFIDENCE_95 * stddev) / Math.sqrt(trialsCount);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + (CONFIDENCE_95 * stddev) / Math.sqrt(trialsCount);
    }

    // test client (see below)
    public static void main(String[] args) {
        // 1. Read n and T from the command-line arguments.
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        // 2. Create a assignment1.PercolationStats object to run the simulation.
        PercolationStats ps = new PercolationStats(n, t);

        // 3. Print the results in the specified format.
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}