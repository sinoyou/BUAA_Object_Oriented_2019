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
        nodePathIdCount = new Matrix();
    }

    public void addOnePath(Path path, int pathId) {
        for (Integer num : path) {
            if (nodePathIdCount.isExist(num, pathId)) {
                int value = nodePathIdCount.getValue(num, pathId);
                nodePathIdCount.addPair(num, pathId, value + 1);
            } else {
                nodePathIdCount.addPair(num, pathId, 1);
            }
        }
    }

    /**
     * Remove one existent path with correct pathId.
     * Require: path must exist in the graph, pathId must correct match it.
     *
     * @param path   path to be removed.
     * @param pathId unique sign of this path.
     */
    public void removeOnePath(Path path, int pathId) {
        for (Integer num : path) {
            int value = nodePathIdCount.getValue(num, pathId);
            if (value == 1) {
                nodePathIdCount.clearPairValue(num, pathId);
            } else {
                nodePathIdCount.addPair(num, pathId, value - 1);
            }
        }
    }

    public int size() {
        return nodePathIdCount.getExistFirstIndexAmount();
    }

    public boolean containsNode(int nodeId) {
        return nodePathIdCount.isFirstIndexExist(nodeId);
    }

    public Iterator<Integer> nodeSet() {
        return nodePathIdCount.getExistFirstIndex();
    }

    /**
     * Given a actual node number, return paths which this node is on.
     *
     * @param node Actual node number
     * @return Iterator of pathId
     */
    public Iterator<Integer> getPathIdOnNode(int node) {
        return nodePathIdCount.getExistSecondIndex(node);
    }
}
