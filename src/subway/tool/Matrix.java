package subway.tool;

import java.util.HashMap;
import java.util.Iterator;

public class Matrix {
    private HashMap<Integer, HashMap<Integer, Integer>> linkMap;

    public Matrix() {
        linkMap = new HashMap<>();
    }

    public void addPair(int a, int b, int value) {
        add(a, b, value);
    }

    // occasion: map refresh after single source algorithm.
    public void addGroup(int node, HashMap<Integer,Integer> map){
        linkMap.put(node,map);
    }

    public boolean isExist(int a, int b) {
        if (a == b) {
            return true;
        }
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

    public Iterator<Integer> getNoNullIndex(int from){
        return linkMap.get(from).keySet().iterator();
    }
}
