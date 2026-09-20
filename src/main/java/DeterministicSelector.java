public class DeterministicSelector {

    public static int select(int[] array, int k) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }

        if (k < 0 || k >= array.length) {
            throw new IllegalArgumentException("k is out of range");
        }

        return select(array, 0, array.length - 1, k);
    }
    private static int select(int[] array, int left, int right, int k) {

        if (left == right) {
            return array[left];
        }

        int pivotValue = medianOfMedians(array, left, right);

        int pivotIndex = partition(array, left, right, pivotValue);

        if (k == pivotIndex) {
            return array[pivotIndex];
        } else if (k < pivotIndex) {
            return select(array, left, pivotIndex - 1, k);
        } else {
            return select(array, pivotIndex + 1, right, k);
        }
    }
    private static int medianOfMedians(int[] array, int left, int right) {

        int n = right - left + 1;

        if (n <= 5) {
            insertionSort(array, left, right);
            return array[left + n / 2];
        }

        int medianCount = 0;

        for (int i = left; i <= right; i += 5) {

            int groupRight = Math.min(i + 4, right);

            insertionSort(array, i, groupRight);

            int medianIndex = i + (groupRight - i) / 2;

            swap(array, left + medianCount, medianIndex);
            medianCount++;
        }

        int medianIndex = left + (medianCount - 1) / 2;

        return select(
                array,
                left,
                left + medianCount - 1,
                medianIndex
        );
    }
    private static void insertionSort(int[] array, int left, int right) {

        for (int i = left + 1; i <= right; i++) {

            int key = array[i];
            int j = i - 1;

            while (j >= left && array[j] > key) {
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
    private static int partition(int[] array, int left, int right, int pivotValue) {

        int pivotIndex = left;

        for (int i = left; i <= right; i++) {
            if (array[i] == pivotValue) {
                pivotIndex = i;
                break;
            }
        }

        swap(array, pivotIndex, right);

        int storeIndex = left;

        for (int i = left; i < right; i++) {
            if (array[i] < pivotValue) {
                swap(array, i, storeIndex);
                storeIndex++;
            }
        }

        swap(array, storeIndex, right);

        return storeIndex;
    }
}
