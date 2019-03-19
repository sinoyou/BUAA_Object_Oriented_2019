package node.func;

import method.Derivate;
import method.derivate.SinDerivate;
import node.Node;

import java.math.BigInteger;

public class SinNode extends FuncNode {
    public SinNode(Node node, BigInteger power) {
        super(node, power);
        if (node.isZero() && !power.equals(BigInteger.ZERO)) {
            setZero(true);
            setConst(true);
        }
    }

    public SinNode(BigInteger power) {
        super(power);
    }

    @Override
    public Node getDerivate() {
        Derivate derivate = new SinDerivate();
        return derivate.getDerivate(this);
    }

}
