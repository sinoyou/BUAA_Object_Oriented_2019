package graph.component;

import com.oocourse.specs2.models.Path;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class MyPath implements Path {

    // private final int[] nodes;
    private Integer distinctCount;
    private Integer hashSave;
    private ArrayList<Integer> nodes;
    private HashSet<Integer> nodeSet;

    public MyPath(int[] array) {
        // nodes = array;
        distinctCount = null;
        nodes = new ArrayList<>(array.length);
        nodeSet = new HashSet<>();
        for (int i = 0; i < array.length; i++) {
            nodes.add(i, array[i]);
            nodeSet.add(array[i]);
        }
        hashSave = nodes.hashCode();
    }

    @Override
    public int size() {
        // return nodes.length;
        return nodes.size();
    }

    @Override
    public int getNode(int index) {
        // return nodes[index];
        return nodes.get(index);
    }

    @Override
    public boolean containsNode(int node) {
        return nodeSet.contains(node);
    }

    @Override
    public int getDistinctNodeCount() {
        if (distinctCount != null) {
            return distinctCount;
        } else {
            distinctCount = nodeSet.size();
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
            hashSave = nodes.hashCode();
            return hashSave;
        }
    }

    @Override
    public boolean isValid() {
        return (this.size() >= 2);
    }

    @Override
    public Iterator<Integer> iterator() {
        return nodes.iterator();
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
        return Integer.compare(size1,size2);
    }

    public int getHead() {
        return nodes.get(0);
    }

    public int getTail() {
        return nodes.get(nodes.size() - 1);
    }
}
