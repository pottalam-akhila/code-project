import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
public class QueueRotationCounter{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        System.out.print("enter the size of the queue:");
        int queueSize=sc.nextInt();
        System.out.print("enter the total number of elements to add:");
        int totalElements=sc.nextInt();
        System.out.println("enter the elements:");
        Queue<Integer> queue=new LinkedList<>();
        int rotations=0;
        int inserted=0;
        int removedInCurrentRotation=0;
        while(inserted<queueSize && inserted<totalElements){
            int element=sc.nextInt();
            queue.offer(element);
            inserted++;
        }
        while(inserted<totalElements){
            queue.poll();
            removedInCurrentRotation++;
            int element=sc.nextInt();
            queue.offer(element);
            inserted++;
            if(removedInCurrentRotation==queueSize){
                rotations++;
                removedInCurrentRotation=0;
            }
        }
        System.out.print("queue elements:");
        for(int elem:queue){
        System.out.print(elem +"");
        }
        System.out.println();
        System.out.print("the queue was rotated" + rotations + "times.");
        sc.close();
    }
}