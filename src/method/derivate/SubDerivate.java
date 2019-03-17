package method.derivate;

import method.Derivate;
import node.Node;
import node.operation.SubNode;

public class SubDerivate implements Derivate {
    @Override
    public Node getDerivate(Node node) {
        SubNode subNode = (SubNode) node;
        Node left = subNode.getLeft();
        Node right = subNode.getRight();
        SubNode subDer = new SubNode(left.getDerivate(),right.getDerivate());
        return subDer;
    }
}
