
class SequentialMaxFinder {

    static int findMax(int[] nums, int low, int high) {
        int max = nums[low];

        for (int i = low + 1; i < high; i++) {
            if (nums[i] > max) {
                max = nums[i];
            }
        }

        return max;
    }
}