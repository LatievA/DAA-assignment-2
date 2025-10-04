# 🌀 Shellsort

A comprehensive Java-based study of **Shellsort** algorithm variations, featuring five classic and modern gap sequences — **Shell**, **Knuth**, **Sedgewick**, **Pratt**, and **Tokuda** — benchmarked for empirical and theoretical analysis.

**Pratt** and **Tokuda** variations weren't actualy requered by the assignment criteria but were impelemented by me out of interest.

This project is designed for algorithmic research, educational purposes, and performance analysis under controlled experimental conditions.

---

## 📘 Overview

Shellsort is an in-place comparison-based sorting algorithm that improves upon insertion sort by allowing exchanges of elements far apart.  
Its performance depends heavily on the **gap sequence**, making it one of the most tunable and insightful algorithms for performance analysis.

This project:
- Implements multiple gap sequence strategies.
- Collects detailed runtime metrics.
- Outputs CSV benchmark data for later visualization.
- Includes JUnit 5 tests and Maven CI integration.
- Supports timing, operation counting, and CSV export.

---

## ⚙️ Features

- ✅ Modular **Shellsort** implementation using functional gap providers.
- 📊 Built-in **metrics system** for comparisons, swaps, reads, writes, allocations, and time.
- 🧮 **Benchmarking suite** with configurable warm-up runs.
- 🧪 **JUnit 5 test coverage** for all gap sequences.
- 📁 **CSV export** for easy analysis in Python, R, or Excel.
- 🔁 Extensible design — easy to add new gap sequences or alternative algorithms.

---

## 🧠 Algorithms Implemented

| Algorithm | Description | Complexity (Worst) | Space |
|------------|--------------|--------------------|--------|
| **Shell** | Original halving gaps | O(n²) | O(1) |
| **Knuth** | h = 3h + 1 sequence | Θ(n^1.5) | O(1) |
| **Sedgewick** | Analytically optimized sequence | O(n^(4/3)) | O(1) |
| **Pratt** | Gaps from powers of 2 and 3 | O(n log² n) | O(1) |
| **Tokuda** | Empirically tuned gaps | ≈O(n^1.3) | O(1) |
| **Heapsort** | Binary heap-based | Θ(n log n) | O(1) |

---

## 🧩 Link to the docs
[HeapSort Report](docs)

