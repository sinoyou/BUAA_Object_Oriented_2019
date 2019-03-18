package node.operation;

import method.Derivate;
import method.derivate.SubDerivate;
import node.Node;

public class SubNode extends OpNode {
    public SubNode(Node left, Node right) {
        super(left, right);
        if (left.isZero() && right.isZero()) {
            setZero(true);
        }
    }

    @Override
    public Node getDerivate() {
        Derivate derivate = new SubDerivate();
        return derivate.getDerivate(this);
    }
}
