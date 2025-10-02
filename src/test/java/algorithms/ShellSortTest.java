package algorithms;

import metrics.PerformanceTracker;

import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ShellSortTest {

    @Test
    void testShellGapsSortsCorrectly() {
        Integer[] arr = {5, 2, 9, 1, 5, 6};
        Integer[] expected = arr.clone();
        Arrays.sort(expected);

        PerformanceTracker metrics = new PerformanceTracker();
        ShellSort.shellSort(arr, ShellSort::shellGaps, metrics);

        assertArrayEquals(expected, arr, "Array should be sorted using Shell gaps");
        assertTrue(metrics.comparisons > 0, "Should have some comparisons");
        assertTrue(metrics.arrayAccesses > 0, "Should have array accesses");
    }

    @Test
    void testKnuthGapsSortsCorrectly() {
        Integer[] arr = {3, 0, -1, 8, 7};
        Integer[] expected = arr.clone();
        Arrays.sort(expected);

        PerformanceTracker metrics = new PerformanceTracker();
        ShellSort.shellSort(arr, ShellSort::knuthGaps, metrics);

        assertArrayEquals(expected, arr, "Array should be sorted using Knuth gaps");
        assertTrue(metrics.swaps >= 0, "Swaps metric should be recorded");
    }

    @Test
    void testSedgewickGapsSortsCorrectly() {
        Integer[] arr = {10, 7, 3, 8, 2};
        Integer[] expected = arr.clone();
        Arrays.sort(expected);

        PerformanceTracker metrics = new PerformanceTracker();
        ShellSort.shellSort(arr, ShellSort::sedgewickGaps, metrics);

        assertArrayEquals(expected, arr, "Array should be sorted using Sedgewick gaps");
        assertTrue(metrics.allocations >= arr.length, "Allocations should count array creation");
    }

    @Test
    void testEmptyArray() {
        Integer[] arr = {};
        PerformanceTracker metrics = new PerformanceTracker();
        ShellSort.shellSort(arr, ShellSort::knuthGaps, metrics);

        assertEquals(0, arr.length, "Empty array should remain empty");
        assertTrue(metrics.comparisons == 0, "No comparisons for empty array");
    }

    @Test
    void testSingleElementArray() {
        Integer[] arr = {42};
        PerformanceTracker metrics = new PerformanceTracker();
        ShellSort.shellSort(arr, ShellSort::shellGaps, metrics);

        assertEquals(42, arr[0], "Single element array should remain unchanged");
        assertTrue(metrics.comparisons == 0, "No comparisons needed for single element");
    }
}
