public class MergeSorter {

    private static final int INSERTION_SORT_THRESHOLD = 16;

    private static int maxRecursionDepth;
    private static long comparisons;

    public static void sort(int[] array) {
        maxRecursionDepth = 0;
        comparisons = 0;

        if (array == null || array.length < 2) {
            return;
        }

        int[] buffer = new int[array.length];
        mergeSort(array, buffer, 0, array.length - 1, 1);
    }

    private static void mergeSort(
            int[] array,
            int[] buffer,
            int left,
            int right,
            int depth) {

        maxRecursionDepth = Math.max(maxRecursionDepth, depth);

        if (left >= right) {
            return;
        }

        if (right - left + 1 <= INSERTION_SORT_THRESHOLD) {
            insertionSort(array, left, right);
            return;
        }

        int mid = left + (right - left) / 2;

        mergeSort(array, buffer, left, mid, depth + 1);
        mergeSort(array, buffer, mid + 1, right, depth + 1);

        merge(array, buffer, left, mid, right);
    }

    private static void insertionSort(int[] array, int left, int right) {

        for (int i = left + 1; i <= right; i++) {

            int key = array[i];
            int j = i - 1;

            while (j >= left) {

                comparisons++;

                if (array[j] <= key) {
                    break;
                }

                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = key;
        }
    }

    private static void merge(
            int[] array,
            int[] buffer,
            int left,
            int mid,
            int right) {

        for (int i = left; i <= right; i++) {
            buffer[i] = array[i];
        }

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {

            comparisons++;

            if (buffer[i] <= buffer[j]) {
                array[k++] = buffer[i++];
            } else {
                array[k++] = buffer[j++];
            }
        }

        while (i <= mid) {
            array[k++] = buffer[i++];
        }

        while (j <= right) {
            array[k++] = buffer[j++];
        }
    }

    public static int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }

    public static long getComparisons() {
        return comparisons;
    }
}
