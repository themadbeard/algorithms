package sorting;

/**
 * Heap sort implementation.
 *
 * In-place sorting algorithm with <b>Nlog(N)</b> worst-case.
 * <ul>
 *  <li>It's inner loop longer than quicksort's.</li>
 *  <li>Makes poor use of cache memory.</li>
 *  <li>Not stable.</li>
 * </ul>
 */
public class HeapSort {

  public static <T extends Comparable<T>> void sort(T[] array) {
    int N = array.length;

    for (int k = N / 2; k >= 1; k--) {
      sink(array, k, N);
    }

    while (N > 1) {
      exchange(array, 1, N);
      sink(array, 1, --N);
    }
  }

  //Parent key becomes smaller than one (or both) of its children's.
  //Exchange key in parent with key in larger child.

  //Top-down reheapify.
  private static <T extends Comparable<T>> void sink(T[] array, int k, int N) {
    while (2 * k <= N) {
      int j = 2 * k;
      if(j < N && isLess(array, j, j + 1)) j++;
      if(!isLess(array, k, j)) break;

      exchange(array, k, j);
      k = j;
    }
  }

  private static <T extends Comparable<T>> boolean isLess(T[] array, int i, int j) {
    T first = array[--i];
    T second = array[--j];
    return first != second && first.compareTo(second) < 0;
  }

  private static <T> void exchange(T[] array, int elementToSwap, int swapWith) {
    T temp = array[--elementToSwap];
    array[elementToSwap] = array[--swapWith];
    array[swapWith] = temp;

  }

}
