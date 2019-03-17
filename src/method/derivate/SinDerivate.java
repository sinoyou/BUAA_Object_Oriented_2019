package method.derivate;

import method.Derivate;
import node.ConstNode;
import node.Node;
import node.func.CosNode;
import node.func.SinNode;
import node.operation.MulNode;

import java.math.BigInteger;

public class SinDerivate implements Derivate {
    @Override
    public Node getDerivate(Node node) {
        SinNode sinNode = (SinNode) node;
        Node inner = sinNode.getSon();
        BigInteger power = sinNode.getPower();
        // special occasion: power is 1
        if(power.equals(BigInteger.ZERO)){
            return new ConstNode(BigInteger.ZERO);
        }
        else{
            // part1: m*sin(factor)^m-1
            SinNode temp1 = new SinNode(inner,power.subtract(BigInteger.ONE));
            ConstNode temp2 = new ConstNode(power);
            MulNode mul1 = new MulNode(temp1,temp2);
            // part2: cos(factor)*(factor)'
            CosNode temp3 = new CosNode(inner,BigInteger.ONE);
            Node temp4 = inner.getDerivate();

            MulNode mul2 = new MulNode(temp3,temp4);
            MulNode sinDer = new MulNode(mul1,mul2);
            return sinDer;
        }
    }
}
