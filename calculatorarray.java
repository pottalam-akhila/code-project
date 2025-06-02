import java.util.Scanner;
import java.util.Arrays; 
public class JavaTest {
    public static void main(String[] args) {
        int operator, count;
        double result = 0.0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose operation:");
        System.out.println("1. Add\n2.sub\n3.mul\n4.div");
        System.out.print("Enter your choice (1-4): ");
        operator = sc.nextInt();
        System.out.print("How many numbers do you want to " + getOperationName(operator) + "? ");
        count = sc.nextInt();
        if (count < 2) {
            System.out.println("Please enter at least two numbers.");
            return;
        }
        double[] numbers = new double[count];
        for (int i = 0; i < count; i++) {
            System.out.print("Enter number " + (i + 1) + ": ");
            numbers[i] = sc.nextDouble();
        }
        System.out.println("Numbers entered: " + Arrays.toString(numbers));
        switch (operator) {
            case 1:
                result = 0.0;
                for (double num : numbers) {
                    result += num;
                }
                break;
            case 2:
                result = numbers[0];
                for (int i = 1; i < count; i++) {
                    result -= numbers[i];
                }
                break;
            case 3:
                result = 1.0;
                for (double num : numbers) {
                    result *= num;
                }
                break;
            case 4:
                result = numbers[0];
                for (int i = 1; i < count; i++) {
                    if (numbers[i] == 0) {
                        System.out.println("Error: Division by zero at number " + (i + 1));
                        return;
                    }
                    result /= numbers[i];
                }
                break;
            default:
                System.out.println("Invalid choice");
                return;
        }
        System.out.println("The result is: " + result);
    }
    private static String getOperationName(int operator) {
        switch (operator) {
            case 1:
                return "add";
            case 2:
                return "subtract";
            case 3:
                return "multiply";
            case 4:
                return "divide";
            default:
                return "operate on";
        }
    }
}