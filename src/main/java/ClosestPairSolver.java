import java.util.Arrays;
import java.util.Comparator;

public class ClosestPairSolver {

    public static double findClosestDistance(Point[] points) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("At least two points are required");
        }

        Point[] pointsByX = points.clone();

        Arrays.sort(pointsByX, Comparator.comparingDouble(Point::getX));

        return closest(pointsByX, 0, pointsByX.length - 1);
    }
    private static double closest(Point[] points, int left, int right) {

        int count = right - left + 1;

        if (count <= 3) {
            return bruteForce(points, left, right);
        }

        int mid = left + (right - left) / 2;
        double midX = points[mid].getX();

        double leftDistance = closest(points, left, mid);
        double rightDistance = closest(points, mid + 1, right);

        double minDistance = Math.min(leftDistance, rightDistance);

        return stripClosest(points, left, right, midX, minDistance);
    }
    private static double bruteForce(Point[] points, int left, int right) {

        double minDistance = Double.POSITIVE_INFINITY;

        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {

                double distance = distance(points[i], points[j]);

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
    private static double stripClosest(
            Point[] points,
            int left,
            int right,
            double midX,
            double minDistance) {

        Point[] strip = new Point[right - left + 1];
        int stripSize = 0;

        for (int i = left; i <= right; i++) {
            if (Math.abs(points[i].getX() - midX) < minDistance) {
                strip[stripSize] = points[i];
                stripSize++;
            }
        }

        Arrays.sort(
                strip,
                0,
                stripSize,
                Comparator.comparingDouble(Point::getY)
        );

        for (int i = 0; i < stripSize; i++) {
            for (int j = i + 1;
                 j < stripSize &&
                         strip[j].getY() - strip[i].getY() < minDistance;
                 j++) {

                double distance = distance(strip[i], strip[j]);

                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }

        return minDistance;
    }
    public static double bruteForceDistance(Point[] points) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("At least two points are required");
        }

        return bruteForce(points, 0, points.length - 1);
    }
}
