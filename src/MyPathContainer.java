import com.oocourse.specs1.models.Path;
import com.oocourse.specs1.models.PathContainer;
import com.oocourse.specs1.models.PathIdNotFoundException;
import com.oocourse.specs1.models.PathNotFoundException;

import java.util.HashMap;
import java.util.Iterator;

public class MyPathContainer implements PathContainer {
    private HashMap<Integer, Path> map;
    private HashMap<Integer, Integer> countMap;
    private int idCnt;

    public MyPathContainer() {
        map = new HashMap<>();
        idCnt = 0;
        countMap = new HashMap<>();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean containsPath(Path path) {
        return map.containsValue(path);
    }

    @Override
    public boolean containsPathId(int pathId) {
        return map.containsKey(pathId);
    }

    @Override
    public Path getPathById(int pathId) throws PathIdNotFoundException {
        if (containsPathId(pathId)) {
            return map.get(pathId);
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
            Iterator it = map.keySet().iterator();
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                if (map.get(num).equals(path)) {
                    return num;
                }
            }
            throw new PathNotFoundException(path);
        }
    }

    @Override
    public int addPath(Path path) throws PathNotFoundException {
        if (path != null && path.isValid()) {
            if (!containsPath(path)) {
                idCnt++;
                map.put(idCnt, path);
                countAdd(path);
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
            countSub(path);
            map.remove(pathId, path);
            return pathId;
        }
    }

    @Override
    public void removePathById(int pathId) throws PathIdNotFoundException {
        if (!containsPathId(pathId)) {
            throw new PathIdNotFoundException(pathId);
        } else {
            countSub(map.get(pathId));
            map.remove(pathId);
        }
    }

    @Override
    public int getDistinctNodeCount() {
        return countMap.size();
    }

    private void countAdd(Path path) {
        Iterator<Integer> it = path.iterator();
        while (it.hasNext()) {
            int num = it.next();
            int cnt;
            if (countMap.containsKey(num)) {
                cnt = countMap.get(num);
                countMap.replace(num, cnt + 1);
            } else {
                cnt = 1;
                countMap.put(num, cnt);
            }
        }
    }

    private void countSub(Path path) {
        Iterator<Integer> it = path.iterator();
        while (it.hasNext()) {
            int num = it.next();
            int cnt;
            assert (countMap.containsKey(num));
            cnt = countMap.get(num);
            if (cnt == 1) {
                countMap.remove(num);
            } else {
                countMap.replace(num, cnt - 1);
            }
        }
    }
}
