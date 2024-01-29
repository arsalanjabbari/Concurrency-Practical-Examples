import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MaximumNumberProblem {

    public static void main(String[] args) {
        // Start with N = 100000 and increase as needed
        int[] array = generateRandomArray(100000);

        // Serial implementation
        long serialStartTime = System.nanoTime();
        int serialMax = findMaxSerial(array);
        long serialEndTime = System.nanoTime();
        long serialRuntime = serialEndTime - serialStartTime;

        // Parallel implementation
        long parallelStartTime = System.nanoTime();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int parallelMax = forkJoinPool.invoke(new MaximumTask(array, 0, array.length));
        long parallelEndTime = System.nanoTime();
        long parallelRuntime = parallelEndTime - parallelStartTime;

        // Calculate performance
        double performance = (double) serialRuntime / parallelRuntime;

        // Report run time in a table
        System.out.println("N\t\tSerial Runtime\t\tParallel Runtime\t\tPerformance");
        System.out.println(array.length + "\t\t" + serialRuntime + " ns\t\t" + parallelRuntime + " ns\t\t" + performance);
    }

    // Serial implementation
    private static int findMaxSerial(int[] array) {
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    // Parallel implementation using ForkJoinTask
    private static class MaximumTask extends RecursiveTask<Integer> {
        private final int[] array;
        private final int start;
        private final int end;

        public MaximumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= 1000) { // Adjust the threshold as needed
                return findMaxSerial(Arrays.copyOfRange(array, start, end));
            } else {
                int mid = (start + end) / 2;
                MaximumTask leftTask = new MaximumTask(array, start, mid);
                MaximumTask rightTask = new MaximumTask(array, mid, end);

                invokeAll(leftTask, rightTask);

                return Math.max(leftTask.join(), rightTask.join());
            }
        }
    }

    // Utility method to generate a random array of integers
    private static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }
}
