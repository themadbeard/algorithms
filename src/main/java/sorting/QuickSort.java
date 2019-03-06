package sorting;


import static sorting.SortingUtils.exchange;
import static sorting.SortingUtils.isLess;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Quick-sort algorithm implementation.
 * In-place sorting. Most popular sorting algorithm.
 * <ul>
 *   <li>Quadratic time in worst-case</li>
 *   <li>Need optimization for duplicate keys</li>
 * </ul>
 */
public class QuickSort {

    public static <T extends Comparable<T>> void sort(T[] unsorted) {
        StdRandom.shuffle(unsorted); //shuffle is needed for performance guarantee
        sort(unsorted, 0, unsorted.length - 1);
    }

    /**
     * Method for solving selection problem (find k-th largest element)
     * @param unsorted array to research
     * @param k the power of the element to search (usual it's min/max/median)
     * @param <T>
     * @return
     */
    private static <T extends Comparable<T>> T selection(T[] unsorted, int k) {
        StdRandom.shuffle(unsorted);
        int lo = 0; int hi = unsorted.length - 1;

        while (lo < hi) {
            int j = partitioning(unsorted, lo, hi);
            if (j < k) {
                lo = j + 1;
            } else if (j > k) {
                hi = j - 1;
            } else {
                return unsorted[k];
            }
        }
        return unsorted[k];
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
}
