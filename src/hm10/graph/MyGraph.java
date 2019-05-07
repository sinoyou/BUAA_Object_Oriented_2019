package hm10.graph;

import com.oocourse.specs2.models.Graph;
import com.oocourse.specs2.models.NodeIdNotFoundException;
import com.oocourse.specs2.models.NodeNotConnectedException;
import hm10.graph.old.MyPathContainer;

public class MyGraph extends MyPathContainer implements Graph {
    @Override
    public boolean containsNode(int i) {
        return false;
    }

    @Override
    public boolean containsEdge(int i, int i1) {
        return false;
    }

    @Override
    public boolean isConnected(int i, int i1) throws NodeIdNotFoundException {
        return false;
    }

    @Override
    public int getShortestPathLength(int i, int i1) throws NodeIdNotFoundException, NodeNotConnectedException {
        return 0;
    }
}
