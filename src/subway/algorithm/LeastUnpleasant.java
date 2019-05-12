package subway.algorithm;

import subway.component.NodeCountMap;
import subway.component.link.LinkContainer;
import subway.tool.ConvertMap;
import subway.tool.Matrix;
import subway.tool.VersionMark;

import java.util.HashMap;
import java.util.Iterator;

public class LeastUnpleasant implements CostGraph {
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

    public LeastUnpleasant(LinkContainer linkContainer, VersionMark versionMark,
                           NodeCountMap nodeCountMap) {
        this.linkContainer = linkContainer;
        this.nodeCountMap = nodeCountMap;
        this.versionMark = versionMark;

        graphVersion = 0;
        nodeVersion = new HashMap<>();
        resultCache = new Matrix();
    }

    public int getLowestCost(int from, int to) {
        if (!resultCache.isExist(from, to)) {
            nodeResultUpdate(from);
        } else if (!nodeVersion.containsKey(from)) {
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
        HashMap<Integer, Integer> result = new HashMap<>();
        Iterator<Integer> virNodeIt = convertMap.actual2Virtual(node);
        while (virNodeIt.hasNext()) {
            // calculate each vir node result that actual node mapped to.
            int virNode = virNodeIt.next();
            HashMap<Integer, Integer> oneResult = singleSrcAlgorithm(virNode, weightGraph);

            // combine the result
            Iterator<Integer> toNodeIt = nodeCountMap.nodeSet();
            // foreach actual ToNode:
            while (toNodeIt.hasNext()) {
                int toNode = toNodeIt.next();
                Iterator<Integer> virToNodeIt = convertMap.actual2Virtual(toNode);
                // foreach ToNode's virtual node:
                while (virToNodeIt.hasNext()) {
                    int virToNode = virToNodeIt.next();
                    int cost = oneResult.get(virToNode);
                    if (!result.containsKey(toNode)) {
                        result.put(toNode, cost);
                    } else {
                        if (result.get(toNode) > cost) {
                            result.put(toNode, cost);
                        }
                    }
                }
            }
        }

        // update result of 'node' and refresh versionMark
        resultCache.addGroup(node, result);
        nodeVersion.put(node, versionMark.getVersion());
    }

    private void graphUpdate() {
        // check if graph is newest
        if (!versionMark.isLatest(graphVersion)) {

            // Part 1: Convert Actual Node
            int cnt = 0;
            convertMap = new ConvertMap();

            Iterator<Integer> actualNodeIt = nodeCountMap.nodeSet();
            while (actualNodeIt.hasNext()) {
                int actualNode = actualNodeIt.next();
                Iterator<Integer> pathIdIt = nodeCountMap.getPathIdOnNode(actualNode);
                while (pathIdIt.hasNext()) {
                    int virNode = ++cnt;
                    convertMap.addConvert(actualNode, virNode, pathIdIt.next());
                }
            }

            // Part 2: Link all Virtual Node
            // TODO with multiple strategy - present: unpleasant
            weightGraph = new Matrix();
            for (int i = 1; i <= cnt; i++) {
                for (int j = 1; j <= cnt; j++) {
                    int actNodeI = convertMap.virtual2Actual(i);
                    int actNodeJ = convertMap.virtual2Actual(j);
                    int pathIdI = convertMap.virtual2PathId(i);
                    int pathIdJ = convertMap.virtual2PathId(j);
                    if (actNodeI == actNodeJ) {
                        if (pathIdI == pathIdJ) {
                            weightGraph.addPair(i, j, 0);
                            weightGraph.addPair(j, i, 0);
                        } else {
                            weightGraph.addPair(i, j, 32);
                            weightGraph.addPair(j, i, 32);
                        }
                    } else if(linkContainer.containsEdge(actNodeI,actNodeJ)){
                        if (pathIdI == pathIdJ) {
                            int value = Math.max((actNodeI % 5 + 5) % 5,
                                (actNodeJ % 5 + 5) % 5);
                            weightGraph.addPair(i,j,value);
                            weightGraph.addPair(j,i,value);
                        }
                    }
                }
            }

            graphVersion = versionMark.getVersion();
        }
    }

    private HashMap<Integer, Integer> singleSrcAlgorithm(int from,
                                                         Matrix weightGraph) {
        return null;
    }
}
