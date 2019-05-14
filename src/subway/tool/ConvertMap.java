package subway.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ConvertMap {
    private HashMap<Integer, ArrayList<Integer>> actualMap;
    private HashMap<Integer, Integer> virtualMap;
    private HashMap<Integer, Integer> pathIdOnVirtual;

    public ConvertMap() {
        actualMap = new HashMap<>(Constant.maxGraphDistinctNode);
        virtualMap = new HashMap<>(Constant.maxGraphDistinctNode * 2);
        pathIdOnVirtual = new HashMap<>(Constant.maxGraphDistinctNode * 2);
    }

    /**
     * virtual to actual : one mapped to one.
     * actual to virtual : one mapped to >=one.
     */
    public void addConvert(int actual, int virtual, int pathId) {
        virtualMap.put(virtual, actual);
        pathIdOnVirtual.put(virtual, pathId);
        if (!actualMap.containsKey(actual)) {
            ArrayList<Integer> list = new ArrayList<>();
            list.add(virtual);
            actualMap.put(actual, list);
        } else {
            actualMap.get(actual).add(virtual);
        }
    }

    public int virtual2Actual(int virtual) {
        assert virtualMap.containsKey(virtual);
        return virtualMap.get(virtual);
    }

    public Iterator<Integer> actual2Virtual(int actual) {
        assert actualMap.containsKey(actual);
        return actualMap.get(actual).iterator();
    }

    public int virtual2PathId(int virtual) {
        return pathIdOnVirtual.get(virtual);
    }
}
