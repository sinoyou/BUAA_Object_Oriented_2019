package method.derivate;

import method.Derivate;
import node.Node;
import node.operation.AddNode;
import node.operation.MulNode;

public class MulDerivate implements Derivate {
    @Override
    public Node getDerivate(Node node) {
        MulNode mulNode = (MulNode) node;
        Node left = mulNode.getLeft();
        Node right = mulNode.getRight();
        // (f(x)*g(x))' = f'(x)g(x) + f(x)g'(x)
        MulNode item1 = new MulNode(left.getDerivate(),right);
        MulNode item2 = new MulNode(left,right.getDerivate());
        AddNode mulDer = new AddNode(item1,item2);
        return mulDer;
    }
}
