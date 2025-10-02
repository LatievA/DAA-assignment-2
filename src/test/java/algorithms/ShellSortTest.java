package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.function.IntFunction;

import static org.junit.jupiter.api.Assertions.*;

class ShellSortTest {

    record Algo(String name, IntFunction<int[]> gaps) {}

    static List<Algo> algorithms() {
        return Arrays.asList(
                new Algo("Shell", ShellSort::shellGaps),
                new Algo("Knuth", ShellSort::knuthGaps),
                new Algo("Sedgewick", ShellSort::sedgewickGaps),
                new Algo("Pratt", ShellSort::prattGaps),
                new Algo("Tokuda", ShellSort::tokudaGaps)
        );
    }

    @ParameterizedTest
    @MethodSource("algorithms")
    void testSortCorrectness(Algo algo) {
        Integer[] arr = {5, 2, 9, 1, 5, 6};
        Integer[] expected = arr.clone();
        Arrays.sort(expected);

        PerformanceTracker metrics = new PerformanceTracker();
        ShellSort.shellSort(arr, algo.gaps(), metrics);

        assertArrayEquals(expected, arr, algo.name() + " should sort array correctly");
        assertTrue(metrics.comparisons > 0, algo.name() + " should make comparisons");
        assertTrue(metrics.accesses > 0, algo.name() + " should have array accesses");
    }

    @ParameterizedTest
    @MethodSource("algorithms")
    void testEmptyArray(Algo algo) {
        Integer[] arr = {};
        PerformanceTracker metrics = new PerformanceTracker();
        ShellSort.shellSort(arr, algo.gaps(), metrics);

        assertEquals(0, arr.length, "Empty array should remain empty");
        assertEquals(0, metrics.comparisons, algo.name() + " should do no comparisons on empty array");
    }

    @ParameterizedTest
    @MethodSource("algorithms")
    void testSingleElement(Algo algo) {
        Integer[] arr = {42};
        PerformanceTracker metrics = new PerformanceTracker();
        ShellSort.shellSort(arr, algo.gaps(), metrics);

        assertEquals(42, arr[0], "Single element should remain unchanged");
        assertEquals(0, metrics.comparisons, algo.name() + " should do no comparisons on single element");
    }
}
