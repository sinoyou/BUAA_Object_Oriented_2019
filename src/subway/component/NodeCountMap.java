package subway.component;

import com.oocourse.specs3.models.Path;
import subway.tool.Constant;

import java.util.HashMap;
import java.util.Iterator;

public class NodeCountMap {
    private HashMap<Integer, Integer> countMap;

    public NodeCountMap() {
        countMap = new HashMap<>(Constant.maxGraphDistinctNode);
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

    public Iterator<Integer> nodeSet() {
        return countMap.keySet().iterator();
    }
}
