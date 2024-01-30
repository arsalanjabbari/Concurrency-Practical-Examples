
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class MaxApp {

    private static final Random random = new Random();

    public static void main(String[] args) {
        doIt();
    }

    private static void doIt() {
        double performance = 0;
        double previous = 0;
        int arraySize = 100000;
        while (performance >= previous) {
            previous = performance;

            System.out.println("Using --> " + arraySize);

            int[] nums = random.ints(arraySize, (-1 * arraySize), arraySize).toArray();

            long start = Instant.now().toEpochMilli();
            System.out.println("Max (sequential) : " + SequentialMaxFinder.findMax(nums, 1, nums.length));
            double a1 = (Instant.now().toEpochMilli() - start);
            System.out.println("Time taken (ms) : " + a1);

            ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

            int threshold = nums.length / Runtime.getRuntime().availableProcessors();
            System.out.println("\nSplit threshold : " + threshold);

            ParallelMaxFinder para = new ParallelMaxFinder(nums, 0, nums.length, threshold);
            start = Instant.now().toEpochMilli();
            System.out.println("Max (parallel) : " + pool.invoke(para));
            double a2 = (Instant.now().toEpochMilli() - start);
            System.out.println("Time taken (ms) : " + a2);
            performance = a1 / a2;
            System.out.println("Performance = " + performance);
            arraySize += 1;
        }
    }
}