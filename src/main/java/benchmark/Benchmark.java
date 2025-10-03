package benchmark;

import metrics.*;
import algorithms.ShellSort;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.function.Function;

public class Benchmark {
    private static final int WARMUP_RUNS = 5;

    public static void main(String[] args) throws IOException {
        int n = 10_000; // array size
        try (FileWriter writer = new FileWriter("results.csv")) {
            writer.write("Algorithm,Comparisons,Swaps,Reads,Writes,Allocations,Time(ms)\n");

            Benchmark.benchmark("Shell", ShellSort::shellGaps, n, writer);
            Benchmark.benchmark("Knuth", ShellSort::knuthGaps, n, writer);
            Benchmark.benchmark("Sedgewick", ShellSort::sedgewickGaps, n, writer);
            Benchmark.benchmark("Pratt", ShellSort::prattGaps, n, writer);
            Benchmark.benchmark("Tokuda", ShellSort::tokudaGaps, n, writer);
        }

        System.out.println("Benchmark completed. Results saved to results.csv");
    }

    public static void benchmark(String name,
                                 Function<Integer, int[]> gapFunction,
                                 int n,
                                 Writer writer) throws IOException {

        // Prepare input
        Integer[] base = generateRandomArray(n);

        // Warm-up (no metrics, just runs)
        for (int i = 0; i < WARMUP_RUNS; i++) {
            Integer[] copy = Arrays.copyOf(base, base.length);
            ShellSort.shellSort(copy, Comparator.naturalOrder(), gapFunction, new PerformanceTracker());
        }

        // Real run with metrics
        Integer[] arr = Arrays.copyOf(base, base.length);
        PerformanceTracker metrics = new PerformanceTracker();
        metrics.allocate(); // count test array creation

        ShellSort.shellSort(arr, Comparator.naturalOrder(), gapFunction, metrics);

        // Write to CSV
        writer.write(String.format(
                "%s,%d,%d,%d,%d,%d,%.3f\n",
                name,
                metrics.getComparisons(),
                metrics.getSwaps(),
                metrics.getReads(),
                metrics.getWrites(),
                metrics.getAllocations(),
                metrics.getElapsedMillis()
        ));
    }

    private static Integer[] generateRandomArray(int n) {
        Random rnd = new Random(42);
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) arr[i] = rnd.nextInt(n * 10);
        return arr;
    }
}

