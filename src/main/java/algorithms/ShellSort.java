package algorithms;

import metrics.PerformanceTracker;

import java.util.*;
import java.util.function.Function;

public final class ShellSort {

    private ShellSort() {}

    public static <T extends Comparable<? super T>> void shellSort(
            T[] arr, Comparator<? super T> cmp,
            Function<Integer, int[]> gapFunction,
            PerformanceTracker metrics) {

        metrics.reset();
        metrics.startTimer();

        int[] gaps = gapFunction.apply(arr.length);
        metrics.allocate();
        for (int gap : gaps) {
            for (int i = gap; i < arr.length; i++) {
                T temp = arr[i];
                metrics.read();
                int j = i;
                while (j >= gap && cmp.compare(arr[j - gap], temp) > 0) {
                    metrics.compare();
                    arr[j] = arr[j - gap];
                    metrics.write();
                    j -= gap;
                }
                arr[j] = temp;
                metrics.write();
            }
        }

        metrics.stopTimer();
    }

    // ---- Gap sequences ----

    // Original Shell gaps: n/2, n/4, ..., 1
    public static int[] shellGaps(int n) {
        List<Integer> gaps = new ArrayList<>();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            gaps.add(gap);
        }
        return toArray(gaps);
    }

    // Knuth gaps: 1, 4, 13, 40, ...
    public static int[] knuthGaps(int n) {
        List<Integer> gaps = new ArrayList<>();
        int h = 1;
        while (h < n) {
            gaps.add(h);
            h = 3 * h + 1;
        }
        Collections.reverse(gaps);
        return toArray(gaps);
    }

    // Sedgewick gaps
    public static int[] sedgewickGaps(int n) {
        List<Integer> gaps = new ArrayList<>();
        int k = 0;
        int gap;
        do {
            if (k % 2 == 0) {
                gap = 9 * ((1 << (2 * k)) - (1 << k)) + 1;
            } else {
                gap = 8 * (1 << (2 * k)) - 6 * (1 << (k + 1)) + 1;
            }
            if (gap < n) gaps.add(gap);
            k++;
        } while (gap < n);
        Collections.reverse(gaps);
        return toArray(gaps);
    }

    // Pratt gaps (all numbers of form 2^p * 3^q < n)
    public static int[] prattGaps(int n) {
        Set<Integer> gaps = new HashSet<>();
        for (int p = 1; p < n; p *= 2) {
            for (int q = p; q < n; q *= 3) {
                gaps.add(q);
            }
        }
        List<Integer> sorted = new ArrayList<>(gaps);
        Collections.sort(sorted, Collections.reverseOrder());
        return toArray(sorted);
    }

    // Tokuda gaps
    public static int[] tokudaGaps(int n) {
        List<Integer> gaps = new ArrayList<>();
        int k = 1;
        int gap;
        do {
            gap = (int) Math.ceil((Math.pow(9, k) - Math.pow(4, k)) / (5.0 * Math.pow(4, k - 1)));
            if (gap < n) gaps.add(gap);
            k++;
        } while (gap < n);
        Collections.reverse(gaps);
        return toArray(gaps);
    }

    // Utility: convert list → int[]
    private static int[] toArray(List<Integer> list) {
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) arr[i] = list.get(i);
        return arr;
    }
}
