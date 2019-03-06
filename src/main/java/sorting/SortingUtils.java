package sorting;

public class SortingUtils {

  public static <T extends Comparable<T>> boolean isLess(T first, T second) {
    return first != second && first.compareTo(second) < 0;
  }

  public static <T> void exchange(T[] array, int elementToSwap, int swapWith) {
    T temp = array[elementToSwap];
    array[elementToSwap] = array[swapWith];
    array[swapWith] = temp;

  }

}
