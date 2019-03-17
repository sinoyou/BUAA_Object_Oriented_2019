package method.derivate;

import method.Derivate;
import node.Node;
import node.operation.AddNode;
import node.operation.OpNode;

public class AddDerivate implements Derivate {
    @Override
    public Node getDerivate(Node node) {
        OpNode opNode = (OpNode)node;
        Node left = opNode.getLeft();
        Node right = opNode.getRight();
        return new AddNode(left.getDerivate(),right.getDerivate());
    }
}
