import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;
public class JavaTest {
    public static boolean areBracketsBalanced(String expr) {
        Stack<Character> stack = new Stack<>();
        for (char ch : expr.toCharArray()) {
            if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                if (stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty();
    }
    public static void main(String[] args) {
        LinkedList<String> inputs = new LinkedList<>();  
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Enter an expression (with parentheses) to check if brackets are balanced:");
            String expr = sc.nextLine();

            if (!areBracketsBalanced(expr)) {
                throw new Exception("Error: Brackets are not balanced!");
            }
            System.out.println("Brackets are balanced.");
            System.out.print(" 1.add\n 2.sub\n 3.mul\n 4.div\n");
            System.out.print("Enter an operator choice: ");
            int operator = sc.nextInt();
            System.out.print("Enter number1: ");
            int n1 = sc.nextInt();
            System.out.print("Enter number2: ");
            int n2 = sc.nextInt();
            sc.nextLine(); 
            inputs.add(String.valueOf(operator));
            inputs.add(String.valueOf(n1));
            inputs.add(String.valueOf(n2));
            while (true) {
                System.out.print("Do you want to insert or delete an element? (insert/delete/none): ");
                String choice = sc.nextLine().trim().toLowerCase();

                if (choice.equals("none")) break;

                if (choice.equals("insert")) {
                    System.out.print("Enter element to insert (operator(1-4) or number): ");
                    String element = sc.nextLine();

                    System.out.print("Enter index position to insert at (0 to " + inputs.size() + "): ");
                    int index = sc.nextInt();
                    sc.nextLine();

                    if (index < 0 || index > inputs.size()) {
                        System.out.println("Invalid index! Must be between 0 and " + inputs.size());
                    } else {
                        inputs.add(index, element);
                        System.out.println("After insertion: " + inputs);
                    }
                } else if (choice.equals("delete")) {
                    System.out.print("Enter index position to delete from (0 to " + (inputs.size() - 1) + "): ");
                    int index = sc.nextInt();
                    sc.nextLine();

                    if (index < 0 || index >= inputs.size()) {
                        System.out.println("Invalid index! Must be between 0 and " + (inputs.size() - 1));
                    } else {
                        String removed = inputs.remove(index);
                        System.out.println("Removed element: " + removed);
                        System.out.println("After deletion: " + inputs);
                    }
                } else {
                    System.out.println("Invalid choice.");
                }
            }
            System.out.print("Do you want to add parentheses? (yes/no): ");
            String parenChoice = sc.nextLine().trim().toLowerCase();
            if (parenChoice.equals("yes")) {
                System.out.print("Enter index of element to add parentheses around (0 to " + (inputs.size() - 1) + "): ");
                int idx = sc.nextInt();
                sc.nextLine();

                if (idx < 0 || idx >= inputs.size()) {
                    System.out.println("Invalid index for parentheses.");
                } else {
                    System.out.print("Add parentheses 'before' or 'after' the element? (before/after): ");
                    String pos = sc.nextLine().trim().toLowerCase();

                    String val = inputs.get(idx);
                    if (pos.equals("before")) {
                        inputs.set(idx, "(" + val);
                    } else if (pos.equals("after")) {
                        inputs.set(idx, val + ")");
                    } else {
                        System.out.println("Invalid choice for parentheses position.");
                    }

                    System.out.println("After adding parentheses: " + inputs);
                    StringBuilder combined = new StringBuilder();
                    for (String s : inputs) {
                        combined.append(s);
                    }
                    if (!areBracketsBalanced(combined.toString())) {
                        throw new Exception("Error: Brackets became unbalanced after insertion!");
                    }
                }
            }
            if (inputs.size() < 3) {
                System.out.println("Not enough elements to perform calculation.");
                sc.close();
                return;
            }
            int calcOperator = Integer.parseInt(inputs.get(0).replaceAll("[()]", ""));
            int calcN1 = Integer.parseInt(inputs.get(1).replaceAll("[()]", ""));
            int calcN2 = Integer.parseInt(inputs.get(2).replaceAll("[()]", ""));

            int result = 0;

            switch (calcOperator) {
                case 1:
                    result = calcN1 + calcN2;
                    break;
                case 2:
                    result = calcN1 - calcN2;
                    break;
                case 3:
                    result = calcN1 * calcN2;
                    break;
                case 4:
                    if (calcN2 != 0) {
                        result = calcN1 / calcN2;
                    } else {
                        System.out.println("Error: Division by zero");
                        sc.close();
                        return;
                    }
                    break;
                default:
                    System.out.println("Invalid operator choice");
                    sc.close();
                    return;
            }

            System.out.println("The result is: " + result);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            sc.close();
        }
    }
}
