package graph.component;

import com.oocourse.specs2.models.Path;

import java.util.HashMap;
import java.util.Set;

public class NodeCountMap {
    private HashMap<Integer, Integer> countMap;

    public NodeCountMap() {
        countMap = new HashMap<>();
    }

    public void addOnePath(Path path) {
        for (Integer num : path) {
            int cnt;
            if (countMap.containsKey(num)) {
                cnt = countMap.get(num);
                countMap.replace(num, cnt + 1);
            } else {
                cnt = 1;
                countMap.put(num, cnt);
            }
        }
    }

    public void removeOnePath(Path path) {
        for (Integer num : path) {
            int cnt;
            assert (countMap.containsKey(num));
            cnt = countMap.get(num);
            if (cnt == 1) {
                countMap.remove(num);
            } else {
                countMap.replace(num, cnt - 1);
            }
        }
    }

    public int size() {
        return countMap.size();
    }

    public boolean containsNode(int nodeId) {
        return countMap.containsKey(nodeId);
    }

    public Set<Integer> nodeSet() {
        return countMap.keySet();
    }
}
