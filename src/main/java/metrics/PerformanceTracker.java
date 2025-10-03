package metrics;

public class PerformanceTracker {
    private long comparisons = 0;
    private long swaps = 0;
    private long reads = 0;
    private long writes = 0;
    private long allocations = 0;

    private long startTime = 0;
    private long elapsedTime = 0;

    public void reset() {
        comparisons = swaps = reads = writes = allocations = 0;
        startTime = elapsedTime = 0;
    }

    // --- Counters ---
    public void compare() { comparisons++; }
    public void swap() { swaps++; }
    public void read() { reads++; }
    public void write() { writes++; }
    public void allocate() { allocations++; }

    // --- Timing ---
    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        if (startTime != 0) {
            elapsedTime = System.nanoTime() - startTime;
            startTime = 0;
        }
    }

    public long getElapsedTime() { return elapsedTime; }
    public double getElapsedMillis() { return elapsedTime / 1_000_000.0; }

    // --- Getters ---
    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
    public long getReads() { return reads; }
    public long getWrites() { return writes; }
    public long getAllocations() { return allocations; }

    @Override
    public String toString() {
        return String.format(
                "Comparisons=%d, Swaps=%d, Reads=%d, Writes=%d, Allocations=%d, Time=%.3f ms",
                comparisons, swaps, reads, writes, allocations, getElapsedMillis()
        );
    }
}

