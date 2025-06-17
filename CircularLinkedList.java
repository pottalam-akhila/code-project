class CNode {
    int data;
    CNode next;
    CNode(int d) { data = d; next = null; }
}
public class CircularLinkedList {
    CNode head;
    void insert(int data) {
        CNode newNode = new CNode(data);
        if (head == null) {
            head = newNode;
            newNode.next = head;
        } else {
            CNode temp = head;
            while (temp.next != head) temp = temp.next;
            temp.next = newNode;
            newNode.next = head;
        }
    }
    void printList() {
        if (head == null) return;
        CNode temp = head;
        do {
            System.out.print(temp.data + " ");
            temp = temp.next;
        } while (temp != head);
    }
    public static void main(String[] args) {
        CircularLinkedList cl = new CircularLinkedList();
        cl.insert(1);
        cl.insert(2);
        cl.insert(3);
        cl.printList();
    }
}
