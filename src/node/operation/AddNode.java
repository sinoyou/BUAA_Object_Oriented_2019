package node.operation;

import method.Derivate;
import method.derivate.AddDerivate;
import node.Node;

public class AddNode extends OpNode {
    public AddNode(Node left, Node right) {
        super(left, right);
        // const judge
        if (left.isConst() && right.isConst()) {
            setConst(true);
        }
        // one/zero judge
        if (left.isZero() && right.isZero()) {
            setZero(true);
        } else if (left.isZero() && right.isOne()) {
            setOne(true);
        } else if (left.isOne() && right.isZero()) {
            setOne(true);
        }
    }

    @Override
    public Node getDerivate() {
        Derivate derivate = new AddDerivate();
        return derivate.getDerivate(this);
    }
}
