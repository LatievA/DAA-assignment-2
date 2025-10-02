package metrics;

public class PerformanceTracker {
    public long comparisons = 0;
    public long swaps = 0;
    public long arrayAccesses = 0;
    public long allocations = 0;

    public void reset() {
        comparisons = swaps = arrayAccesses = allocations = 0;
    }

    @Override
    public String toString() {
        return "Comparisons=" + comparisons +
                ", Swaps=" + swaps +
                ", Accesses=" + arrayAccesses +
                ", Allocations=" + allocations;
    }

    public String toCSV() {
        return comparisons + "," + swaps + "," + arrayAccesses + "," + allocations;
    }

    public static String csvHeader() {
        return "comparisons,swaps,array_accesses,allocations";
    }
}
