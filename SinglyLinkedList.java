class Node {
    int data;
    Node next;
    Node(int d) { data = d; next = null; }
}
public class SinglyLinkedList {
    Node head;
    public void printList() {
        Node curr = head;
        while (curr != null) {
            System.out.print(curr.data + " ");
            curr = curr.next;
        }
    }
    public static void main(String[] args) {
        SinglyLinkedList list = new SinglyLinkedList();
        list.head = new Node(1);
        list.head.next = new Node(2);
        list.head.next.next = new Node(3);
        list.printList();
    }
}
