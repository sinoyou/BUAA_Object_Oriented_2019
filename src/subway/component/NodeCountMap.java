package subway.component;

import com.oocourse.specs3.models.Path;
import subway.tool.Matrix;

import java.util.Iterator;

public class NodeCountMap {
    // For one node, how many paths does it exist now and how many times does it
    // exist on one certain path.
    // private HashMap<Integer, HashMap<Integer, Integer>> nodePathIdCount;
    private Matrix nodePathIdCount;

    public NodeCountMap() {
        // countMap = new HashMap<>(Constant.maxGraphDistinctNode);
        // nodePathIdCount = new HashMap<>(Constant.maxGraphDistinctNode);
        nodePathIdCount = new Matrix();
    }


    public void addOnePath(Path path, int pathId) {
        for (Integer num : path) {
            /*
            int cnt;
            if (countMap.containsKey(num)) {
                cnt = countMap.get(num);
                countMap.replace(num, cnt + 1);
            } else {
                cnt = 1;
                countMap.put(num, cnt);
            }

            if (!nodePathIdCount.containsKey(num)) {
                nodePathIdCount.put(num, new HashMap<>());
            }

            HashMap<Integer,Integer> map = nodePathIdCount.get(num);
            if(map.containsKey(pathId)){
                int cnt = map.get(pathId);
                map.replace(pathId,cnt+1);
            }else {
                map.put(pathId,1);
            }
            */
            if(nodePathIdCount.isExist(num,pathId)){
                int value = nodePathIdCount.getValue(num,pathId);
                nodePathIdCount.addPair(num,pathId,value+1);
            }else {
                nodePathIdCount.addPair(num,pathId,1);
            }
        }
    }

    /**
     * Remove one existent path with correct pathId.
     * Require: path must exist in the graph, pathId must correct match it.
     *
     * @param path
     * @param pathId
     */
    public void removeOnePath(Path path, int pathId) {
        for (Integer num : path) {
            /*
            int cnt;
            assert (countMap.containsKey(num));
            cnt = countMap.get(num);
            if (cnt == 1) {
                countMap.remove(num);
            } else {
                countMap.replace(num, cnt - 1);
            }

            assert nodePathIdCount.containsKey(num);
            HashMap<Integer,Integer> map = nodePathIdCount.get(num);
            assert map.containsKey(pathId);
            int cnt = map.get(pathId);
            if(cnt == 1){
                nodePathIdCount.remove(pathId,map);
            }else {
                map.replace(pathId,cnt-1);
            }
            */
            int value = nodePathIdCount.getValue(num,pathId);
            if(value == 1){
                nodePathIdCount.clearPairValue(num,pathId);
            }else {
                nodePathIdCount.addPair(num,pathId,value - 1);
            }
        }
    }

    public int size() {
        // return countMap.size();
        // return nodePathIdCount.size();
        return nodePathIdCount.getExistFirstIndexAmount();
    }

    public boolean containsNode(int nodeId) {
        // return countMap.containsKey(nodeId);
        // return nodePathIdCount.containsKey(nodeId);
        return nodePathIdCount.isFirstIndexExist(nodeId);
    }

    public Iterator<Integer> nodeSet() {
        // return countMap.keySet().iterator();
        // return nodePathIdCount.keySet().iterator();
        return nodePathIdCount.getExistFirstIndex();
    }

    /**
     * Given a actual node number, return paths which this node is on.
     * @param node Actual node number
     * @return Iterator of pathId
     */
    public Iterator<Integer> getPathIdOnNode(int node) {
        // return nodePathIdCount.get(node).keySet().iterator();
        return nodePathIdCount.getExistSecondIndex(node);
    }
}
