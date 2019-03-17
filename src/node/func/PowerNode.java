package node.func;

import method.Derivate;
import method.derivate.PowerDerivate;
import node.Node;

import java.math.BigInteger;

public class PowerNode extends FuncNode {

    public PowerNode(Node node, BigInteger power){
        super(node,power);
    }

    public PowerNode(BigInteger power){
        super(power);
    }

    @Override
    public Node getDerivate() {
        Derivate derivate = new PowerDerivate();
        return derivate.getDerivate(this);
    }
}
