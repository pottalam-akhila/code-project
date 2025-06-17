import java.util.PriorityQueue;
public class PriorityQueueExample {
    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(20);
        pq.add(10);
        pq.add(30);
        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
    }
}
