import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiFunction;

public class JavaTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Map of operations using lambdas
        Map<Integer, BiFunction<Integer, Integer, Integer>> operations = new HashMap<>();
        operations.put(1, (a, b) -> a + b);
        operations.put(2, (a, b) -> a - b);
        operations.put(3, (a, b) -> a * b);
        operations.put(4, (a, b) -> {
            if (b == 0) {
                throw new ArithmeticException("Division by zero is not allowed.");
            }
            return a / b;
        });

        System.out.println(" 1. add\n 2. sub\n 3. mul\n 4. div");
        System.out.print("Enter an operator choice: ");
        int operator = sc.nextInt();

        System.out.print("Enter number1: ");
        int n1 = sc.nextInt();

        System.out.print("Enter number2: ");
        int n2 = sc.nextInt();

        if (operations.containsKey(operator)) {
            try {
                int result = operations.get(operator).apply(n1, n2);
                System.out.println("The result is: " + result);
            } catch (ArithmeticException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid choice");
        }

        sc.close();
    }
}
