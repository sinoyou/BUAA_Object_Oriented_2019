
import com.oocourse.specs1.models.Path;

import java.util.HashSet;
import java.util.Iterator;

public class MyPath implements Path {

    final private int[] nodes;
    private Integer distinctCount;
    private Integer hashSave;

    public MyPath(int[] array) {
        nodes = array;
        distinctCount = null;
        hashSave = null;
    }

    @Override
    public int size() {
        return nodes.length;
    }

    @Override
    public int getNode(int index) {
        return nodes[index];
    }

    @Override
    public boolean containsNode(int node) {
        for (int i = 0; i < this.size(); i++) {
            if (this.getNode(i) == node) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getDistinctNodeCount() {
        if (distinctCount != null) {
            return distinctCount;
        } else {
            HashSet<Integer> set = new HashSet<>();
            for (int i = 0; i < this.size(); i++) {
                if (!set.contains(this.getNode(i))) {
                    set.add(this.getNode(i));
                }
            }
            distinctCount = set.size();
            return distinctCount;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Path && obj.hashCode() == this.hashCode()) {
            Path path = (Path) obj;
            if (path.size() == this.size() && this.compareTo(path) == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        if (hashSave != null) {
            return hashSave;
        } else {
            Iterator it = this.iterator();
            int hash = 0;
            while (it.hasNext()) {
                int num = (int) it.next();
                hash += num;
            }
            hashSave = hash;
            return hash;
        }
    }

    @Override
    public boolean isValid() {
        return (this.size() >= 2);
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private int cursor = -1;

            @Override
            public boolean hasNext() {
                return (cursor + 1 < size());
            }

            @Override
            public Integer next() {
                cursor++;
                return getNode(cursor);
            }
        };
    }

    @Override
    public int compareTo(Path o) {
        int size1 = this.size();
        int size2 = o.size();
        for (int i = 0; i < size1 && i < size2; i++) {
            if (this.getNode(i) < o.getNode(i)) {
                return -1;
            } else if (this.getNode(i) > o.getNode(i)) {
                return 1;
            }
        }
        // non public
        if (size1 < size2) {
            return -1;
        } else if (size1 > size2) {
            return 1;
        } else {
            return 0;
        }
    }
}
