package week_one;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

  private final int numberOfExperiments;
  private final double[] fractions;

  private static final double CONDINDENCE_INTERVAL_PARAMETER = 1.96;

  public PercolationStats(int n, int trials) {
    if(n <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }

    numberOfExperiments = trials;
    fractions = new double[numberOfExperiments];

    for(int i = 0; i < numberOfExperiments; ++i) {
      Percolation percolation = new Percolation(n);

      while(!percolation.percolates()) {
        int row = StdRandom.uniform(1, n + 1);
        int col = StdRandom.uniform(1, n + 1);
        percolation.open(row, col);
      }

      double fraction = (double) percolation.numberOfOpenSites() / (n * n);
      fractions[i] = fraction;
    }

  }

  public double mean() {
    return StdStats.mean(fractions);
  }

  public double stddev() {
    return StdStats.stddev(fractions);
  }

  public double confidenceLo() {
    return mean() - (CONDINDENCE_INTERVAL_PARAMETER * stddev()) / Math.sqrt(numberOfExperiments);
  }

  public double confidenceHi() {
    return mean() + (CONDINDENCE_INTERVAL_PARAMETER * stddev()) / Math.sqrt(numberOfExperiments);
  }

}
