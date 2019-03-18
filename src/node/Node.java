package node;

public abstract class Node {
    // each node has only one father, but number of son depends.
    private Node father;
    private boolean zero;
    private boolean one;

    public Node() {
        father = null;
        zero = false;
        one = false;
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

    public boolean isOne() {
        return one;
    }

    public void setZero(boolean zero) {
        this.zero = zero;
    }

    public void setOne(boolean one) {
        this.one = one;
    }

    public abstract Node getDerivate();

    public abstract String toString();
}
