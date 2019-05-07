package graph.component;

import com.oocourse.specs2.models.Path;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class EdgeContainer {
    private HashMap<Integer, HashMap<Integer, Integer>> edgeMap;
    private int versionMark;

    public EdgeContainer() {
        edgeMap = new HashMap<>();
        versionMark = 0;
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

        versionMark++;
    }

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

        versionMark++;
    }

    public Iterator<Integer> getConnectNodes(int node) {
        assert edgeMap.containsKey(node);
        Set<Integer> set = edgeMap.get(node).keySet();
        assert !set.isEmpty();
        return set.iterator();
    }

    public int getVersionMark() {
        return versionMark;
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
        HashMap<Integer, Integer> map = edgeMap.get(from);

        assert map.containsKey(to);
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
