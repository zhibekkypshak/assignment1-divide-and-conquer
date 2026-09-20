import java.util.Random;

public class QuickSorter {

    private static final Random RANDOM = new Random();

    private static int maxRecursionDepth;
    private static long comparisons;

    public static void sort(int[] array) {
        maxRecursionDepth = 0;
        comparisons = 0;

        if (array == null || array.length < 2) {
            return;
        }

        quickSort(array, 0, array.length - 1, 1);
    }

    private static void quickSort(
            int[] array,
            int left,
            int right,
            int depth) {

        maxRecursionDepth = Math.max(maxRecursionDepth, depth);

        while (left < right) {

            int pivotIndex = partition(array, left, right);

            int leftSize = pivotIndex - left;
            int rightSize = right - pivotIndex;

            if (leftSize < rightSize) {

                if (left < pivotIndex - 1) {
                    quickSort(
                            array,
                            left,
                            pivotIndex - 1,
                            depth + 1
                    );
                }

                left = pivotIndex + 1;

            } else {

                if (pivotIndex + 1 < right) {
                    quickSort(
                            array,
                            pivotIndex + 1,
                            right,
                            depth + 1
                    );
                }

                right = pivotIndex - 1;
            }
        }
    }

    private static int partition(
            int[] array,
            int left,
            int right) {

        int pivotIndex =
                left + RANDOM.nextInt(right - left + 1);

        int pivotValue = array[pivotIndex];

        swap(array, pivotIndex, right);

        int storeIndex = left;

        for (int i = left; i < right; i++) {

            comparisons++;

            if (array[i] < pivotValue) {
                swap(array, i, storeIndex);
                storeIndex++;
            }
        }

        swap(array, storeIndex, right);

        return storeIndex;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }

    public static long getComparisons() {
        return comparisons;
    }
}
