package subway.algorithm.shortest;

import subway.component.NodeCountMap;
import subway.component.link.LinkContainer;
import subway.tool.Constant;
import subway.tool.ConvertMap;
import subway.tool.Matrix;
import subway.tool.VersionMark;

import java.util.HashMap;
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
        nodeVersion = new HashMap<>();
        resultCache = new Matrix();
    }


    /**
     * Require: node From and node To must be connected to each other.
     * NOTICE: node number defined here is actual node, only when call update
     * method, virtual node will use.
     * @param from node From
     * @param to node To
     * @return lowestCost based on different graph edge weight.
     */
    public int getLowestCost(int from, int to) {
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
                    if(oneResult.containsKey(virToNode)){
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
            weightGraph = new Matrix();
            for (int i = 1; i <= cnt; i++) {
                for (int j = 1; j <= cnt; j++) {
                    int actNodeI = convertMap.virtual2Actual(i);
                    int actNodeJ = convertMap.virtual2Actual(j);
                    int pathIdI = convertMap.virtual2PathId(i);
                    int pathIdJ = convertMap.virtual2PathId(j);

                    Integer value = getEdgeValue(actNodeI,actNodeJ,
                        pathIdI,pathIdJ,linkContainer);

                    if(value != null){
                        weightGraph.addPair(i,j,value);
                        weightGraph.addPair(j,i,value);
                    }
                }
            }

            graphVersion = versionMark.getVersion();
        }
    }

    /**
     * A single source shortest path algorithm, it runs on different types of
     * weightGraph. It's implemented by SPFA.
     * @param from Start node, virtual node number - can only map one pathId.
     * @param weightGraph Contain edge weights between all virtual node. (if
     *                    null means no edge.)
     * @return hashMap with result[i] = minLength(from -> i)
     */
    private HashMap<Integer, Integer> singleSrcAlgorithm(int from,
                                                         Matrix weightGraph) {
        HashMap<Integer,Integer> result = new HashMap<>(Constant.maxGraphDistinctNode);
        LinkedList<Integer> queue = new LinkedList<>();

        // length[from] = 0, queue.add(from)
        result.put(from,0);
        queue.addLast(from);

        // SPFA
        while(!queue.isEmpty()){
            int virNode = queue.removeFirst();
            Iterator<Integer> it = weightGraph.getNoNullIndex(virNode);
            while(it.hasNext()){
                int visToNode = it.next();
                int curLength = result.get(virNode);
                if(!result.containsKey(visToNode)){
                    result.put(visToNode,
                        curLength + weightGraph.getValue(virNode,visToNode));
                    queue.addLast(visToNode);
                }else{
                    if(result.get(visToNode) >
                        curLength + weightGraph.getValue(virNode,visToNode)){
                        result.put(visToNode,
                            curLength + weightGraph.getValue(virNode,visToNode));
                        if(!queue.contains(visToNode)){
                            queue.addLast(visToNode);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * For each sub class extend this model,it has to implement this method to
     * give edge weight in weight graph.
     * @param actNodeI actual node number i
     * @param actNodeJ actual node number j
     * @param pathIdI pathId the virtual node of actNodeI belongs to.
     * @param pathIdJ pathId the virtual node of actNodeJ belongs to.
     * @param linkContainer Stores details of link between actual nodes.
     * @return road weight value of (virNodeI, virNodeJ) & (virNodeJ, virNodeI),
     *         null means needn't add edge.
     */
    public abstract Integer getEdgeValue(int actNodeI,int actNodeJ,
                                int pathIdI, int pathIdJ,
                                LinkContainer linkContainer);
}
