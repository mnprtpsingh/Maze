
package maze;

/**
 *
 * @author mnprtpsingh
 */
public class GraphEdge<V> implements Comparable<GraphEdge> {
    public V v1;
    public V v2;
    public int e;
    
    public GraphEdge(V v1, V v2, int e) {
        this.v1 = v1;
        this.v2 = v2;
        this.e = e;
    }

    @Override
    public int compareTo(GraphEdge o) {
        return this.e - o.e;
    }
    
}
