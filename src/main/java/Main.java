import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        testMergeSort(new int[]{8, 3, 5, 1, 9, 2, 7, 4}, "Random");
        testMergeSort(new int[]{1, 2, 3, 4, 5, 6}, "Sorted");
        testMergeSort(new int[]{6, 5, 4, 3, 2, 1}, "Reverse-sorted");
        testMergeSort(new int[]{4, 2, 4, 1, 2, 4, 1}, "Duplicates");
        testMergeSort(new int[]{}, "Empty");
        testMergeSort(new int[]{5}, "Single element");
    }

    private static void testMergeSort(int[] array, String testName) {

        int[] expected = array.clone();
        Arrays.sort(expected);

        int[] actual = array.clone();
        MergeSorter.sort(actual);

        boolean passed = Arrays.equals(expected, actual);

        System.out.println(testName + ": " + (passed ? "PASSED" : "FAILED"));
        System.out.println("Expected: " + Arrays.toString(expected));
        System.out.println("Actual:   " + Arrays.toString(actual));
        System.out.println();
    }
}