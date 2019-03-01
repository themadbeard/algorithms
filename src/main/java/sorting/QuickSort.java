package sorting;


import edu.princeton.cs.algs4.StdRandom;

public class QuickSort {

    public static <T extends Comparable<T>> void sort(T[] unsorted) {
        StdRandom.shuffle(unsorted); //shuffle is needed for performance guarantee
        sort(unsorted, 0, unsorted.length - 1);
    }

    private static <T extends Comparable<T>> void sort(T[] array, int lo, int hi) {
        if(lo >= hi) return;
        int j = partitioning(array, lo, hi);
        sort(array, lo, j -1);
        sort(array, j + 1, hi);

    }

    private static <T extends Comparable<T>> int partitioning(T[] array, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        while(true) {
            while(isLess(array[++i], array[lo])) { // if keys are equal we terminate loop
                if(i == hi) {
                    break;
                }
            }

            while (isLess(array[lo], array[--j])) { // and there
                if(j == lo) {
                    break;
                }
            }

            //check pointers cross
            if(i >= j) {
                break;
            }

            exchange(array, i, j);

        }

        exchange(array, lo, j);

        return j;
    }

    private static <T extends Comparable<T>> boolean isLess(T first, T second) {
        return first != second && first.compareTo(second) < 0;
    }

    private static <T> void exchange(T[] array, int elementToSwap, int swapWith) {
        T temp = array[elementToSwap];
        array[elementToSwap] = array[swapWith];
        array[swapWith] = temp;

    }
}
