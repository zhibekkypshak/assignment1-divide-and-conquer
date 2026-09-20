import java.util.Arrays;
import java.util.Comparator;

public class ClosestPairSolver {

    private static int maxRecursionDepth;
    private static long comparisons;

    public static double findClosestDistance(Point[] points) {

        maxRecursionDepth = 0;
        comparisons = 0;

        if (points == null || points.length < 2) {
            throw new IllegalArgumentException(
                    "At least two points are required"
            );
        }

        Point[] pointsByX = points.clone();

        Arrays.sort(
                pointsByX,
                Comparator.comparingDouble(Point::getX)
        );

        return closest(
                pointsByX,
                0,
                pointsByX.length - 1,
                1
        );
    }

    private static double closest(
            Point[] points,
            int left,
            int right,
            int depth) {

        maxRecursionDepth =
                Math.max(maxRecursionDepth, depth);

        int count = right - left + 1;

        if (count <= 3) {

            double minDistance =
                    bruteForce(points, left, right);

            Arrays.sort(
                    points,
                    left,
                    right + 1,
                    Comparator.comparingDouble(Point::getY)
            );

            return minDistance;
        }

        int mid = left + (right - left) / 2;

        double midX = points[mid].getX();

        double leftDistance =
                closest(points, left, mid, depth + 1);

        double rightDistance =
                closest(points, mid + 1, right, depth + 1);

        double minDistance =
                Math.min(leftDistance, rightDistance);

        mergeByY(points, left, mid, right);

        Point[] strip = new Point[count];
        int stripSize = 0;

        for (int i = left; i <= right; i++) {

            if (Math.abs(points[i].getX() - midX)
                    < minDistance) {

                strip[stripSize++] = points[i];
            }
        }

        for (int i = 0; i < stripSize; i++) {

            for (int j = i + 1;
                 j < stripSize &&
                         strip[j].getY() - strip[i].getY()
                                 < minDistance;
                 j++) {

                comparisons++;

                double distance =
                        distance(strip[i], strip[j]);

                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }

        return minDistance;
    }

    public static double bruteForceDistance(Point[] points) {

        if (points == null || points.length < 2) {
            throw new IllegalArgumentException(
                    "At least two points are required"
            );
        }

        return bruteForce(
                points,
                0,
                points.length - 1
        );
    }

    private static double bruteForce(
            Point[] points,
            int left,
            int right) {

        double minDistance =
                Double.POSITIVE_INFINITY;

        for (int i = left; i <= right; i++) {

            for (int j = i + 1; j <= right; j++) {

                comparisons++;

                double distance =
                        distance(points[i], points[j]);

                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }

        return minDistance;
    }

    private static double distance(Point a, Point b) {

        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();

        return Math.sqrt(dx * dx + dy * dy);
    }

    private static void mergeByY(
            Point[] points,
            int left,
            int mid,
            int right) {

        Point[] temp =
                new Point[right - left + 1];

        int i = left;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= right) {

            if (points[i].getY()
                    <= points[j].getY()) {

                temp[k++] = points[i++];

            } else {

                temp[k++] = points[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = points[i++];
        }

        while (j <= right) {
            temp[k++] = points[j++];
        }

        System.arraycopy(
                temp,
                0,
                points,
                left,
                temp.length
        );
    }

    public static int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }

    public static long getComparisons() {
        return comparisons;
    }
}
