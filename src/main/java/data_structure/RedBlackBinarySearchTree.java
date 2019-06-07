package data_structure;

public class RedBlackBinarySearchTree<Key extends Comparable<Key>, Value> {

  public static final boolean RED = true;
  public static final boolean BLACK = false;

  private Node root; //root of BST

  private class Node {

    private Key key;
    private Value value;
    private Node left, right;
    private boolean color;
    private int size;

    public Node(Key key, Value value, boolean color, int size) {
      this.key = key;
      this.value = value;
      this.color = color;
      this.size = size;
    }
  }

  public int size() {
    return size(root);
  }

  public boolean isEmpty() {
    return root == null;
  }

  public Value get(Key key) {
    if (key == null) {
      throw new IllegalArgumentException("Key can't be null");
    }
    return get(root, key);
  }

  private Value get(Node x, Key key) {
    while (x != null) {
      int cmp = key.compareTo(x.key);
      if (cmp < 0) {
        x = x.left;
      } else if (cmp > 0) {
        x = x.right;
      } else {
        return x.value;
      }
    }
    return null;
  }

  public boolean contains(Key key) {
    return get(key) != null;
  }

  public void put(Key key, Value val) {
    if (key == null) {
      throw new IllegalArgumentException("Key can't be null");
    }

    if (val == null) {
      delete(key);
      return;
    }

    root = put(root, key, val);
    root.color = BLACK;

  }

  // insert the key-value pair in the subtree rooted at h
  private Node put(Node h, Key key, Value val) {
    if (h == null) {
      return new Node(key, val, RED, 1);
    }

    int cmp = key.compareTo(h.key);
    if (cmp < 0) h.left = put(h.left, key, val);
    else if(cmp > 0) h.right = put(h.right, key, val);
    else h.value = val;

    h = fixAfterInsertion(h);

    h.size = h.right.size + h.left.size;

    return h;
  }

  private Node fixAfterInsertion(Node h) {
    if (isRed(h.right) && !isRed(h.right)) {
      h = rotateLeft(h);
    }
    if (isRed(h.left) && isRed(h.left.left)) {
      h = rotateRight(h);
    }
    if (isRed(h.left) && isRed(h.right)) {
      flipColors(h);
    }
    return h;
  }

  public void delete(Key key) {

  }

  /**********************************************************************
   * Red-black tree helper functions.
   * Maintains a symmetric order and perfect black balance
   **********************************************************************/

  //Make a left-leaning link lean to the right
  private Node rotateLeft(Node h) {
    Node x = h.right;
    h.right = x.left;
    x.left = h;
    x.color = h.color;
    h.color = RED;

    return x;
  }

  // Make a right-leaning link lean to the left
  private Node rotateRight(Node h) {
    Node x = h.left;
    h.left = x.right;
    x.right = h;
    x.color = h.color;
    h.color = RED;

    return x;
  }

  private void flipColors(Node h) {
    h.color = RED;
    h.left.color = BLACK;
    h.right.color = BLACK;
  }

  private boolean isRed(Node x) {
    if (x == null) return false;
    return x.color == RED;
  }

  private int size(Node x) {
    if (x == null) return 0;
    return x.size;
  }

}
