package week_one;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  private boolean[][] sites;
  private final int size;
  private final int top = 0;
  private int opened = 0;
  private int bottom;
  private final WeightedQuickUnionUF quickUnionUF;

  public Percolation(int n) {
    if(n <= 0) {
      throw new IllegalArgumentException();
    }
    size = n;
    sites = new boolean[n][n];
    bottom = (n * n) + 1;
    quickUnionUF = new WeightedQuickUnionUF((n * n) + 2);
  }

  public void open(int row, int col) {
    checkLimits(row, col);

    int currentSiteQFIndex = getQFIndex(row, col);

    if(isOpen(row, col)) {
      //already opened
      return;
    }

    sites[row - 1][col - 1] = true;
    opened++;

    //top sites always connected with virtual top
    if(row == 1) {
      quickUnionUF.union(top, currentSiteQFIndex);
    }

    //bottom sites always connected with the virtual bottom
    if(row == size) {
      quickUnionUF.union(bottom, currentSiteQFIndex);
    }

    //union with left opened site
    if(row > 1 && isOpen(row - 1, col)) {
      quickUnionUF.union(currentSiteQFIndex, getQFIndex(row - 1, col));
    }
    //union with right opened site
    if(row < size && isOpen(row + 1, col)) {
      quickUnionUF.union(currentSiteQFIndex, getQFIndex(row + 1, col));
    }

    //union with top opened site
    if(col > 1 && isOpen(row, col - 1)) {
      quickUnionUF.union(currentSiteQFIndex, getQFIndex(row, col - 1));
    }

    //union with bottom opened site
    if(col < size && isOpen(row, col + 1)) {
      quickUnionUF.union(currentSiteQFIndex, getQFIndex(row, col + 1));
    }
  }

  public boolean isOpen(int row, int col) {
    checkLimits(row, col);
    return sites[row - 1][col - 1];
  }

  public boolean isFull(int row, int col) {
    checkLimits(row, col);
    return quickUnionUF.connected(top, getQFIndex(row, col));
  }

  public int numberOfOpenSites() {
    return opened;
  }

  public boolean percolates() {
    return quickUnionUF.connected(top, bottom);
  }

  //Convert a two-dimensional array index into one-dimensional array index
  // In UnionFind used an one-dimensional array.
  private int getQFIndex(int row, int col) {
    return (row - 1) * size + col;
  }

  private void checkLimits(int row, int col) {
    if((row < 0 || row >= size) ||  (col < 0 || col >= size)) {
      throw new IllegalArgumentException();
    }
  }
}
