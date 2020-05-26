
package maze;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author mnprtpsingh
 */
public class UnionFind {
    private final int size;
    private final HashMap<String, String> parent;
    private final HashMap<String, Integer> weight;
    
    public UnionFind(ArrayList<GraphEdge<String>> items) {
        this.size = items.size();
        this.parent = new HashMap<>();
        this.weight = new HashMap<>();
        for (int i = 0; i < this.size; i++) {
            GraphEdge<String> item = items.get(i);
            this.parent.put(item.v1, item.v1);
            this.parent.put(item.v2, item.v2);
            this.weight.put(item.v1, 1);
            this.weight.put(item.v2, 1);
        }
    }
    
    // Weighted Union
    public void union(String x, String y) {
        int weightX = this.weight.get(x);
        int weightY = this.weight.get(y);
        int total = weightX + weightY;
        if (weightX < weightY) {
            this.parent.put(x, y);
            this.weight.put(y, total);
        } else {
            this.parent.put(y, x);
            this.weight.put(x, total);
        }
    }
    
    // Collapsing Find
    public String find(String x) {
        String y = new String(x);
        while (!this.parent.get(y).equals(y))
            y = this.parent.get(y);
        while (!this.parent.get(x).equals(x)) {
            String t = this.parent.get(x);
            this.parent.put(x, y);
            x = t;
        }
        return y;
    }
}
