package node;

public abstract class Node {
    // each node has only one father, but number of son depends.
    private Node father;
    private boolean zero;

    public Node() {
        father = null;
        zero = false;
    }

    public void setFather(Node father) {
        this.father = father;
    }

    public Node getFather() {
        return father;
    }

    public boolean isZero() {
        return zero;
    }

    public void setZero(boolean zero) {
        this.zero = zero;
    }

    public abstract Node getDerivate();

    public abstract String toString();
}
