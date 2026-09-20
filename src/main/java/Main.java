import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        testQuickSort(new int[]{8, 3, 5, 1, 9, 2, 7, 4}, "Random");
        testQuickSort(new int[]{1, 2, 3, 4, 5, 6}, "Sorted");
        testQuickSort(new int[]{6, 5, 4, 3, 2, 1}, "Reverse-sorted");
        testQuickSort(new int[]{4, 2, 4, 1, 2, 4, 1}, "Duplicates");
        testQuickSort(new int[]{}, "Empty");
        testQuickSort(new int[]{5}, "Single element");
    }

    private static void testQuickSort(int[] array, String testName) {

        int[] expected = array.clone();
        Arrays.sort(expected);

        int[] actual = array.clone();
        QuickSorter.sort(actual);

        boolean passed = Arrays.equals(expected, actual);

        System.out.println(testName + ": " + (passed ? "PASSED" : "FAILED"));
        System.out.println("Expected: " + Arrays.toString(expected));
        System.out.println("Actual:   " + Arrays.toString(actual));
        System.out.println();
    }
}