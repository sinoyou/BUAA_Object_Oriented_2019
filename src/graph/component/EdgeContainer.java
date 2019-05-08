package graph.component;

import com.oocourse.specs2.models.Path;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class EdgeContainer {
    /**
     * 1.edgeMap: Use double-layer HashMap to record the number of edges.
     * edgeMap[i][j] = 3 means there're 3 edges from i to j.
     * TIPS:
     * When edge[i][j] = 0, <j, count> as a value will be removed.
     * When edge[i].size() = 0, <i, hashMap> as a value will be removed.
     */
    private HashMap<Integer, HashMap<Integer, Integer>> edgeMap;

    public EdgeContainer() {
        edgeMap = new HashMap<>();
    }

    public void addOnePath(Path path) {
        assert (path != null && path.isValid());
        int length = path.size();
        // normal occasion
        for (int i = 0; i <= length - 2; i++) {
            int cur = path.getNode(i);
            int next = path.getNode(i + 1);
            addOneEdge(cur, next);
            addOneEdge(next, cur);
        }
    }

    /**
     * Require: Path must have been added to edgeMap.
     * !!! Run this method without the requirement will cause unpredictable bug.
     * 1. Remove edges of path in edgeMap.
     * 2. Update version mark.
     */
    public void removeOnePath(Path path) {
        assert (path != null && path.isValid());
        int length = path.size();
        // normal occasion
        for (int i = 0; i <= length - 2; i++) {
            int cur = path.getNode(i);
            int next = path.getNode(i + 1);
            removeOneEdge(cur, next);
            removeOneEdge(next, cur);
        }
    }

    public Iterator<Integer> getConnectNodes(int node) {
        assert edgeMap.containsKey(node);
        Set<Integer> set = edgeMap.get(node).keySet();
        assert !set.isEmpty();
        return set.iterator();
    }

    public boolean containsEdge(int from, int to) {
        if (edgeMap.containsKey(from)) {
            return edgeMap.get(from).containsKey(to);
        } else {
            return false;
        }
    }

    public boolean containsKey(int node) {
        return edgeMap.containsKey(node);
    }

    /* -------- Inner Maintain Function --------*/

    /**
     * 1. get from's hashMap. If not exists, create one.
     * 2. add or update to's value in from's hashMap.
     */
    private void addOneEdge(int from, int to) {
        if (!edgeMap.containsKey(from)) {
            edgeMap.put(from, new HashMap<>());
        }
        HashMap<Integer, Integer> map = edgeMap.get(from);

        if (!map.containsKey(to)) {
            map.put(to, 1);
        } else {
            int count = map.get(to);
            map.replace(to, count + 1);
        }
    }

    /**
     * 1. Get from's hashMap.(hashMap must exists)
     * 2. remove or update to's value in from's hashMap.(hashMap must exists)
     * 3. If from's hashMap is empty, remove it in edgeMap.
     */
    private void removeOneEdge(int from, int to) {
        assert edgeMap.containsKey(from);
        if (edgeMap.containsKey(from)) {
            HashMap<Integer, Integer> map = edgeMap.get(from);
            assert map.containsKey(to);
            if (map.containsKey(to)) {
                int count = map.get(to);
                if (count == 1) {
                    map.remove(to, count);
                } else {
                    map.replace(to, count - 1);
                }

                if (map.isEmpty()) {
                    edgeMap.remove(from, map);
                }
            }
        }
    }
}
