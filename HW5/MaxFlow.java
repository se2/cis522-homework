
package maxflow;

// Implementation of Ford Fulkerson algorithm
import java.util.Arrays;
import java.util.LinkedList;

class MaxFlow {
    
    // Number of vertices in graph
    static int V = 0;
    
    // Constructor
    public MaxFlow(int V) {
        MaxFlow.V = V;
    }
    
    /* 
     * Returns true if there is a path from source 's' to sink
     * 't' in residual graph. Also fills parent[] to store the
     * path 
     */
    boolean bfs(int rGraph[][], int s, int t, int parent[]) {
        
        // Create a visited array and mark all vertices as not
        // visited
        boolean visited[] = new boolean[V];
        
        for (int i = 0; i < V; ++i) {
            visited[i] = false;
        }

        // Create a queue, enqueue source vertex and mark
        // source vertex as visited
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;

        // Standard BFS Loop
        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < V; v++) {
                if (visited[v] == false && rGraph[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        // If we reached sink in BFS starting from source, then
        // return true, else false
        return (visited[t] == true);
    }

    // Returns the maximum flow from s to t in the given graph
    int fordFulkerson(int graph[][], int s, int t) {
        int u, v;

        // Create a residual graph and fill the residual graph
        // with given capacities in the original graph as
        // residual capacities in residual graph
        // Residual graph where rGraph[i][j] indicates
        // residual capacity of edge from i to j (if there
        // is an edge. If rGraph[i][j] is 0, then there is
        // not)
        int rGraph[][] = new int[V][V];
        int flowGraph[][] = new int[V][V];
        
        for (u = 0; u < V; u++) {
            for (v = 0; v < V; v++) {
                rGraph[u][v] = graph[u][v];
                flowGraph[u][v] = 0;
            }
        }

        // This array is filled by BFS and to store path
        int parent[] = new int[V];
        
        // There is no flow initially
        int maxFlow = 0;

        // Augment the flow while there is path from source
        // to sink
        while (bfs(rGraph, s, t, parent)) {
            // Find minimum residual capacity of the edges
            // along the path filled by BFS. 
            // Or find the maximum flow through the path found.
            int pathFlow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                pathFlow = Math.min(pathFlow, rGraph[u][v]);
            }

            // Update residual capacities of the edges and
            // reverse edges along the path
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                rGraph[u][v] -= pathFlow;
                rGraph[v][u] += pathFlow;
                flowGraph[u][v] += pathFlow;
            }            

            // Add path flow to overall flow
            maxFlow += pathFlow;
        }
        
        // Print out flow diagram
        System.out.println("Flow network:");
        for (u = 0; u < V; u++) {
            for (v = 0; v < V; v++) {
                if (flowGraph[u][v]>0) {
                    String source = (u == 0) ? "s" : (u + "");
                    String sink = (v == graph.length - 1) ? "t" : (v + "");
                    System.out.println(source + " - " + sink + ": " + flowGraph[u][v]);    
                }                
            }
        }
        
        // Return the maximum flow
        return maxFlow;
    }

