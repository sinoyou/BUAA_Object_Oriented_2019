package subway;

import com.oocourse.specs3.models.*;
import subway.algorithm.ShortestRoad;
import subway.component.DoubleDirPathMap;
import subway.component.EdgeContainer;
import subway.component.NodeCountMap;
import subway.tool.VersionMark;

public class MyRailwaySystem implements RailwaySystem {
    // private HashMap<Integer, Path> doubleDirMap;
    private DoubleDirPathMap doubleDirMap;
    private NodeCountMap nodeCountMap;
    private EdgeContainer edgeContainer;
    private ShortestRoad shortestRoad;
    private int idCnt;
    private VersionMark versionMark;

    public MyRailwaySystem() {
        idCnt = 0;
        doubleDirMap = new DoubleDirPathMap();
        nodeCountMap = new NodeCountMap();
        versionMark = new VersionMark();
        // Graph Theory Part
        edgeContainer = new EdgeContainer();
        shortestRoad = new ShortestRoad(edgeContainer,
            nodeCountMap, versionMark);
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
        return edgeContainer.containsEdge(i, i1);
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
                return shortestRoad.isNodesConnected(i, i1);
            }
        }
    }

    @Override
    public int getShortestPathLength(int i, int i1)
        throws NodeIdNotFoundException, NodeNotConnectedException {
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
                return shortestRoad.getShortestRoadLength(i, i1);
            }
        }
    }

    /* -------- New Graph Theory Method in HM 11 -------- */

    @Override
    public int getConnectedBlockCount() {
        return 0;
    }

    @Override
    public int getLeastTransferCount(int i, int i1) throws NodeIdNotFoundException, NodeNotConnectedException {
        return 0;
    }

    @Override
    public int getLeastTicketPrice(int i, int i1) throws NodeIdNotFoundException, NodeNotConnectedException {
        return 0;
    }

    @Override
    public int getLeastUnpleasantValue(int i, int i1) throws NodeIdNotFoundException, NodeNotConnectedException {
        return 0;
    }



    /* -------- Inner Maintain Method -------- */
    private void addOnePath(Path path, int pathId) {
        versionMark.versionUpdate();
        doubleDirMap.put(pathId, path);
        nodeCountMap.addOnePath(path);
        edgeContainer.addOnePath(path);
    }

    private void removeOnePath(Path path, int pathId) {
        versionMark.versionUpdate();
        doubleDirMap.remove(pathId, path);
        nodeCountMap.removeOnePath(path);
        edgeContainer.removeOnePath(path);
    }

}
