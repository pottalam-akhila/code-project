import java.util.Scanner;
import java.util.Stack;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays; 
public class OperatorPrecedenceCalculator {
    private static final Map<Character, Integer> PRECEDENCE = new HashMap<>();
    static {
        PRECEDENCE.put('+', 1);
        PRECEDENCE.put('-', 1);
        PRECEDENCE.put('*', 2);
        PRECEDENCE.put('/', 2);
    }
    private static boolean isOperator(char c) {
        return PRECEDENCE.containsKey(c);
    }
    private static int comparePrecedence(char op1, char op2) {
        return PRECEDENCE.get(op1) - PRECEDENCE.get(op2);
    }
    public static String[] infixToRpn(String infix) {
        StringBuilder output = new StringBuilder(); 
        Stack<Character> operatorStack = new Stack<>(); 
        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                while (i < infix.length() && (Character.isDigit(infix.charAt(i)) || infix.charAt(i) == '.')) {
                    output.append(infix.charAt(i));
                    i++;
                }
                output.append(' '); 
                i--; 
            } else if (isOperator(c)) {
                while (!operatorStack.isEmpty() && isOperator(operatorStack.peek()) &&
                       comparePrecedence(operatorStack.peek(), c) >= 0) {
                    output.append(operatorStack.pop()).append(' ');
                }
                operatorStack.push(c);
            } else if (c == '(') {
                operatorStack.push(c);
            } else if (c == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    output.append(operatorStack.pop()).append(' ');
                }
                if (operatorStack.isEmpty() || operatorStack.peek() != '(') {
                    throw new IllegalArgumentException("Mismatched or empty parentheses in expression.");
                }
                operatorStack.pop();
            } else if (Character.isWhitespace(c)) {
                continue;
            } else {
                throw new IllegalArgumentException("Invalid character in expression: '" + c + "'");
            }
        }
        while (!operatorStack.isEmpty()) {
            if (operatorStack.peek() == '(') {
                throw new IllegalArgumentException("Mismatched parentheses in expression.");
            }
            output.append(operatorStack.pop()).append(' ');
        }
        return output.toString().trim().split(" ");
    }
    public static double evaluateRpn(String[] rpnTokens) {
        Stack<Double> operandStack = new Stack<>(); 
        for (String token : rpnTokens) {
            if (token.isEmpty()) {
                continue; 
            }
            if (token.length() == 1 && isOperator(token.charAt(0))) {
                if (operandStack.size() < 2) {
                    throw new IllegalArgumentException("Invalid RPN expression: insufficient operands for operator '" + token + "'");
                }
                double operand2 = operandStack.pop(); // Second operand is on top
                double operand1 = operandStack.pop(); // First operand is below it
                double result;
                switch (token.charAt(0)) {
                    case '+':
                        result = operand1 + operand2;
                        break;
                    case '-':
                        result = operand1 - operand2;
                        break;
                    case '*':
                        result = operand1 * operand2;
                        break;
                    case '/':
                        if (operand2 == 0) {
                            throw new ArithmeticException("Division by zero occurred.");
                        }
                        result = operand1 / operand2;
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown operator encountered: '" + token + "'");
                }
                operandStack.push(result); // Push the result back onto the stack
            } else {
                try {
                    operandStack.push(Double.parseDouble(token));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid number in RPN expression: '" + token + "'");
                }
            }
        }
        if (operandStack.size() != 1) {
            throw new IllegalArgumentException("Invalid RPN expression: too many operands or operators remaining.");
        }
        return operandStack.pop();
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Advanced Calculator!");
        System.out.println("Enter a mathematical expression (e.g., 2 + 3 * (4 - 1) / 2):");
        System.out.print("Expression: ");
        String expression = scanner.nextLine();
        try {
            String[] rpnTokens = infixToRpn(expression);
            System.out.println("Converted to RPN: " + Arrays.toString(rpnTokens));
            double finalResult = evaluateRpn(rpnTokens);
            System.out.println("Result: " + finalResult);
        } catch (IllegalArgumentException | ArithmeticException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}