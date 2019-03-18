package node.operation;

import method.Derivate;
import method.derivate.AddDerivate;
import node.Node;

public class AddNode extends OpNode {
    public AddNode(Node left, Node right) {
        super(left, right);
        if (left.isZero() && right.isZero()) {
            setZero(true);
        }
    }

    @Override
    public Node getDerivate() {
        Derivate derivate = new AddDerivate();
        return derivate.getDerivate(this);
    }
}
