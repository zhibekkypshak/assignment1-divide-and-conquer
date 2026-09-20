public class Experiment {

    public static long measureMergeSortTime(int[] array) {

        int[] copy = array.clone();

        long startTime = System.nanoTime();

        MergeSorter.sort(copy);

        long endTime = System.nanoTime();

        return endTime - startTime;
    }
    public static long measureQuickSortTime(int[] array) {

        int[] copy = array.clone();

        long startTime = System.nanoTime();

        QuickSorter.sort(copy);

        long endTime = System.nanoTime();

        return endTime - startTime;
    }
    public static long measureDeterministicSelectTime(int[] array, int k) {

        int[] copy = array.clone();

        long startTime = System.nanoTime();

        DeterministicSelector.select(copy, k);

        long endTime = System.nanoTime();

        return endTime - startTime;
    }
    public static long measureClosestPairTime(Point[] points) {

        Point[] copy = points.clone();

        long startTime = System.nanoTime();

        ClosestPairSolver.findClosestDistance(copy);

        long endTime = System.nanoTime();

        return endTime - startTime;
    }
}