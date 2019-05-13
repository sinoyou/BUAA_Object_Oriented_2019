package subway.component.link;

import java.util.HashMap;
import java.util.Iterator;

public class Link {
    // Used for count the number of pathId between <nodeA, nodeB>
    private HashMap<Integer, Integer> pathCnt;

    public Link() {
        pathCnt = new HashMap<>();
    }

    public void addPathId(int pathId) {
        if (!pathCnt.containsKey(pathId)) {
            pathCnt.put(pathId, 1);
        } else {
            int cnt = pathCnt.get(pathId);
            pathCnt.replace(pathId, cnt + 1);
        }
    }

    public void subPathId(int pathId) {
        assert pathCnt.containsKey(pathId);
        if (pathCnt.containsKey(pathId)) {
            int cnt = pathCnt.get(pathId);
            if (cnt == 1) {
                pathCnt.remove(pathId, cnt);
            } else {
                pathCnt.replace(pathId, cnt - 1);
            }
        }
    }

    public boolean isEmpty(){
        return pathCnt.isEmpty();
    }

    public Iterator<Integer> getPathIdOfLink(){
        return pathCnt.keySet().iterator();
    }

    public boolean containEdgeOnPath(int pathId){
        return pathCnt.containsKey(pathId);
    }
}
