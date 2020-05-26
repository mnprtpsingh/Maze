
package maze;

import java.util.Random;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 *
 * @author mnprtpsingh
 */
public class Maze {
    public static final int X = 21;
    public static final int Y = 75;
    
    private static char [][] maze;
    private static Random random;
    private static ArrayList<GraphEdge<String>> graph;
    private static ArrayList<GraphEdge<String>> spanningTree;
    
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_RESET = "\u001B[0m";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        random = new Random();
        graph = new ArrayList<>();
        createGraph();
        
        spanningTree = new ArrayList<>();
        kruskal();
        
        maze = new char[X][Y];
        createMaze();
        displayMaze();
    }
    
    private static void createGraph() {
        // Horizontal Edges
        for (int i = 0; i < X; i += 2) {
            for (int j = 2; j < Y; j += 2) {
                String v1 = Integer.toString(i) + "," + Integer.toString(j-2);
                String v2 = Integer.toString(i) + "," + Integer.toString(j);
                int e = random.nextInt(1000);
                graph.add(new GraphEdge(v1, v2, e));
            }
        }
        
        // Vertical Edges
        for (int i = 2; i < X; i += 2) {
            for (int j = 0; j < Y; j += 2) {
                String v1 = Integer.toString(i-2) + "," + Integer.toString(j);
                String v2 = Integer.toString(i) + "," + Integer.toString(j);
                int e = random.nextInt(1000);
                graph.add(new GraphEdge(v1, v2, e));
            }
        }
    }
    
    private static void kruskal() {
        PriorityQueue<GraphEdge> pq = new PriorityQueue<>(graph);
        UnionFind uf = new UnionFind(graph);
        for (int i = 0; i < graph.size(); i++) {
            GraphEdge<String> edge = pq.remove();
            String v1 = uf.find(edge.v1);
            String v2 = uf.find(edge.v2);
            if (!v1.equals(v2)) {
                spanningTree.add(edge);
                uf.union(v1, v2);
            }
        }
    }
    
    private static void createMaze() {
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                maze[i][j] = '#';
            }
        }
        
        for (int i = 0; i < spanningTree.size(); i++) {
            GraphEdge<String> edge = spanningTree.get(i);
            String [] v1 = edge.v1.split(",");
            String [] v2 = edge.v2.split(",");
            int x1 = Integer.parseInt(v1[0]);
            int y1 = Integer.parseInt(v1[1]);
            int x2 = Integer.parseInt(v2[0]);
            if (x1 != x2) {
                maze[x1][y1] = maze[x1+1][y1] = maze[x1+2][y1] = '.';
            } else {
                maze[x1][y1] = maze[x1][y1+1] = maze[x1][y1+2] = '.';
            }
        }
    }
    
    public static void displayMaze() {
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                if (maze[i][j] == '.') {
                    System.out.print(ANSI_CYAN_BACKGROUND);
                    System.out.print(maze[i][j]);
                    System.out.print(ANSI_RESET);
                } else {
                    System.out.print(maze[i][j]);
                }
            }
            System.out.println();
        }
    }
}
