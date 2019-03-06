package data_structure;

import static sorting.SortingUtils.exchange;
import static sorting.SortingUtils.isLess;

import java.util.NoSuchElementException;

/**
 * Class for representation priority heap-ordered queue.
 * Uses arithmetic operations for inserting and searching.
 * Parent of node at k is at k / 2. Child nodes for k are at  2k and (2k + 1)
 * @param <Key> comparable element
 */
@SuppressWarnings("unchecked")
public class BinaryHeap<Key extends Comparable<Key>> {

  private Key[] container;
  private int N;

  public boolean isEmpty() {
    return N == 0;
  }

  public void insert(Key k){
    container[++N] = k;
    swim(N);

    if(N == container.length) {
      resize(2 * container.length);
    }

  }

  public Key delMax() {
    if(isEmpty()) {
      throw new NoSuchElementException();
    }

    Key max = container[1];
    exchange(container, 1, N--);
    sink(1);
    container[N + 1] = null;

    if(N == container.length / 4) {
      resize(container.length / 2);
    }

    return max;
  }

  private void swim(int k){
    while (k > 1 && isLess(k / 2, k)) {
      exchange(container, k, k / 2);
      k = k / 2;
    }
  }

  private void sink(int k){
    while (2 * k <= N) {
      int j = 2 * k;
      if(j < N && isLess(j, j + 1)) j++;
      if(!isLess(k, j)) break;

      exchange(container, k, j);
      k = j;
    }
  }

  private void resize(int capacity) {
    Key[] newContainer = (Key[]) new Object[capacity];

    for (int i = 0; i < N; i++) {
      newContainer[i] = container[i];
    }
    container = newContainer;
  }

  public int size() {
    return N;
  }

  public Key[] toArray() {
    return container;
  }
}
