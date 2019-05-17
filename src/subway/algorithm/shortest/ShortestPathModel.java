package subway.algorithm.shortest;

import subway.component.node.NodeCountMap;
import subway.component.link.LinkContainer;
import subway.tool.Constant;
import subway.tool.ConvertMap;
import subway.tool.Matrix;
import subway.tool.VersionMark;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class ShortestPathModel {
    // Version Control
    private VersionMark versionMark;
    private int graphVersion;
    private HashMap<Integer, Integer> nodeVersion;

    // Graph : node number is actual
    private LinkContainer linkContainer;
    private NodeCountMap nodeCountMap;

    // convert map: node number is virtual.
    private ConvertMap convertMap;
    // weight graph part: node number is virtual.
    private Matrix weightGraph;

    // result cache: node number is actual.
    private Matrix resultCache;

    public ShortestPathModel(LinkContainer linkContainer,
                             NodeCountMap nodeCountMap,
                             VersionMark versionMark) {
        this.linkContainer = linkContainer;
        this.nodeCountMap = nodeCountMap;
        this.versionMark = versionMark;

        graphVersion = 0;
        nodeVersion = new HashMap<>(Constant.maxGraphDistinctNode);
        resultCache = new Matrix();
    }


    /**
     * Require: node From and node To must be connected to each other.
     * NOTICE: node number defined here is actual node, only when call update
     * method, virtual node will use.
     *
     * @param from node From
     * @param to   node To
     * @return lowestCost based on different graph edge weight.
     */
    public int getLowestRoadWeight(int from, int to) {
        if (!nodeVersion.containsKey(from)) {
            nodeResultUpdate(from);
        } else if (!versionMark.isLatest(nodeVersion.get(from))) {
            nodeResultUpdate(from);
        }

        assert versionMark.isLatest(nodeVersion.get(from));
        assert versionMark.isLatest(graphVersion);

        return resultCache.getValue(from, to);
    }

    /**
     * Run this method to update cost result of single src node.
     * NOTICE:
     * 1. For 'one to many' type node, has to use algorithm many times, then
     * combine them together to form final result map.
     * 2. Graph build is not necessary every time, it depends on graph version.
     *
     * @param node The Start Node Number. (Actual Node)
     */
    private void nodeResultUpdate(int node) {
        // update graph
        graphUpdate();

        // Establish result map to record actual node's least cost.
        HashMap<Integer, Integer> oneResult =
            singleSrcSpfa(node, weightGraph, convertMap, nodeCountMap);

        // update result of 'node' and refresh versionMark
        resultCache.addGroup(node, oneResult);
        nodeVersion.put(node, versionMark.getVersion());
        System.err.println(String.format("Node %d of %s update to Version %d",
            node, this.getClass().getName(), graphVersion));
    }

    /**
     * Make sure the weight graph is latest version.
     * 1. Check version, if latest -> no need to build graph and return.
     * <p>
     * 2. Convert all actual nodes in graph into virtual nodes, virtual nodes is
     * split actual node in different path and they must acquire:
     * - point to unique actual node
     * - point to unique pathId
     * Here, we use convertMap to record relation of actNode,virNode,pathId.
     * <p>
     * 3. For each virNode pair in new graph, use implemented method to give
     * correct edge weight. Here, we use Matrix to record weight.
     */
    private void graphUpdate() {
        // check if graph is newest
        if (versionMark.isLatest(graphVersion)) {
            return;
        }

        // initial
        weightGraph = new Matrix();
        convertMap = new ConvertMap();

        // Part 1: Convert Actual Node
        int cnt = 0;

        Iterator<Integer> actualNodeIt = nodeCountMap.nodeSet();
        while (actualNodeIt.hasNext()) {
            int actualNode = actualNodeIt.next();
            Iterator<Integer> pathIdIt =
                nodeCountMap.getPathIdOnNode(actualNode);
            while (pathIdIt.hasNext()) {
                int virNode = ++cnt;
                convertMap.addConvert(actualNode, virNode, pathIdIt.next());
            }
        }

        // Part 2: Link all Virtual Node
        for (int i = 1; i <= cnt; i++) {
            for (int j = 1; j <= cnt; j++) {
                int actNodeI = convertMap.virtual2Actual(i);
                int actNodeJ = convertMap.virtual2Actual(j);
                int pathIdI = convertMap.virtual2PathId(i);
                int pathIdJ = convertMap.virtual2PathId(j);

                Integer value = getEdgeValue(actNodeI, actNodeJ,
                    pathIdI, pathIdJ, linkContainer);

                if (value != null) {
                    weightGraph.addPair(i, j, value);
                    // weightGraph.addPair(j, i, value);
                }
            }
        }

        graphVersion = versionMark.getVersion();
        System.err.println(String.format("Graph of %s update to Version %d",
            this.getClass().getName(), graphVersion));
    }

    /**
     * A single source shortest path algorithm, it runs on different types of
     * weightGraph. It's implemented by SPFA.
     * <p>
     * Here, we set multiple start nodes are all from's virtual nodes.
     * At end, we combine actNodes' virNodes' best answer to form final
     * result HashMap (key is actual node.)
     *
     * @param from        Start node, act node.
     * @param weightGraph Contain edge weights between all virtual node. (if
     *                    null means no edge.)
     * @return hashMap with result[i] = minLength(from -> i)
     */
    private HashMap<Integer, Integer> singleSrcSpfa(int from,
                                                    Matrix weightGraph,
                                                    ConvertMap convertMap,
                                                    NodeCountMap nodeCountMap) {
        HashMap<Integer, Integer> virResult =
            new HashMap<>(Constant.maxGraphDistinctNode * 2);
        LinkedList<Integer> queue = new LinkedList<>();
        HashSet<Integer> vis = new HashSet<>(Constant.maxGraphDistinctNode * 2);


        // Technique 1 : Add all from's virNode to queue with length value 0.
        // length[from] = 0, queue.add(from)
        Iterator<Integer> virNodeIt = convertMap.actual2Virtual(from);
        while (virNodeIt.hasNext()) {
            int virNode = virNodeIt.next();
            virResult.put(virNode, 0);
            queue.addLast(virNode);
        }

        // -------- Code Below Runs On Virtual Node Graph --------
        // SPFA
        while (!queue.isEmpty()) {
            int virNode = queue.removeFirst();
            Iterator<Integer> it = weightGraph.getExistSecondIndex(virNode);
            while (it.hasNext()) {
                int visToNode = it.next();
                vis.remove(visToNode);
                int curLength = virResult.get(virNode);
                int cmpLength = curLength +
                    weightGraph.getValue(virNode, visToNode);

                if (!virResult.containsKey(visToNode)) {
                    virResult.put(visToNode, cmpLength);
                    queue.addLast(visToNode);
                    vis.add(visToNode);
                } else {
                    if (virResult.get(visToNode) > cmpLength) {
                        virResult.put(visToNode, cmpLength);
                        if (!vis.contains(visToNode)) {
                            queue.addLast(visToNode);
                            vis.add(visToNode);
                        }
                    }
                }
            }
        }
        // -------- Code Above Runs Virtual Node Graph --------

        // Visit All Actual Node and Combine Best Value of their virtual nodes.
        HashMap<Integer, Integer> result = new HashMap<>();
        Iterator<Integer> actNodeIt = nodeCountMap.nodeSet();
        while (actNodeIt.hasNext()) {
            int actNode = actNodeIt.next();
            Iterator<Integer> virToNodeIt = convertMap.actual2Virtual(actNode);
            while (virToNodeIt.hasNext()) {
                int virNode = virToNodeIt.next();
                // If virNode not reachable, then skip.
                if (virResult.containsKey(virNode)) {
                    int value = virResult.get(virNode);

                    // result[actNode] = min(virResult[virNode])
                    // * (virNode == convert(actNode).iterator)
                    if (!result.containsKey(actNode)) {
                        result.put(actNode, value);
                    } else if (result.get(actNode) > value) {
                        result.put(actNode, value);
                    }
                }
            }
        }

        return result;
    }

    /**
     * For each sub class extend this model,it has to implement this method to
     * give edge weight in weight graph.
     *
     * @param actNodeI      actual node number i
     * @param actNodeJ      actual node number j
     * @param pathIdI       pathId the virtual node of actNodeI belongs to.
     * @param pathIdJ       pathId the virtual node of actNodeJ belongs to.
     * @param linkContainer Stores details of link between actual nodes.
     * @return road weight value of (virNodeI, virNodeJ) & (virNodeJ, virNodeI),
     * null means needn't add edge.
     */
    public abstract Integer getEdgeValue(int actNodeI, int actNodeJ,
                                         int pathIdI, int pathIdJ,
                                         LinkContainer linkContainer);
}
