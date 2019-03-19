package method.derivate;

import method.Derivate;
import node.ConstNode;
import node.Node;
import node.operation.AddNode;
import node.operation.OpNode;

import java.math.BigInteger;

public class AddDerivate implements Derivate {
    @Override
    public Node getDerivate(Node node) {
        if (node.isConst()) {
            return new ConstNode(BigInteger.ZERO);
        } else {
            OpNode opNode = (OpNode) node;
            Node left = opNode.getLeft();
            Node right = opNode.getRight();
            return new AddNode(left.getDerivate(), right.getDerivate());
        }
    }
}
