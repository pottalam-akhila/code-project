class DNode {
    int data;
    DNode next, prev;
    DNode(int d) { data = d; next = prev = null; }
}
public class DoublyLinkedList {
    DNode head;
    void insert(int data) {
        DNode newNode = new DNode(data);
        if (head == null) {
            head = newNode;
            return;
        }
        DNode temp = head;
        while (temp.next != null) temp = temp.next;
        temp.next = newNode;
        newNode.prev = temp;
    }
    void printList() {
        DNode temp = head;
        while (temp != null) {
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
    }
    public static void main(String[] args) {
        DoublyLinkedList dl = new DoublyLinkedList();
        dl.insert(1);
        dl.insert(2);
        dl.insert(3);
        dl.printList();
    }
}
