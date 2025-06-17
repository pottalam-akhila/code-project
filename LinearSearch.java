import java.util.Scanner;

public class LinearSearch {
    public static void main(String[] args) {
        int[] arr = {10, 25, 30, 45, 50};  // Sample array

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the element to search: ");
        int key = scanner.nextInt();

        int result = linearSearch(arr, key);

        if (result == -1) {
            System.out.println("Element not found in the array.");
        } else {
            System.out.println("Element found at index: " + result);
        }

        scanner.close();
    }

    // Linear Search Method
    public static int linearSearch(int[] arr, int key) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == key) {
                return i; // Return index if found
            }
        }
        return -1; // Return -1 if not found
    }
}
