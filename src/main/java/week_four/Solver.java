package week_four;

import edu.princeton.cs.algs4.MinPQ;
import java.util.Stack;

public class Solver {

  private Node lastNode;

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {
    if(initial == null) {
      throw new IllegalArgumentException();
    }

    MinPQ<Node> nodes = new MinPQ<>();
    nodes.insert(new Node(initial));

    MinPQ<Node> twinNodes = new MinPQ<>();
    twinNodes.insert(new Node(initial.twin()));

    while (true) {
      lastNode = expand(nodes);
      if (lastNode != null || expand(twinNodes) != null) {
        return;
      }
    }
  }

  private Node expand(MinPQ<Node> Nodes) {
    if (Nodes.isEmpty()) {
      return null;
    }

    Node bestNode = Nodes.delMin();

    if (bestNode.board.isGoal()) {
      return bestNode;
    }

    for (Board neighbor : bestNode.board.neighbors()) {
      // compare init state
      if (bestNode.previous == null || !neighbor.equals(bestNode.previous.board)) {
        Nodes.insert(new Node(neighbor, bestNode));
      }
    }
    return null;
  }

  public boolean isSolvable() {
    return lastNode != null;
  }

  public int moves() {
    return isSolvable() ? lastNode.numNodes : -1;
  }

  public Iterable<Board> solution() {
    if (!isSolvable()) {
      return null;
    }

    Stack<Board> Nodes = new Stack<>();
    while (lastNode != null) {
      Nodes.push(lastNode.board);
      lastNode = lastNode.previous;
    }

    return Nodes;
  }

  private class Node implements Comparable<Node> {

    private Node previous;
    private Board board;
    private int numNodes = 0;

    public Node(Board board) {
      this.board = board;
    }

    public Node(Board board, Node previous) {
      this.board = board;
      this.previous = previous;
      this.numNodes = previous.numNodes + 1;
    }

    public int compareTo(Node Node) {
      return (this.board.manhattan() - Node.board.manhattan()) + (this.numNodes - Node.numNodes);
    }
  }

}
