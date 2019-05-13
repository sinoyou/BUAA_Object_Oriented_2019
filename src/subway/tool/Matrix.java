package subway.tool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Matrix {
    private HashMap<Integer, HashMap<Integer, Integer>> linkMap;

    public Matrix() {
        linkMap = new HashMap<>();
    }

    public void addPair(int a, int b, int value) {
        add(a, b, value);
    }

    public void deletePair(int a, int b) {
        assert isExist(a, b);
        HashMap<Integer, Integer> map = linkMap.get(a);
        map.remove(b);
        if (map.isEmpty()) {
            linkMap.remove(a, map);
        }
    }

    // occasion: map refresh after single source algorithm.
    public void addGroup(int node, HashMap<Integer, Integer> map) {
        HashMap<Integer, Integer> mapCopy = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            mapCopy.put(entry.getKey(), entry.getValue());
        }
        linkMap.put(node, mapCopy);
    }

    public boolean isExist(int a, int b) {
        if (linkMap.containsKey(a)) {
            return linkMap.get(a).containsKey(b);
        }
        return false;
    }

    public int getValue(int a, int b) {
        assert isExist(a, b);
        return linkMap.get(a).get(b);
    }

    private void add(int from, int to, int value) {
        if (!linkMap.containsKey(from)) {
            HashMap<Integer, Integer> map = new HashMap<>();
            map.put(to, value);
            linkMap.put(from, map);
        } else {
            linkMap.get(from).put(to, value);
        }
    }

    public int getExistFirstIndexAmount() {
        return linkMap.keySet().size();
    }

    public boolean isFirstIndexExist(int from) {
        return linkMap.containsKey(from);
    }

    public Iterator<Integer> getExistFirstIndex() {
        return linkMap.keySet().iterator();
    }

    public Iterator<Integer> getExistSecondIndex(int from) {
        return linkMap.get(from).keySet().iterator();
    }

}
