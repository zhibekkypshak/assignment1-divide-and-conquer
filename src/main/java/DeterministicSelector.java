public class DeterministicSelector {

    private static int maxRecursionDepth;
    private static long comparisons;

    public static int select(int[] array, int k) {
        maxRecursionDepth = 0;
        comparisons = 0;

        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }

        if (k < 0 || k >= array.length) {
            throw new IllegalArgumentException("k is out of range");
        }

        return select(array, 0, array.length - 1, k, 1);
    }

    private static int select(
            int[] array,
            int left,
            int right,
            int k,
            int depth) {

        maxRecursionDepth = Math.max(maxRecursionDepth, depth);

        if (left == right) {
            return array[left];
        }

        int pivotValue = medianOfMedians(array, left, right, depth + 1);

        int[] equalRange =
                partition(array, left, right, pivotValue);

        if (k < equalRange[0]) {
            return select(
                    array,
                    left,
                    equalRange[0] - 1,
                    k,
                    depth + 1
            );
        }

        if (k > equalRange[1]) {
            return select(
                    array,
                    equalRange[1] + 1,
                    right,
                    k,
                    depth + 1
            );
        }

        return pivotValue;
    }

    private static int medianOfMedians(
            int[] array,
            int left,
            int right,
            int depth) {

        maxRecursionDepth = Math.max(maxRecursionDepth, depth);

        int n = right - left + 1;

        if (n <= 5) {
            insertionSort(array, left, right);
            return array[left + n / 2];
        }

        int medianCount = 0;

        for (int i = left; i <= right; i += 5) {

            int groupRight = Math.min(i + 4, right);

            insertionSort(array, i, groupRight);

            int medianIndex =
                    i + (groupRight - i) / 2;

            swap(
                    array,
                    left + medianCount,
                    medianIndex
            );

            medianCount++;
        }

        int medianIndex =
                left + (medianCount - 1) / 2;

        return select(
                array,
                left,
                left + medianCount - 1,
                medianIndex,
                depth + 1
        );
    }

    private static int[] partition(
            int[] array,
            int left,
            int right,
            int pivotValue) {

        int less = left;
        int current = left;
        int greater = right;

        while (current <= greater) {

            comparisons++;

            if (array[current] < pivotValue) {

                swap(array, less, current);
                less++;
                current++;

            } else {

                comparisons++;

                if (array[current] > pivotValue) {

                    swap(array, current, greater);
                    greater--;

                } else {
                    current++;
                }
            }
        }

        return new int[]{less, greater};
    }

    private static void insertionSort(
            int[] array,
            int left,
            int right) {

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