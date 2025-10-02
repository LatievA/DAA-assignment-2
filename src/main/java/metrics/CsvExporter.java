package metrics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class CsvExporter {

    private CsvExporter() {}

    public static void exportHeader(String filename) {
        try (FileWriter fw = new FileWriter(filename, false)) {
            fw.write("algorithm,array_size,comparisons,swaps,array_accesses,allocations\n");
        } catch (IOException e) {
            throw new RuntimeException("Failed to write header to CSV", e);
        }
    }

    public static void exportMetrics(String filename, String algo, int size, PerformanceTracker metrics) {
        try (FileWriter fw = new FileWriter(filename, true)) {
            fw.write(algo + "," + size + "," + metrics.toCSV() + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Failed to write metrics to CSV", e);
        }
    }
}
