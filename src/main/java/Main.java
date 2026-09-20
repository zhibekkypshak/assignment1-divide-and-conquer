import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Random random = new Random();
        boolean allPassed = true;

        for (int test = 1; test <= 100; test++) {

            int size = random.nextInt(100) + 2;
            Point[] points = new Point[size];

            for (int i = 0; i < size; i++) {
                double x = random.nextInt(1000);
                double y = random.nextInt(1000);

                points[i] = new Point(x, y);
            }

            double expected =
                    ClosestPairSolver.bruteForceDistance(points);

            double actual =
                    ClosestPairSolver.findClosestDistance(points);

            if (Math.abs(expected - actual) > 1e-9) {
                allPassed = false;

                System.out.println("Test " + test + ": FAILED");
                System.out.println("Expected: " + expected);
                System.out.println("Actual:   " + actual);

                break;
            }
        }

        if (allPassed) {
            System.out.println("All 100 Closest Pair tests PASSED!");
        }
    }
}