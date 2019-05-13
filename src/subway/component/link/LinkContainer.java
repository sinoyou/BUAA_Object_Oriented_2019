package subway.component.link;

import com.oocourse.specs3.models.Path;
import subway.tool.Constant;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class LinkContainer {
    /**
     * 1.edgeMap: Use double-layer HashMap to record link detail information.
     * edgeMap[i][j] contains pathId which has edge <i,j>
     * TIPS:
     * -Link describe the status of two nodes, in Link Object, there can be many
     * specific info about this link status. such as edges number, pathId, etc.
     * -When edge[i][j].size() = 0, <j, count> as a value will be removed.
     * -When edge[i].size() = 0, <i, hashMap> as a value will be removed.
     */
    private HashMap<Integer, HashMap<Integer, Link>> edgeMap;

    public LinkContainer() {
        edgeMap = new HashMap<>(Constant.maxGraphDistinctNode);
    }

    public void addOnePath(Path path, int pathId) {
        assert (path != null && path.isValid());

        int length = path.size();
        for (int i = 0; i <= length - 2; i++) {
            int cur = path.getNode(i);
            int next = path.getNode(i + 1);
            addOneEdge(cur, next, pathId);
            addOneEdge(next, cur, pathId);
        }
    }

    /**
     * Require: Path must have been added to edgeMap.
     * !!! Run this method without requirement will lead unpredictable error.
     * 1. Remove edges of path in edgeMap.
     * 2. Update version mark.
     */
    public void removeOnePath(Path path, int pathId) {
        assert (path != null && path.isValid());

        int length = path.size();
        for (int i = 0; i <= length - 2; i++) {
            int cur = path.getNode(i);
            int next = path.getNode(i + 1);
            removeOneEdge(cur, next, pathId);
            removeOneEdge(next, cur, pathId);
        }
    }

    public Iterator<Integer> getConnectNodes(int node) {
        assert edgeMap.containsKey(node);
        Set<Integer> set = edgeMap.get(node).keySet();
        assert !set.isEmpty();
        return set.iterator();
    }

    public boolean containsEdge(int from, int to) {
        if (edgeMap.containsKey(from)) {
            return edgeMap.get(from).containsKey(to);
        } else {
            return false;
        }
    }

    public boolean containsKey(int node) {
        return edgeMap.containsKey(node);
    }

    /**
     * Check if link <from,to> has one edge belong to pathId.
     */
    public boolean hasEdgeOnPath(int from, int to, int pathId) {
        if (containsEdge(from, to)) {
            return edgeMap.get(from).get(to).containEdgeOnPath(pathId);
        }
        return false;
    }

    /* -------- Inner Maintain Function --------*/

    /**
     * 1. get from's hashMap. If not exists, create one.
     * 2. add or update to's value in from's hashMap.
     */
    private void addOneEdge(int from, int to, int pathId) {
        if (!edgeMap.containsKey(from)) {
            edgeMap.put(from, new HashMap<>(Constant.maxGraphDistinctNode));
        }
        HashMap<Integer, Link> map = edgeMap.get(from);

        if (!map.containsKey(to)) {
            Link link = new Link();
            link.addPathId(pathId);

            map.put(to, link);
        } else {
            Link link = map.get(to);
            link.addPathId(pathId);

            map.replace(to, link);
        }
    }

    /**
     * 1. Get from's hashMap.(hashMap must exists)
     * 2. remove or update to's value in from's hashMap.(hashMap must exists)
     * 3. If from's hashMap is empty, remove it in edgeMap.
     */
    private void removeOneEdge(int from, int to, int pathId) {
        assert edgeMap.containsKey(from);
        if (edgeMap.containsKey(from)) {
            HashMap<Integer, Link> map = edgeMap.get(from);
            assert map.containsKey(to);
            if (map.containsKey(to)) {

                Link link = map.get(to);
                link.subPathId(pathId);

                if (link.isEmpty()) {
                    map.remove(to, link);
                } else {
                    map.replace(to, link);
                }

                if (map.isEmpty()) {
                    edgeMap.remove(from, map);
                }
            }
        }
    }
}
