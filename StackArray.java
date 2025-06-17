public class StackArray {
    int[] arr;
    int top;
    int capacity;
    StackArray(int size) {
        arr = new int[size];
        capacity = size;
        top = -1;
    }
    void push(int x) {
        if (top == capacity - 1) return;
        arr[++top] = x;
    }
    int pop() {
        if (top == -1) return -1;
        return arr[top--];
    }
    public static void main(String[] args) {
        StackArray stack = new StackArray(5);
        stack.push(10);
        stack.push(20);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }
}
