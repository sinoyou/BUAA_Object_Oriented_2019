package hm10.graph.algorithm;

import hm10.graph.component.EdgeContainer;
import hm10.graph.component.NodeCountMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class ShortestRoad {
    private EdgeContainer edgeContainer;
    private HashMap<Integer, HashMap<Integer, Integer>> roadMap;
    private int edgeVersion;
    private NodeCountMap nodeCountMap;

    public ShortestRoad(EdgeContainer edgeContainer,
                        NodeCountMap nodeCountMap) {
        this.edgeContainer = edgeContainer;
        this.nodeCountMap = nodeCountMap;
        this.roadMap = new HashMap<>();
        edgeVersion = 0;
    }

    /**
     * Require: from exists && to exists
     * 1. Check version stamp, if not matched then flush and call SPFA.
     * 2. Check connection.
     */
    public boolean isNodesConnected(int from, int to) {
        stampCheck();
        assert roadMap.containsKey(from);
        assert roadMap.containsKey(to);
        if (roadMap.containsKey(from)) {
            return roadMap.get(from).containsKey(to);
        } else {
            return false;
        }
    }

    /**
     * Require: from exists && to exists && from and to is connected.
     * 1. Check version stamp, if not matched then flush and call SPFA.
     * 2. Get shortest path. (If not connected, then return null.)
     */
    public Integer getShortestRoadLength(int from, int to) {
        stampCheck();
        if (isNodesConnected(from, to)) {
            return roadMap.get(from).get(to);
        } else {
            return null;
        }
    }

    /* -------- Inner Maintain Function --------*/

    /**
     * If version can not match.
     * 1. Flush roadMap.
     * 2. Use spfa algorithm rebuild roadMap.
     * 3. Update versionMark.
     */
    private void stampCheck() {
        if (edgeVersion < edgeContainer.getVersionMark()) {
            roadMap.clear();
            spfa();
            edgeVersion = edgeContainer.getVersionMark();
        }
    }

    /**
     * One of Shortest Path Algorithm.
     * 1. Get keySet of EdgeContainer.
     * 2. For each not-lonely-node, use spfa single-source algorithm to generate
     * a hashMap<targetNode, shortestLength>.
     * 3. Add to RoadMap.
     */
    private void spfa() {
        Set<Integer> nodeSet = nodeCountMap.nodeSet();
        for (Integer node : nodeSet) {
            HashMap<Integer, Integer> lenthMap = spfaSingle(node);
            roadMap.put(node, lenthMap);
        }
    }

    private HashMap<Integer, Integer> spfaSingle(int nodeId) {
        HashMap<Integer, Integer> lengthMap = new HashMap<>();
        LinkedList<Integer> queue = new LinkedList<>();

        // initial: length[nodeId] = 0
        lengthMap.put(nodeId, 0);
        queue.addLast(nodeId);

        // spfa main body: dynamic plan
        // Once one node shortest road length is updated, it have to get into
        // queue for checking and updating other node length it can influence.
        while (!queue.isEmpty()) {
            int node = queue.removeFirst();
            int length = lengthMap.get(node);
            Iterator<Integer> it = edgeContainer.getConnectNodes(node);
            while (it.hasNext()) {
                int nodeTo = it.next();
                // If already visited nodeTo -> check if need update.
                if (lengthMap.containsKey(nodeTo) &&
                    lengthMap.get(nodeTo) > length + 1) {
                    lengthMap.replace(nodeTo, length + 1);
                    if (!queue.contains(nodeTo)) {
                        queue.addLast(nodeTo);
                    }
                }
                // Have not visited nodeTo yet -> update(put)
                else {
                    lengthMap.put(nodeTo, length + 1);
                    if (!queue.contains(nodeTo)) {
                        queue.addLast(nodeTo);
                    }
                }
            }
        }

        // return lengthMap for upper class process
        return lengthMap;
    }
}
