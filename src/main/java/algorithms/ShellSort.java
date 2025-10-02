package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.IntFunction;

public final class ShellSort {

    private ShellSort() {}

    /**
     * Generic Shellsort.
     * @param arr array to sort
     * @param gapSequence function that generates an int[] of gaps (descending order)
     */
    public static <T extends Comparable<? super T>> void shellSort(T[] arr, IntFunction<int[]> gapSequence) {
        int n = arr.length;
        int[] gaps = gapSequence.apply(n);

        for (int gap : gaps) {
            for (int i = gap; i < n; i++) {
                T temp = arr[i];
                int j = i;
                while (j >= gap && arr[j - gap].compareTo(temp) > 0) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                arr[j] = temp;
            }
        }
    }

    /** Convenience overload using Shell's original gaps */
    public static <T extends Comparable<? super T>> void shellSort(T[] arr) {
        shellSort(arr, ShellSort::shellGaps);
    }

    // ---------------- GAP SEQUENCES ---------------- //

    /** Shell's original: n/2, n/4, ..., 1 */
    public static int[] shellGaps(int n) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            list.add(gap);
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    /** Knuth’s sequence: 1, 4, 13, 40... (3*h+1). Used in reverse order. */
    public static int[] knuthGaps(int n) {
        ArrayList<Integer> list = new ArrayList<>();
        int h = 1;
        while (h < n) {
            list.add(h);
            h = 3 * h + 1;
        }
        Collections.reverse(list);
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    /** Sedgewick’s sequence (1986 mix of two formulas). */
    public static int[] sedgewickGaps(int n) {
        ArrayList<Integer> list = new ArrayList<>();
        int k = 0;
        while (true) {
            int gap1 = (int) (Math.pow(4, k) + 3 * Math.pow(2, k - 1) + 1);
            int gap2 = (int) (9 * Math.pow(4, k) - 9 * Math.pow(2, k) + 1);
            if (gap1 > 0 && gap1 < n) list.add(gap1);
            if (gap2 > 0 && gap2 < n) list.add(gap2);
            if (gap1 >= n && gap2 >= n) break;
            k++;
        }
        Collections.sort(list, Collections.reverseOrder());
        return list.stream().mapToInt(Integer::intValue).toArray();
    }
}
