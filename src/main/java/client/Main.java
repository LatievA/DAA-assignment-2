package client;

import metrics.*;
import algorithms.ShellSort;

import java.util.*;
import java.util.function.IntFunction;

public class Main {
    private record Algo(String name, IntFunction<int[]> gaps) {}

    public static void main(String[] args) {
        int size = 2000; // array size
        int seed = 42;   // reproducibility
        Integer[] baseInput = generateRandomArray(size, seed);

        // Prepare CSV file
        String filename = "shellsort_metrics.csv";
        CsvExporter.exportHeader(filename);

        List<Algo> algos = Arrays.asList(
                new Algo("Shell", ShellSort::shellGaps),
                new Algo("Knuth", ShellSort::knuthGaps),
                new Algo("Sedgewick", ShellSort::sedgewickGaps),
                new Algo("Pratt", ShellSort::prattGaps),
                new Algo("Tokuda", ShellSort::tokudaGaps)
        );

        for (Algo algo : algos) {
            runOne(algo.name, baseInput, algo.gaps, filename);
        }

        System.out.println("Results written to " + filename);
    }

    private static void runOne(
            String name,
            Integer[] baseInput,
            IntFunction<int[]> gapFunc,
            String filename
    ) {
        Integer[] arr = Arrays.copyOf(baseInput, baseInput.length);
        PerformanceTracker metrics = new PerformanceTracker();

        ShellSort.shellSort(arr, gapFunc, metrics);

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
