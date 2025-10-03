package metrics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class CsvExporter {

    private CsvExporter() {}

    public static void exportHeader(String filename) {
        try (FileWriter fw = new FileWriter(filename, false)) {
            fw.write("Algorithm,Comparisons,Swaps,Reads,Writes,Allocations,Time(ms)\n");
        } catch (IOException e) {
            throw new RuntimeException("Failed to write header to CSV", e);
        }
    }

    public static void exportMetrics(String filename, String algo, int size, PerformanceTracker metrics) {
        try (FileWriter fw = new FileWriter(filename, true)) {
            fw.write(String.format(
                    "%s,%d,%d,%d,%d,%d,%.3f\n",
                    algo, metrics.getComparisons(), metrics.getSwaps(),
                    metrics.getReads(), metrics.getWrites(), metrics.getAllocations(),
                    metrics.getElapsedMillis()
            ));
        } catch (IOException e) {
            throw new RuntimeException("Failed to write metrics to CSV", e);
        }
    }
}
