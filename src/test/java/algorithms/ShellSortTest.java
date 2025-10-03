package algorithms;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import metrics.*;

import static org.junit.jupiter.api.Assertions.*;

class ShellSortTest {

    // Provide all algorithms for parameterized tests
    public static Stream<Map.Entry<String, java.util.function.Function<Integer, int[]>>> algorithms() {
        Map<String, java.util.function.Function<Integer, int[]>> map = new LinkedHashMap<>();
        map.put("Shell", ShellSort::shellGaps);
        map.put("Knuth", ShellSort::knuthGaps);
        map.put("Sedgewick", ShellSort::sedgewickGaps);
        map.put("Pratt", ShellSort::prattGaps);
        map.put("Tokuda", ShellSort::tokudaGaps);
        return map.entrySet().stream();
    }

    private boolean isSorted(Integer[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) return false;
        }
        return true;
    }

    @ParameterizedTest(name = "{0} gap sequence sorts correctly")
    @MethodSource("algorithms")
    void testSortsCorrectly(Map.Entry<String, java.util.function.Function<Integer, int[]>> algo) {
        PerformanceTracker metrics = new PerformanceTracker();
        Integer[] arr = {5, 4, 3, 2, 1};
        metrics.allocate(); // count input array allocation

        ShellSort.shellSort(arr, Comparator.naturalOrder(), algo.getValue(), metrics);

        assertTrue(isSorted(arr), algo.getKey() + " did not sort correctly");
        assertTrue(metrics.getComparisons() > 0, "Comparisons should be > 0");
        assertTrue(metrics.getAllocations() > 0, "Allocations should count array creation");
        assertTrue(metrics.getElapsedTime() > 0, "Timer should measure elapsed time");
    }

    @Test
    void testAlreadySortedArray() {
        PerformanceTracker metrics = new PerformanceTracker();
        Integer[] arr = {1, 2, 3, 4, 5};
        metrics.allocate();

        ShellSort.shellSort(arr, Comparator.naturalOrder(), ShellSort::tokudaGaps, metrics);

        assertTrue(isSorted(arr));
        assertEquals(1, metrics.getAllocations(), "Should count initial array allocation");
    }

    @Test
    void testEmptyArray() {
        PerformanceTracker metrics = new PerformanceTracker();
        Integer[] arr = {};
        metrics.allocate();

        ShellSort.shellSort(arr, Comparator.naturalOrder(), ShellSort::knuthGaps, metrics);

        assertTrue(isSorted(arr));
        assertEquals(1, metrics.getAllocations());
    }
}
