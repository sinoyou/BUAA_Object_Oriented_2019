package hm10.graph.component;

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
        for (int i = 1; i <= length - 2; i++) {
            int pre = path.getNode(i - 1);
            int cur = path.getNode(i);
            int next = path.getNode(i + 1);
            addOneEdge(cur, pre);
            addOneEdge(cur, next);
        }
        // head & tail occasion
        MyPath myPath = (MyPath) path;
        int head = myPath.getHead(), headNext = myPath.getHeadNext();
        int tail = myPath.getTail(), tailPre = myPath.getTailPre();
        addOneEdge(head, headNext);
        addOneEdge(tail, tailPre);

        versionMark++;
    }

    public void removeOnePath(Path path) {
        assert (path != null && path.isValid());
        int length = path.size();
        // normal occasion
        for (int i = 1; i <= length - 2; i++) {
            int pre = path.getNode(i - 1);
            int cur = path.getNode(i);
            int next = path.getNode(i + 1);
            removeOneEdge(cur, pre);
            removeOneEdge(cur, next);
        }
        // head & tail occasion
        MyPath myPath = (MyPath) path;
        int head = myPath.getHead(), headNext = myPath.getHeadNext();
        int tail = myPath.getTail(), tailPre = myPath.getTailPre();
        removeOneEdge(head, headNext);
        removeOneEdge(tail, tailPre);

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

    public boolean containsKey(int node){
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
