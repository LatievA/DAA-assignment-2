package client;

import metrics.*;
import algorithms.ShellSort;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int size = 1000; // array size
        int seed = 42;   // for reproducibility
        Integer[] baseInput = generateRandomArray(size, seed);

        // prepare CSV file
        String filename = "shellsort_metrics.csv";
        CsvExporter.exportHeader(filename);

        runOne("Shell", baseInput, ShellSort::shellGaps, filename);
        runOne("Knuth", baseInput, ShellSort::knuthGaps, filename);
        runOne("Sedgewick", baseInput, ShellSort::sedgewickGaps, filename);

        System.out.println("Results written to " + filename);
    }

    private static void runOne(
            String name,
            Integer[] baseInput,
            java.util.function.IntFunction<int[]> gapFunc,
            String filename
    ) {
        Integer[] arr = Arrays.copyOf(baseInput, baseInput.length);
        PerformanceTracker metrics = new PerformanceTracker();

        ShellSort.shellSort(arr, gapFunc, metrics);

        // sanity check
        if (!isSorted(arr)) {
            throw new IllegalStateException(name + " failed to sort correctly!");
        }

        System.out.printf("%s: %s%n", name, metrics);

        CsvExporter.exportMetrics(filename, name, arr.length, metrics);
    }

    private static Integer[] generateRandomArray(int n, int seed) {
        Random rand = new Random(seed);
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(n * 10);
        }
        return arr;
    }

    private static boolean isSorted(Integer[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) return false;
        }
        return true;
    }
}
