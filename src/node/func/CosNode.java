package node.func;

import method.Derivate;
import method.derivate.CosDerivate;
import node.Node;

import java.math.BigInteger;

public class CosNode extends FuncNode {
    public CosNode(Node node, BigInteger power) {
        super(node, power);
        if (node.isZero()) {
            setOne(true);
        }
    }

    public CosNode(BigInteger power) {
        super(power);
    }

    @Override
    public Node getDerivate() {
        Derivate derivate = new CosDerivate();
        return derivate.getDerivate(this);
    }

}
