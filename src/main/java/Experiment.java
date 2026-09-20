import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Experiment {

    private static final Random RANDOM = new Random();

    public static void runExperiments() {

        new File("results").mkdirs();

        try (FileWriter writer =
                     new FileWriter("results/results.csv")) {

            writer.write(
                    "algorithm,input_type,n,time_ns,max_depth,comparisons\n"
            );

            int[] sizes = {100, 1000, 10000};

            for (int size : sizes) {

                int[] random =
                        createRandomArray(size);

                int[] sorted =
                        createSortedArray(size);

                int[] reverse =
                        createReverseArray(size);

                int[] duplicates =
                        createDuplicateArray(size);

                runMergeSort(writer, random, "random");
                runMergeSort(writer, sorted, "sorted");
                runMergeSort(writer, reverse, "reverse");
                runMergeSort(writer, duplicates, "duplicates");

                runQuickSort(writer, random, "random");
                runQuickSort(writer, sorted, "sorted");
                runQuickSort(writer, reverse, "reverse");
                runQuickSort(writer, duplicates, "duplicates");

                runSelect(writer, random, "random");
                runSelect(writer, sorted, "sorted");
                runSelect(writer, reverse, "reverse");
                runSelect(writer, duplicates, "duplicates");

                Point[] points = createPoints(size);

                runClosestPair(writer, points);
            }

            System.out.println(
                    "Experiments completed successfully!"
            );

            System.out.println(
                    "Results saved to results/results.csv"
            );

        } catch (IOException e) {
            System.out.println(
                    "Error writing CSV: " + e.getMessage()
            );
        }
    }

    private static void runMergeSort(
            FileWriter writer,
            int[] original,
            String inputType) throws IOException {

        int[] array = original.clone();

        long start = System.nanoTime();

        MergeSorter.sort(array);

        long time = System.nanoTime() - start;

        writer.write(
                "MergeSort," +
                        inputType + "," +
                        array.length + "," +
                        time + "," +
                        MergeSorter.getMaxRecursionDepth() + "," +
                        MergeSorter.getComparisons() +
                        "\n"
        );
    }

    private static void runQuickSort(
            FileWriter writer,
            int[] original,
            String inputType) throws IOException {

        int[] array = original.clone();

        long start = System.nanoTime();

        QuickSorter.sort(array);

        long time = System.nanoTime() - start;

        writer.write(
                "QuickSort," +
                        inputType + "," +
                        array.length + "," +
                        time + "," +
                        QuickSorter.getMaxRecursionDepth() + "," +
                        QuickSorter.getComparisons() +
                        "\n"
        );
    }

    private static void runSelect(
            FileWriter writer,
            int[] original,
            String inputType) throws IOException {

        int[] array = original.clone();

        int k = array.length / 2;

        long start = System.nanoTime();

        DeterministicSelector.select(array, k);

        long time = System.nanoTime() - start;

        writer.write(
                "DeterministicSelect," +
                        inputType + "," +
                        array.length + "," +
                        time + "," +
                        DeterministicSelector.getMaxRecursionDepth() + "," +
                        DeterministicSelector.getComparisons() +
                        "\n"
        );
    }

    private static void runClosestPair(
            FileWriter writer,
            Point[] points) throws IOException {

        long start = System.nanoTime();

        ClosestPairSolver.findClosestDistance(points);

        long time = System.nanoTime() - start;

        writer.write(
                "ClosestPair,random," +
                        points.length + "," +
                        time + "," +
                        ClosestPairSolver.getMaxRecursionDepth() + "," +
                        ClosestPairSolver.getComparisons() +
                        "\n"
        );
    }

    private static int[] createRandomArray(int size) {

        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = RANDOM.nextInt(100000);
        }

        return array;
    }

    private static int[] createSortedArray(int size) {

        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = i;
        }

        return array;
    }

    private static int[] createReverseArray(int size) {

        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = size - i;
        }

        return array;
    }

    private static int[] createDuplicateArray(int size) {

        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = RANDOM.nextInt(10);
        }

        return array;
    }

    private static Point[] createPoints(int size) {

        Point[] points = new Point[size];

        for (int i = 0; i < size; i++) {

            points[i] = new Point(
                    RANDOM.nextDouble() * 10000,
                    RANDOM.nextDouble() * 10000
            );
        }

        return points;
    }
}