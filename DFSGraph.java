import java.util.*;

public class DFSGraph {
    private int vertices; // Number of vertices
    private LinkedList<Integer>[] adjList; // Adjacency list

    // Constructor
    public DFSGraph(int v) {
        vertices = v;
        adjList = new LinkedList[v];

        for (int i = 0; i < v; ++i) {
            adjList[i] = new LinkedList<>();
        }
    }

    // Method to add an edge to the graph
    public void addEdge(int v, int w
