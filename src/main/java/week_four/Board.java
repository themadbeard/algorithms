package week_four;

public class Board {

  private static final int SPACE = 0;

  private final int blocks[][];
  private final int dimension;

  public Board(int[][] blocks) {
    this.dimension = blocks.length;
    this.blocks = copy(blocks);
  }

  public int dimension(){
    return dimension;
  }

  public int hamming() {
    int count = 0;
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        if(isBlockIsNotInPlace(i, j)) {
          count++;
        }
      }
    }
    return count;
  }

  public int manhattan() {
    int sum = 0;
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        sum += calculateDistances(i, j);
      }

    }
    return sum;
  }

  public boolean isGoal() {
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        if(isBlockIsNotInPlace(i, j)) {
          return false;
        }
      }
    }
    return true;
  }

  public Board twin() {
    return null;
  }

  public Iterable<Board> neighbors() {
    return null;
  }

  @Override
  public boolean equals(Object y) {
    if (y == null) {
      return false;
    }

    if (y == this) {
      return true;
    }

    if (y.getClass() != this.getClass()) {
      return false;
    }

    Board that = (Board) y;
    if (that.dimension() != this.dimension()) {
      return false;
    }

    for (int i = 0; i < that.dimension() * that.dimension(); i++) {
      if (this.blocks[i] != that.blocks[i]) {
        return false;
      }
    }

    return true;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append(dimension() + "\n");
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks.length; j++)
        str.append(String.format("%2d ", block(i, j)));
      str.append("\n");
    }

    return str.toString();
  }

  private int[][] copy(int[][] blocks) {
    int [][] copy = new int[dimension][dimension];
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        copy[i][j] = blocks[i][j];
      }
    }
    return copy;
  }

  /**
   * Searching for goal value for this block.
   *   0 1 2
   * 0| | | |
   * 1| |x| |
   * 2| | | |
   * Expected value x for cell[1][1] is 5.
   * (1 * 3 + 1 + 1) = 5.
   * @param i - row
   * @param j - col
   * @return goal for [i][j] array cell.
   */
  private int goalFor(int i, int j) {
    return i * dimension + j + 1;
  }

  private boolean isSpaceBlock(int block) {
    return block == SPACE;
  }

  /**
   * Check for current block position
   * @param i - current row.
   * @param j - current col.
   * @return return true if block is not in place, false - otherwise.
   */
  private boolean isBlockIsNotInPlace(int i, int j) {
    int block = block(i, j);
    return !isSpaceBlock(block) && block != goalFor(i, j);
  }

  private int calculateDistances(int i, int j) {
    int block = block(i, j);

    return isSpaceBlock(block) ? 0 : calculateDistance(i, j, block);
  }

  /**
   * First row and column both are 1.
   * Those operations creating (2 dim array) coordinates (or grid coordinates) for current block (1 dim array).
   * @see <a href="https://www.theproblemsite.com/reference/science/technology/programming/one-dimension-two-dimensions"</a>
   * @param i - current block row
   * @param j - current block column
   * @param block - curent block
   * @return distance to the goal position
   */
  private int calculateDistance(int i, int j, int block) {
    int dX = (block - 1) / dimension; // x coordinate
    int dY = (block - 1) % dimension; // y coordinate
    return Math.abs(i - dX) + Math.abs(j - dY);
  }

  public int block(int i, int j) {
    return blocks[i][j];
  }

}
