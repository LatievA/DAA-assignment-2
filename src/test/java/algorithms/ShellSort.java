package algorithms;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.function.IntFunction;

import static org.junit.jupiter.api.Assertions.*;

class ShellSortTest {

    /** Helper to test with different gap sequences */
    private <T extends Comparable<? super T>> void assertSorted(
            T[] input,
            IntFunction<int[]> gapSeq
    ) {
        T[] arr = Arrays.copyOf(input, input.length);
        ShellSort.shellSort(arr, gapSeq);
        for (int i = 0; i < arr.length - 1; i++) {
            assertTrue(arr[i].compareTo(arr[i + 1]) <= 0,
                    "Array not sorted: " + Arrays.toString(arr));
        }
    }

    @Test
    void testShellGaps() {
        Integer[] arr = {9, 7, 3, 1, 5, 2, 4, 6, 8, 0};
        assertSorted(arr, ShellSort::shellGaps);
    }

    @Test
    void testKnuthGaps() {
        Integer[] arr = {9, 7, 3, 1, 5, 2, 4, 6, 8, 0};
        assertSorted(arr, ShellSort::knuthGaps);
    }

    @Test
    void testSedgewickGaps() {
        Integer[] arr = {9, 7, 3, 1, 5, 2, 4, 6, 8, 0};
        assertSorted(arr, ShellSort::sedgewickGaps);
    }

    @Test
    void testEmptyArray() {
        Integer[] arr = {};
        assertSorted(arr, ShellSort::shellGaps);
    }

    @Test
    void testSingleElement() {
        Integer[] arr = {42};
        assertSorted(arr, ShellSort::knuthGaps);
    }

    @Test
    void testAlreadySorted() {
        Integer[] arr = {1, 2, 3, 4, 5};
        assertSorted(arr, ShellSort::sedgewickGaps);
    }

    @Test
    void testReverseOrder() {
        Integer[] arr = {5, 4, 3, 2, 1};
        assertSorted(arr, ShellSort::knuthGaps);
    }

    @Test
    void testWithDuplicates() {
        Integer[] arr = {5, 3, 5, 2, 2, 8, 1, 1};
        assertSorted(arr, ShellSort::shellGaps);
    }
}
