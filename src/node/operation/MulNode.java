package node.operation;

import method.Derivate;
import method.derivate.MulDerivate;
import node.Node;

public class MulNode extends OpNode {
    public MulNode(Node left, Node right) {
        super(left, right);
        // const judge
        if (left.isConst() && right.isConst()) {
            setConst(true);
        }
        // one/zero judge
        if (left.isZero() || right.isZero()) {
            setZero(true);
        } else if (left.isOne() && right.isOne()) {
            setOne(true);
        }
    }

    @Override
    public Node getDerivate() {
        Derivate derivate = new MulDerivate();
        return derivate.getDerivate(this);
    }
}
