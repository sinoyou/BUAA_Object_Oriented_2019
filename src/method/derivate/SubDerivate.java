package method.derivate;

import method.Derivate;
import node.ConstNode;
import node.Node;
import node.operation.SubNode;

import java.math.BigInteger;

public class SubDerivate implements Derivate {
    @Override
    public Node getDerivate(Node node) {
        if (node.isConst()) {
            return new ConstNode(BigInteger.ZERO);
        } else {
            SubNode subNode = (SubNode) node;
            Node left = subNode.getLeft();
            Node right = subNode.getRight();
            SubNode subDer = new SubNode(left.getDerivate(),
                right.getDerivate());
            return subDer;
        }
    }
}
