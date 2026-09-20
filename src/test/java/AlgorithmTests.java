import java.util.Arrays;
import java.util.Random;

public class AlgorithmTests {

    private static final Random RANDOM = new Random(42);

    public static void main(String[] args) {
        testMergeSort();
        testQuickSort();
        testDeterministicSelect();
        testClosestPair();

        System.out.println();
        System.out.println("ALL TESTS PASSED!");
    }

    private static void testMergeSort() {
        int[][] tests = {
                {},
                {5},
                {1, 2, 3, 4, 5},
                {5, 4, 3, 2, 1},
                {4, 2, 4, 1, 2, 4}
        };

        for (int[] test : tests) {
            int[] expected = test.clone();
            int[] actual = test.clone();

            Arrays.sort(expected);
            MergeSorter.sort(actual);

            if (!Arrays.equals(expected, actual)) {
                throw new AssertionError("MergeSort failed");
            }
        }

        System.out.println("MergeSort tests: PASSED");
    }

    private static void testQuickSort() {
        int[][] tests = {
                {},
                {5},
                {1, 2, 3, 4, 5},
                {5, 4, 3, 2, 1},
                {4, 2, 4, 1, 2, 4}
        };

        for (int[] test : tests) {
            int[] expected = test.clone();
            int[] actual = test.clone();

            Arrays.sort(expected);
            QuickSorter.sort(actual);

            if (!Arrays.equals(expected, actual)) {
                throw new AssertionError("QuickSort failed");
            }
        }

        System.out.println("QuickSort tests: PASSED");
    }

    private static void testDeterministicSelect() {
        for (int test = 0; test < 100; test++) {
            int size = RANDOM.nextInt(100) + 1;
            int[] array = new int[size];

            for (int i = 0; i < size; i++) {
                array[i] = RANDOM.nextInt(1000);
            }

            int k = RANDOM.nextInt(size);

            int[] sorted = array.clone();
            Arrays.sort(sorted);

            int actual = DeterministicSelector.select(array.clone(), k);

            if (actual != sorted[k]) {
                throw new AssertionError(
                        "Deterministic Select failed at test " + test
                );
            }
        }

        System.out.println("Deterministic Select 100 random tests: PASSED");
    }

    private static void testClosestPair() {
        for (int test = 0; test < 100; test++) {
            int size = RANDOM.nextInt(100) + 2;
            Point[] points = new Point[size];

            for (int i = 0; i < size; i++) {
                points[i] = new Point(
                        RANDOM.nextDouble() * 1000,
                        RANDOM.nextDouble() * 1000
                );
            }

            double expected =
                    ClosestPairSolver.bruteForceDistance(points.clone());

            double actual =
                    ClosestPairSolver.findClosestDistance(points.clone());

            if (Math.abs(expected - actual) > 1e-9) {
                throw new AssertionError(
                        "Closest Pair failed at test " + test
                );
            }
        }

        System.out.println("Closest Pair 100 random tests: PASSED");
    }
}
