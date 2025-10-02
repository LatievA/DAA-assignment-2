package algorithms;

import metrics.PerformanceTracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.IntFunction;

public final class ShellSort {

    private ShellSort() {}

    public static <T extends Comparable<? super T>> void shellSort(
            T[] arr, IntFunction<int[]> gapSequence, PerformanceTracker metrics) {

        metrics.allocations++; // counting gaps array allocation
        int n = arr.length;
        int[] gaps = gapSequence.apply(n);

        for (int gap : gaps) {
            for (int i = gap; i < n; i++) {
                metrics.arrayAccesses++; // read arr[i]
                T temp = arr[i];
                metrics.allocations++;   // temp variable
                int j = i;

                while (j >= gap) {
                    metrics.arrayAccesses += 2; // read arr[j-gap], read temp
                    metrics.comparisons++;
                    if (arr[j - gap].compareTo(temp) <= 0) break;

                    metrics.arrayAccesses += 2; // write arr[j], read arr[j-gap]
                    arr[j] = arr[j - gap];
                    metrics.swaps++;
                    j -= gap;
                }

                metrics.arrayAccesses++; // write arr[j]
                arr[j] = temp;
            }
        }
    }

    /** Default (Shell’s sequence) */
    public static <T extends Comparable<? super T>> void shellSort(T[] arr, PerformanceTracker metrics) {
        shellSort(arr, ShellSort::shellGaps, metrics);
    }

    // ---------------- GAP SEQUENCES ---------------- //

    public static int[] shellGaps(int n) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            list.add(gap);
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

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
