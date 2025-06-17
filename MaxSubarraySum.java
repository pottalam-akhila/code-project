public class MaxSubarraySum {
    public static void main(String[] args) {
        int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};  // Sample input array

        int maxSum = findMaxSubarraySum(arr);

        System.out.println("Maximum Subarray Sum: " + maxSum);
    }

    // Method to find maximum subarray sum using Kadane's Algorithm
    public static int findMaxSubarraySum(int[] arr) {
        int maxCurrent = arr[0];
        int maxGlobal = arr[0];

        for (int i = 1; i < arr.length; i++) {
            maxCurrent = Math.max(arr[i], maxCurrent + arr[i]);
            maxGlobal = Math.max(maxGlobal, maxCurrent);
        }

        return maxGlobal;
    }
}
