package node.operation;

import method.Derivate;
import method.derivate.SubDerivate;
import node.Node;

public class SubNode extends OpNode {
    public SubNode(Node left, Node right) {
        super(left, right);
        // const judge
        if (left.isConst() && right.isConst()) {
            setConst(true);
        }
        // one/zero judge
        if (left.isZero() && right.isZero()) {
            setZero(true);
        } else if (left.isOne() && right.isZero()) {
            setOne(true);
        }
    }

    @Override
    public Node getDerivate() {
        Derivate derivate = new SubDerivate();
        return derivate.getDerivate(this);
    }
}