    // Test unit
    public static void main(String[] args) throws java.lang.Exception {
        // Graph in the question 1 represented by a matrix

        // Data matrix for question 1
//                                         /* s  2   3   4   5   6   7   t */
//        int graph[][] = new int[][]{/* s */{0, 16, 11, 0,  0,  15, 0,  0},
//                                    /* 2 */{0, 0,  6,  7,  8,  0,  0,  0},
//                                    /* 3 */{0, 0,  0,  0,  12, 0,  0,  0},
//                                    /* 4 */{0, 0,  0,  0,  0,  0,  0,  18},
//                                    /* 5 */{0, 0,  0,  6,  0,  0,  8,  10},
//                                    /* 6 */{0, 0,  4,  0,  5,  0,  6,  0},
//                                    /* 7 */{0, 0,  0,  0,  0,  0,  0,  13},
//                                    /* t */{0, 0,  0,  0,  0,  0,  0,  0}
//                                    };

          // Data matrix for question 2
//                                          /* s  p1   p2   p3   p4   p5   t */
//        int graph[][] = new int[][]{/* s */ {0, 0,   15,  0,   10,  20,  0},
//                                    /* p1 */{0, 0,   0,   0,   0,   0,   10},
//                                    /* p2 */{0, 100, 0,   0,   0,   0,   0},
//                                    /* p3 */{0, 100, 100, 0,   0,   0,   5},
//                                    /* p4 */{0, 0,   100, 0,   0,   0,   0},
//                                    /* p5 */{0, 0,   0,   100, 0,   0,   0},
//                                    /* t */ {0, 0,   0,   0,   0,   0,   0}
//                                    };

          // Data matrix for question 3       
//        int graph[][] = 
//                            /* s  1   2   3   4   5  6   7   8   9   10  11  12  13  14  15  16  17  18  19  20  21  22  t */
//        new int[][]{/* s */   {0, 3,  2,  2,  2,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
//                    /* 1 */   {0, 0,  0,  0,  0,  1, 1,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
//                    /* 2 */   {0, 0,  0,  0,  0,  0, 0,  0,  1,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
//                    /* 3 */   {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  1,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
//                    /* 4 */   {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  1,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
//                    /* 5 */   {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  1,  1,  0,  0,  0,  0,  0,  0,  0,  0},
//                    /* 6 */   {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  1,  0,  0,  0,  0,  0},
//                    /* 7 */   {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  1,  0,  0},
//                    /* 8 */   {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  1,  1,  0,  0,  0,  0,  0,  0,  0,  0},
//                    /* 9 */   {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  1,  0,  0,  0,  0,  0},
//                    /* 10 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  0},
//                    /* 11 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  0,  1,  0},
//                    /* 12 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  1,  0,  0,  0},
//                    /* 13 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  0,  1,  0},
//                    /* 14 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1},
//                    /* 15 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1},
//                    /* 16 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1},
//                    /* 17 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1},
//                    /* 18 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1},
//                    /* 19 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1}, 
//                    /* 20 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1},
//                    /* 21 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1},
//                    /* 22 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1},
//                    /* t */   {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1},
//                    };

        // Data matrix for question 4
        int graph[][] = 
                            /* s  1   2   3   4   5  6   7   8   9   10  11  12  13  14  15  16  17  18  19  20  21  22   23  24  t */
        new int[][]{/* s */   {0, 1,  1,  1,  1,  1, 1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  0,  0,  0,  0,  0,  0,  0,   0,  0,  0},
                    /* 1 */   {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  1,  1,  0,  0,  0,  0,   0,  0,  0},
                    /* 2 */   {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  1,  0,  1,  0,  0,  0,   0,  0,  0},
                    /* 3 */   {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  1,  0,  0,  0,   0,  0,  0},
                    /* 4 */   {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  1,  0,  0,  0,  0,   0,  0,  0},
                    /* 5 */   {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,   0,  0,  0},
                    /* 6 */   {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,   0,  0,  0},
                    /* 7 */   {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  0,  0,   0,  0,  0},
                    /* 8 */   {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,  0,   0,  0,  0},
                    /* 9 */   {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,   0,  0,  0},
                    /* 10 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  1,  0,  0,  0,  0,   0,  0,  0},
                    /* 11 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  0,  1,  0,  0,  0,   0,  0,  0},
                    /* 12 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  1,  0,  0,  0,   0,  0,  0},
                    /* 13 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,   0,  0,  0},
                    /* 14 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,   0,  0,  0},
                    /* 15 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  0,  1,  0,  0,  0,   0,  0,  0},
                    /* 16 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  100,0,  100, 0,  0,  0},
                    /* 17 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  100,0,   100,0,  0},
                    /* 18 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  100, 100,100,0},
                    /* 19 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  100,0,  0,   100,100,0},
                    /* 20 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,   0,  0,  5},
                    /* 21 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,   0,  0,  4},
                    /* 22 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,   0,  0,  3},
                    /* 23 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,   0,  0,  2},
                    /* 24 */  {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,   0,  0,  2},
                    /* t */   {0, 0,  0,  0,  0,  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,   0,  0,  0},
                    };
        
        MaxFlow m = new MaxFlow(graph.length);

        System.out.println("The maximum possible flow is: " 
                + m.fordFulkerson(graph, 0, 25));

    }
}
