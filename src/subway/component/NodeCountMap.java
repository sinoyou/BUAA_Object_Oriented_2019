package subway.component;

import com.oocourse.specs3.models.Path;
import subway.tool.Constant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class NodeCountMap {
    private HashMap<Integer, Integer> countMap;
    private HashMap<Integer, HashSet<Integer>> nodePathIdCount;

    public NodeCountMap() {
        countMap = new HashMap<>(Constant.maxGraphDistinctNode);
        nodePathIdCount = new HashMap<>(Constant.maxGraphDistinctNode);
    }

    public void addOnePath(Path path, int pathId) {
        for (Integer num : path) {
            int cnt;
            if (countMap.containsKey(num)) {
                cnt = countMap.get(num);
                countMap.replace(num, cnt + 1);
            } else {
                cnt = 1;
                countMap.put(num, cnt);
            }

            if(!nodePathIdCount.containsKey(num)){
                nodePathIdCount.put(num, new HashSet<>());
            }
            nodePathIdCount.get(num).add(pathId);
        }
    }

    public void removeOnePath(Path path, int pathId) {
        for (Integer num : path) {
            int cnt;
            assert (countMap.containsKey(num));
            cnt = countMap.get(num);
            if (cnt == 1) {
                countMap.remove(num);
            } else {
                countMap.replace(num, cnt - 1);
            }

            // 可能出现 1 1 1 1 1 型序列当删除一次后就不需要删除了。
            if(nodePathIdCount.containsKey(num)){
                nodePathIdCount.get(num).remove(pathId);
                if(nodePathIdCount.get(num).isEmpty()){
                    nodePathIdCount.remove(num);
                }
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

    public Iterator<Integer> getPathIdOnNode(int node){
        return nodePathIdCount.get(node).iterator();
    }
}
