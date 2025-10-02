package metrics;

public class PerformanceTracker {
    public long comparisons = 0;
    public long swaps = 0;
    public long accesses = 0;   // total reads + writes
    public long allocations = 0;

    // Compare wrapper
    public <T extends Comparable<? super T>> int compare(T a, T b) {
        comparisons++;
        return a.compareTo(b);
    }

    // Record read
    public <T> T read(T value) {
        accesses++;
        return value;
    }

    // Record write
    public <T> T write(T value) {
        accesses++;
        return value;
    }

    // Record allocation
    public void allocate() {
        allocations++;
    }

    // Reset counters
    public void reset() {
        comparisons = 0;
        swaps = 0;
        accesses = 0;
        allocations = 0;
    }

    @Override
    public String toString() {
        return "comparisons=" + comparisons +
                ", swaps=" + swaps +
                ", accesses=" + accesses +
                ", allocations=" + allocations;
    }

    public String toCSV() {
        return comparisons + "," + swaps + "," + accesses + "," + allocations;
    }
}
