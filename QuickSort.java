public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {64, 25, 12, 22, 11};  // Sample array

        System.out.println("Original Array:");
        printArray(arr);

        quickSort(arr, 0, arr.length - 1);

        System.out.println("\nSorted Array in Ascending Order:");
        printArray(arr);
    }

    // Quick Sort Method
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // Partition the array
            int pivotIndex = partition(arr, low, high);

            // Recursively sort elements before and after partition
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
