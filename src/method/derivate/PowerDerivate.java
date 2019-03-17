package method.derivate;

import method.Derivate;
import node.ConstNode;
import node.Node;
import node.func.PowerNode;
import node.operation.MulNode;

import java.math.BigInteger;

public class PowerDerivate implements Derivate {
    @Override
    public Node getDerivate(Node node) {
        PowerNode powerNode = (PowerNode) node;
        BigInteger power = powerNode.getPower();
        // special occasion: power is 0
        if(power.equals(BigInteger.ZERO)){
            return new ConstNode(BigInteger.ZERO);
        }
        // special occasion: power is 1
        else if(power.equals(BigInteger.ONE)){
            return new ConstNode(BigInteger.ONE);
        }
        // m*x^m-1
        else {
            PowerNode temp1 = new PowerNode(power.subtract(BigInteger.ONE));
            ConstNode temp2 = new ConstNode(power);
            MulNode powerDer = new MulNode(temp1,temp2);
            return powerDer;
        }
    }
}
