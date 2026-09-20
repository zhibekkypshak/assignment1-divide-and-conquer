import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        // 1. MergeSort
        int[] mergeArray = {8, 3, 5, 1, 9, 2, 7, 4};
        int[] mergeExpected = mergeArray.clone();
        Arrays.sort(mergeExpected);

        MergeSorter.sort(mergeArray);

        System.out.println(
                "MergeSort: " +
                        (Arrays.equals(mergeArray, mergeExpected)
                                ? "PASSED" : "FAILED")
        );


        // 2. QuickSort
        int[] quickArray = {9, 4, 7, 2, 8, 1, 5, 3};
        int[] quickExpected = quickArray.clone();
        Arrays.sort(quickExpected);

        QuickSorter.sort(quickArray);

        System.out.println(
                "QuickSort: " +
                        (Arrays.equals(quickArray, quickExpected)
                                ? "PASSED" : "FAILED")
        );


        // 3. Deterministic Select
        Random random = new Random();
        boolean selectPassed = true;

        for (int test = 0; test < 100; test++) {

            int size = random.nextInt(100) + 1;
            int[] array = new int[size];

            for (int i = 0; i < size; i++) {
                array[i] = random.nextInt(100);
            }

            int k = random.nextInt(size);

            int[] expected = array.clone();
            Arrays.sort(expected);

            int actual =
                    DeterministicSelector.select(
                            array.clone(),
                            k
                    );

            if (actual != expected[k]) {
                selectPassed = false;
                break;
            }
        }

        System.out.println(
                "Deterministic Select: " +
                        (selectPassed ? "PASSED" : "FAILED")
        );


        // 4. Closest Pair
        boolean closestPassed = true;

        for (int test = 0; test < 100; test++) {

            int size = random.nextInt(100) + 2;
            Point[] points = new Point[size];

            for (int i = 0; i < size; i++) {
                points[i] = new Point(
                        random.nextInt(1000),
                        random.nextInt(1000)
                );
            }

            double expected =
                    ClosestPairSolver.bruteForceDistance(points);

            double actual =
                    ClosestPairSolver.findClosestDistance(points);

            if (Math.abs(expected - actual) > 1e-9) {
                closestPassed = false;
                break;
            }
        }

        System.out.println(
                "Closest Pair: " +
                        (closestPassed ? "PASSED" : "FAILED")
        );


        System.out.println("\nMetrics example:");

        int[] metricsArray =
                {8, 3, 5, 1, 9, 2, 7, 4};

        MergeSorter.sort(metricsArray);

        System.out.println(
                "MergeSort depth: " +
                        MergeSorter.getMaxRecursionDepth()
        );

        System.out.println(
                "MergeSort comparisons: " +
                        MergeSorter.getComparisons()
        );
    }
}