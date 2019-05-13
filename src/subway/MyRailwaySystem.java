package subway;

import com.oocourse.specs3.models.NodeIdNotFoundException;
import com.oocourse.specs3.models.NodeNotConnectedException;
import com.oocourse.specs3.models.Path;
import com.oocourse.specs3.models.PathIdNotFoundException;
import com.oocourse.specs3.models.PathNotFoundException;
import com.oocourse.specs3.models.RailwaySystem;
import subway.algorithm.InfectGraph;
import subway.algorithm.shortest.ShortestPathModel;
import subway.component.DoubleDirPathMap;
import subway.component.link.LinkContainer;
import subway.component.NodeCountMap;
import subway.tool.AlgorithmFactory;
import subway.tool.VersionMark;

public class MyRailwaySystem implements RailwaySystem {
    // private HashMap<Integer, Path> doubleDirMap;
    private DoubleDirPathMap doubleDirMap;
    private NodeCountMap nodeCountMap;
    private LinkContainer linkContainer;
    private InfectGraph infectGraph;
    private int idCnt;
    private VersionMark versionMark;

    /* -------- New Algorithm Class in HM11 -------- */
    private ShortestPathModel shortestPath;
    private ShortestPathModel leastTransfer;
    private ShortestPathModel lowestTicketPrice;
    private ShortestPathModel leastUnpleasant;

    public MyRailwaySystem() throws Exception {
        idCnt = 0;
        doubleDirMap = new DoubleDirPathMap();
        nodeCountMap = new NodeCountMap();
        versionMark = new VersionMark();
        // Graph Theory Part
        linkContainer = new LinkContainer();
        infectGraph = new InfectGraph(linkContainer,
            nodeCountMap, versionMark);
        // newly added in hm11
        AlgorithmFactory algorithmFactory = new AlgorithmFactory(linkContainer,
            nodeCountMap, versionMark);
        shortestPath = algorithmFactory.produce("ShortestPath");
        leastTransfer = algorithmFactory.produce("LeastTransfer");
        lowestTicketPrice = algorithmFactory.produce("LowestPrice");
        leastUnpleasant = algorithmFactory.produce("LeastUnpleasant");
    }

    @Override
    public int size() {
        return doubleDirMap.size();
    }

    @Override
    public boolean containsPath(Path path) {
        return doubleDirMap.containsPath(path);
    }

    @Override
    public boolean containsPathId(int pathId) {
        return doubleDirMap.containsId(pathId);
    }

    @Override
    public Path getPathById(int pathId) throws PathIdNotFoundException {
        if (containsPathId(pathId)) {
            return doubleDirMap.getPathById(pathId);
        } else {
            throw new PathIdNotFoundException(pathId);
        }
    }

    @Override
    public int getPathId(Path path) throws PathNotFoundException {
        if (path == null) {
            throw new PathNotFoundException(path);
        } else if (!path.isValid()) {
            throw new PathNotFoundException(path);
        } else if (!containsPath(path)) {
            throw new PathNotFoundException(path);
        } else {
            return doubleDirMap.getIdByPath(path);
        }
    }

    @Override
    public int getDistinctNodeCount() {
        return nodeCountMap.size();
    }

    @Override
    public boolean containsNode(int i) {
        return nodeCountMap.containsNode(i);
    }

    /* -------- New Path Method in HM 11 --------*/

    @Override
    public int getUnpleasantValue(Path path, int i, int i1) {
        return 0;
    }

    /* -------- Modification Type Method --------*/
    @Override
    public int addPath(Path path) throws PathNotFoundException {
        if (path != null && path.isValid()) {
            if (!containsPath(path)) {
                idCnt++;
                addOnePath(path, idCnt);
                return idCnt;
            } else {
                return getPathId(path);
            }
        } else {
            return 0;
        }
    }

    @Override
    public int removePath(Path path) throws PathNotFoundException {
        if (path == null) {
            throw new PathNotFoundException(path);
        } else if (!path.isValid()) {
            throw new PathNotFoundException(path);
        } else if (!containsPath(path)) {
            throw new PathNotFoundException(path);
        } else {
            int pathId = getPathId(path);
            removeOnePath(path, pathId);
            return pathId;
        }
    }

    @Override
    public void removePathById(int pathId) throws PathIdNotFoundException {
        if (!containsPathId(pathId)) {
            throw new PathIdNotFoundException(pathId);
        } else {
            Path path = doubleDirMap.getPathById(pathId);
            removeOnePath(path, pathId);
        }
    }

    /* -------- Graph Theory Method --------*/

    @Override
    public boolean containsEdge(int i, int i1) {
        return linkContainer.containsEdge(i, i1);
    }

    @Override
    public boolean isConnected(int i, int i1) throws NodeIdNotFoundException {
        if (!containsNode(i)) {
            throw new NodeIdNotFoundException(i);
        } else if (!containsNode(i1)) {
            throw new NodeIdNotFoundException(i1);
        } else {
            if (i == i1) {
                return true;
            } else {
                return infectGraph.isNodesConnected(i, i1);
            }
        }
    }

    @Override
    public int getShortestPathLength(int i, int i1)
        throws NodeIdNotFoundException, NodeNotConnectedException {
        return costQuery(i, i1, shortestPath);
        /*
        if (!containsNode(i)) {
            throw new NodeIdNotFoundException(i);
        } else if (!containsNode(i1)) {
            throw new NodeIdNotFoundException(i1);
        } else if (!isConnected(i, i1)) {
            throw new NodeNotConnectedException(i, i1);
        } else {
            if (i == i1) {
                return 0;
            } else {
                return shortestPath.getLowestCost(i,i1);
                // return infectGraph.getShortestRoadLength(i, i1);
            }
        }
        */
    }

    /* -------- New Graph Theory Method in HM 11 -------- */

    @Override
    public int getConnectedBlockCount() {
        return infectGraph.getConnectedBlocks();
    }

    @Override
    public int getLeastTransferCount(int i, int i1)
        throws NodeIdNotFoundException, NodeNotConnectedException {
        return costQuery(i, i1, leastTransfer);
    }

    @Override
    public int getLeastTicketPrice(int i, int i1)
        throws NodeIdNotFoundException, NodeNotConnectedException {
        return costQuery(i, i1, lowestTicketPrice);
    }

    @Override
    public int getLeastUnpleasantValue(int i, int i1)
        throws NodeIdNotFoundException, NodeNotConnectedException {
        return costQuery(i, i1, leastUnpleasant);
    }


    /* -------- Inner Maintain Method -------- */
    private void addOnePath(Path path, int pathId) {
        versionMark.versionUpdate();
        doubleDirMap.put(pathId, path);
        nodeCountMap.addOnePath(path, pathId);
        linkContainer.addOnePath(path, pathId);
    }

    private void removeOnePath(Path path, int pathId) {
        versionMark.versionUpdate();
        doubleDirMap.remove(pathId, path);
        nodeCountMap.removeOnePath(path, pathId);
        linkContainer.removeOnePath(path, pathId);
    }

    private int costQuery(int i, int i1, ShortestPathModel model)
        throws NodeNotConnectedException, NodeIdNotFoundException {
        if (!containsNode(i)) {
            throw new NodeIdNotFoundException(i);
        } else if (!containsNode(i1)) {
            throw new NodeIdNotFoundException(i1);
        } else if (!isConnected(i, i1)) {
            throw new NodeNotConnectedException(i, i1);
        } else {
            if (i == i1) {
                return 0;
            } else {
                return model.getLowestCost(i, i1);
                // return infectGraph.getShortestRoadLength(i, i1);
            }
        }
    }

}
