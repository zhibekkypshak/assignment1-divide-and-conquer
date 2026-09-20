import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Random random = new Random();
        boolean allPassed = true;

        for (int test = 1; test <= 100; test++) {

            int size = random.nextInt(100) + 1;
            int[] array = new int[size];

            for (int i = 0; i < size; i++) {
                array[i] = random.nextInt(1000);
            }

            int k = random.nextInt(size);

            int[] expectedArray = array.clone();
            Arrays.sort(expectedArray);
            int expected = expectedArray[k];

            int[] actualArray = array.clone();
            int actual = DeterministicSelector.select(actualArray, k);

            if (expected != actual) {
                allPassed = false;

                System.out.println("Test " + test + ": FAILED");
                System.out.println("k = " + k);
                System.out.println("Expected: " + expected);
                System.out.println("Actual:   " + actual);

                break;
            }
        }

        if (allPassed) {
            System.out.println("All 100 Deterministic Select tests PASSED!");
        }
    }
}