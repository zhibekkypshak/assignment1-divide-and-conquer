import java.util.Random;

public class QuickSorter {

    private static final Random RANDOM = new Random();

    public static void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        quickSort(array, 0, array.length - 1);
    }
    private static int partition(int[] array, int left, int right) {

        int pivotIndex = left + RANDOM.nextInt(right - left + 1);
        int pivotValue = array[pivotIndex];

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
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    private static void quickSort(int[] array, int left, int right) {

        while (left < right) {

            int pivotIndex = partition(array, left, right);

            int leftSize = pivotIndex - left;
            int rightSize = right - pivotIndex;

            if (leftSize < rightSize) {

                quickSort(array, left, pivotIndex - 1);
                left = pivotIndex + 1;

            } else {

                quickSort(array, pivotIndex + 1, right);
                right = pivotIndex - 1;
            }
        }
    }
}
