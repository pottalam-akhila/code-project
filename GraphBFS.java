import java.util.*;
public class GraphBFS {
    private LinkedList<Integer>[] adj;
    private boolean[] visited;
    GraphBFS(int v) {
        adj = new LinkedList[v];
        visited = new boolean[v];
        for (int i = 0; i < v; i++) adj[i] = new LinkedList<>();
    }
    void addEdge(int u, int v) {
        adj[u].add(v);
    }
    void bfs(int s) {
        Queue<Integer> queue = new LinkedList<>();
        visited[s] = true;
        queue.add(s);
        while (!queue.isEmpty()) {
            int u = queue.poll();
            System.out.print(u + " ");
            for (int n : adj[u]) {
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
    }
    public static void main(String[] args) {
        GraphBFS g = new GraphBFS(4);
        g.addEdge(0,1);
        g.addEdge(0,2);
        g.addEdge(1,2);
        g.addEdge(2,0);
        g.addEdge(2,3);
        g.addEdge(3,3);
        g.bfs(2);
    }
}
