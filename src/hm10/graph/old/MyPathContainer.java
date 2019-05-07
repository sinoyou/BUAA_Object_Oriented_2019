package hm10.graph.old;

import com.oocourse.specs2.models.*;
import hm10.graph.component.DoubleDirPathMay;

import java.util.HashMap;
import java.util.Iterator;

public class MyPathContainer implements PathContainer {
    // private HashMap<Integer, Path> map;
    private DoubleDirPathMay map;
    private HashMap<Integer, Integer> countMap;
    private int idCnt;

    public MyPathContainer() {
        map = new DoubleDirPathMay();
        idCnt = 0;
        countMap = new HashMap<>(64, (float) 0.75);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean containsPath(Path path) {
        return map.containsPath(path);
    }

    @Override
    public boolean containsPathId(int pathId) {
        return map.containsId(pathId);
    }

    @Override
    public Path getPathById(int pathId) throws PathIdNotFoundException {
        if (containsPathId(pathId)) {
            return map.getPathById(pathId);
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
            return map.getIdByPath(path);
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
            Path p = map.getPathById(pathId);
            countSub(map.getPathById(pathId));
            map.remove(pathId, p);
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
