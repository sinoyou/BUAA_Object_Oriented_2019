package node.operation;

import method.Derivate;
import method.derivate.MulDerivate;
import node.Node;

public class MulNode extends OpNode {
    public MulNode(Node left, Node right) {
        super(left, right);
        if (left.isZero() || right.isZero()) {
            setZero(true);
        }
    }

    @Override
    public Node getDerivate() {
        Derivate derivate = new MulDerivate();
        return derivate.getDerivate(this);
    }
}
