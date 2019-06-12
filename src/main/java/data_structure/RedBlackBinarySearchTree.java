package data_structure;

import java.util.NoSuchElementException;

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

    Node(Key key, Value value, boolean color, int size) {
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
    if (cmp < 0) {
      h.left = put(h.left, key, val);
    } else if (cmp > 0) {
      h.right = put(h.right, key, val);
    } else {
      h.value = val;
    }

    h = balance(h);

    return h;
  }

  private Node balance(Node h) {
    if (isRed(h.right) && !isRed(h.right)) {
      h = rotateLeft(h);
    }
    if (isRed(h.left) && isRed(h.left.left)) {
      h = rotateRight(h); //balance 4-node
    }
    if (isRed(h.left) && isRed(h.right)) {
      flipColors(h); //split 4-node
    }

    h.size = h.right.size + h.left.size;

    return h;
  }

  /***************************************************************************
   *  Red-black tree deletion.
   ***************************************************************************/

  /**
   * Removes the smallest key and associated value from the symbol table.
   *
   * @throws NoSuchElementException if the symbol table is empty
   */
  public void deleteMin() {
    if (isEmpty()) {
      throw new NoSuchElementException("BST underflow");
    }

    // if both children of root are black, set root to red
    if (!isRed(root.left) && !isRed(root.right)) {
      root.color = RED;
    }

    root = deleteMin(root);
    if (!isEmpty()) {
      root.color = BLACK;
    }
  }

  // delete the key-value pair with the minimum key rooted at h
  private Node deleteMin(Node h) {
    if (h.left == null) {
      return null;
    }

    if (!isRed(h.left) && !isRed(h.left.left)) {
      h = moveRedLeft(h);
    }

    h.left = deleteMin(h.left);
    return balance(h);
  }

  /**
   * Removes the largest key and associated value from the symbol table.
   *
   * @throws NoSuchElementException if the symbol table is empty
   */
  public void deleteMax() {
    if (isEmpty()) {
      throw new NoSuchElementException("BST underflow");
    }

    // if both children of root are black, set root to red
    if (!isRed(root.left) && !isRed(root.right)) {
      root.color = RED;
    }

    root = deleteMax(root);
    if (!isEmpty()) {
      root.color = BLACK;
    }
  }

  // delete the key-value pair with the maximum key rooted at h
  private Node deleteMax(Node h) {
    if (isRed(h.left)) {
      h = rotateRight(h);
    }

    if (h.right == null) {
      return null;
    }

    if (!isRed(h.right) && !isRed(h.right.left)) {
      h = moveRedRight(h);
    }

    h.right = deleteMax(h.right);

    return balance(h);
  }

  public void delete(Key key) {
    if (key == null) {
      throw new IllegalArgumentException("Argument to delete() is null");
    }

    if (!contains(key)) {
      return;
    }

    if (!isRed(root.left) && !isRed(root.right)) {
      root.color = RED;
    }

    root = delete(root, key);
    if (!isEmpty()) {
      root.color = BLACK;
    }

  }

  private Node delete(Node h, Key key) {
    if (key.compareTo(h.key) < 0) { //if key is smaller, than current node key
      if (!isRed(h.left) && !isRed(h.left.left)) {
        //rotate tree if it needed
        h = moveRedLeft(h);
      }

      //and continue searching in the left subtree
      h.left = delete(h.left, key);
    } else {
      if (isRed(h.left)) {
        h = rotateLeft(h);
      }

      //at this step left subtree should be null

      //Right subtree is empty and we found key, so just remove it
      if (key.compareTo(h.key) == 0 && h.right == null) {
        return null;
      }

      if (!isRed(h.right) && !isRed(h.right.left)) {
        h = moveRedRight(h);
      }

      if (key.compareTo(h.key) == 0) {
        //we found needed key. replace it with the min key in subtree.
        Node x = min(h.right);
        h.key = x.key;
        h.value = x.value;

        //after replacing we need to remove that min, to fix duplicates of the same key.
        h.right = deleteMin(h.right);
      } else {
        //continue searching of the needed key in the right subtree.
        h.right = delete(h.right, key);
      }
    }

    //balance subtree after deletion at every recursive step
    return balance(h);
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

  //One of the links was red, so we need to color them black, and their parents will be red.
  private void flipColors(Node h) {
    h.color = RED;
    h.left.color = BLACK;
    h.right.color = BLACK;
  }

  // Assuming that h is red and both h.left and h.left.left
  // are black, make h.left or one of its children red.
  private Node moveRedLeft(Node h) {
    flipColors(h);
    if (isRed(h.right.left)) {
      h.right = rotateRight(h.right);
      h = rotateLeft(h);
      flipColors(h);
    }
    return h;
  }

  // Assuming that h is red and both h.right and h.right.left
  // are black, make h.right or one of its children red.
  private Node moveRedRight(Node h) {
    flipColors(h);
    if (isRed(h.left.left)) {
      h = rotateRight(h);
      flipColors(h);
    }
    return h;
  }

  private boolean isRed(Node x) {
    if (x == null) {
      return false;
    }
    return x.color == RED;
  }

  private int size(Node x) {
    if (x == null) {
      return 0;
    }
    return x.size;
  }

  /***************************************************************************
   *  Ordered symbol table methods.
   ***************************************************************************/

  /**
   * Returns the smallest key in the symbol table.
   *
   * @return the smallest key in the symbol table
   * @throws NoSuchElementException if the symbol table is empty
   */
  public Key min() {
    if (isEmpty()) {
      throw new NoSuchElementException("calls min() with empty symbol table");
    }
    return min(root).key;
  }

  // the smallest key in subtree rooted at x; null if no such key
  private Node min(Node x) {
    // assert x != null;
    if (x.left == null) {
      return x;
    } else {
      return min(x.left);
    }
  }

  /**
   * Returns the largest key in the symbol table.
   *
   * @return the largest key in the symbol table
   * @throws NoSuchElementException if the symbol table is empty
   */
  public Key max() {
    if (isEmpty()) {
      throw new NoSuchElementException("calls max() with empty symbol table");
    }
    return max(root).key;
  }

  // the largest key in the subtree rooted at x; null if no such key
  private Node max(Node x) {
    // assert x != null;
    if (x.right == null) {
      return x;
    } else {
      return max(x.right);
    }
  }

}
