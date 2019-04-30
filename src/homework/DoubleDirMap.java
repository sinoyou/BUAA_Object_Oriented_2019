package homework;

import com.oocourse.specs1.models.Path;

import java.util.HashMap;

public class DoubleDirMap {
    private HashMap<Integer, Path> id2Path;
    private HashMap<Path, Integer> path2Id;

    public DoubleDirMap() {
        id2Path = new HashMap<>(64, (float) 0.75);
        path2Id = new HashMap<>(64, (float) 0.75);
    }

    /* ---------- None Pure Method ---------- */
    public int put(Integer i, Path p) {
        if (i == null || p == null) {
            return -1;
        } else {
            id2Path.put(i, p);
            path2Id.put(p, i);
            return 0;
        }
    }

    public int remove(Integer i, Path p) {
        if (i == null || p == null) {
            return -1;
        } else {
            boolean b1 = id2Path.remove(i, p);
            boolean b2 = path2Id.remove(p, i);
            return 0;
        }
    }

    /* ---------- Pure Method ---------- */
    public int size() {
        assert (id2Path.size() == path2Id.size());
        return id2Path.size();
    }

    public boolean containsId(int id) {
        assert (path2Id.containsValue(id) == id2Path.containsKey(id));
        return id2Path.containsKey(id);
    }

    public boolean containsPath(Path path) {
        if (path == null) {
            return false;
        } else {
            assert (path2Id.containsKey(path) && id2Path.containsValue(path));
            return path2Id.containsKey(path);
        }
    }

    public Path getPathById(int id) {
        assert (containsId(id));
        return id2Path.get(id);
    }

    public int getIdByPath(Path path) {
        assert (containsPath(path));
        return path2Id.get(path);
    }

}
