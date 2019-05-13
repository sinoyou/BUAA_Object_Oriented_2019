package subway.algorithm;

import subway.component.link.LinkContainer;
import subway.component.NodeCountMap;
import subway.tool.Constant;
import subway.tool.VersionMark;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class InfectGraph {
    private LinkContainer linkContainer;
    private HashMap<Integer, Integer> colorMap;
    private NodeCountMap nodeCountMap;

    // version control
    private VersionMark versionMark;
    private int edgeVersion;

    // connected block number cache
    private int connectedBlocksCache;

    public InfectGraph(LinkContainer linkContainer,
                       NodeCountMap nodeCountMap,
                       VersionMark versionMark) {
        this.linkContainer = linkContainer;
        this.nodeCountMap = nodeCountMap;
        this.colorMap = new HashMap<>(Constant.maxGraphDistinctNode);
        edgeVersion = 0;
        this.versionMark = versionMark;
        // New in HM 11
        connectedBlocksCache = 0;
    }

    /**
     * Require: from exists && to exists
     * 1. Check version stamp, if not matched then flush and call bfs.
     * 2. Check connection.
     */
    public boolean isNodesConnected(int from, int to) {
        versionCheck();
        if (colorMap.containsKey(from) && colorMap.containsKey(to)) {
            return colorMap.get(from).equals(colorMap.get(to));
        } else {
            return false;
        }
    }


    public int getConnectedBlocks() {
        versionCheck();
        return connectedBlocksCache;
    }

    /* -------- Inner Maintain Function --------*/

    /**
     * If version can not match.
     * 1. Flush colorMap.
     * 2. Use colorInfect algorithm rebuild colorMap.
     * 3. Update versionMark.
     */
    private void versionCheck() {
        // run map
        if (!versionMark.isLatest(edgeVersion)) {
            colorMap.clear();
            colorInfect();

            // Version and Cache Update
            connectedBlocksCache = connectedBlocksCount();
            edgeVersion = versionMark.getVersion();
        }
    }


    private int connectedBlocksCount() {
        HashSet<Integer> set = new HashSet<>(colorMap.values());
        return set.size();
    }


    /**
     * Use bfs to color each node
     * 1. Get keySet of LinkContainer.
     * 2. If the node has been colored by others -> skip.
     * 3. Else create a new color type and bfs to color others.
     */
    private void colorInfect() {
        Iterator<Integer> it = nodeCountMap.nodeSet();
        int colorCnt = 0;
        while (it.hasNext()) {
            int node = it.next();
            assert linkContainer.containsKey(node);
            if (!colorMap.containsKey(node)) {
                colorCnt++;
                bfsSingle(node, colorCnt);
            }
        }
    }

    private void bfsSingle(int nodeId, int color) {
        LinkedList<Integer> queue = new LinkedList<>();

        // initial: length[nodeId] = 0
        colorMap.put(nodeId,color);
        queue.addLast(nodeId);

        while (!queue.isEmpty()) {
            int node = queue.removeFirst();
            // protect programming
            assert linkContainer.containsKey(node);
            if (!linkContainer.containsKey(node)) {
                continue;
            }
            Iterator<Integer> it = linkContainer.getConnectNodes(node);
            while (it.hasNext()) {
                int nodeTo = it.next();
                if(!colorMap.containsKey(nodeTo)){
                    colorMap.put(nodeTo,color);
                    if (!queue.contains(nodeTo)) {
                        queue.addLast(nodeTo);
                    }
                }
            }
        }

    }
}
