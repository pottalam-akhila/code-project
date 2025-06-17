import java.util.Stack;
public class StackExample {
    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        stack.push("A");
        stack.push("B");
        stack.push("C");
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }
}
