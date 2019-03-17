package node.operation;

import method.ToString;
import method.string.OpToString;
import node.Node;

public abstract class OpNode extends Node {
    private Node left;
    private Node right;
    OpNode(Node left,Node right){
        super();
        this.left = left;
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    @Override
    public String toString() {
        ToString str = new OpToString();
        return str.print(this);
    }
}
