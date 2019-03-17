package method.derivate;

import method.Derivate;
import node.ConstNode;
import node.Node;
import node.func.CosNode;
import node.func.SinNode;
import node.operation.MulNode;

import java.math.BigInteger;

public class CosDerivate implements Derivate {
    @Override
    public Node getDerivate(Node node) {
        CosNode cosNode = (CosNode) node;
        Node inner = cosNode.getSon();
        BigInteger power = cosNode.getPower();

        // special occasion: power is 0
        if(power.equals(BigInteger.ZERO)){
            return new ConstNode(BigInteger.ZERO);
        }else {
            // part1: m*cos(factor)^(m-1)
            CosNode temp1 = new CosNode(inner,power.subtract(BigInteger.ONE));
            ConstNode temp2 = new ConstNode(power);
            MulNode mul1 = new MulNode(temp1,temp2);
            // part2: sin(factor)*(factor)'
            SinNode temp3 = new SinNode(inner,BigInteger.ONE);
            Node temp4 = inner.getDerivate();
            MulNode mul2 = new MulNode(temp3,temp4);

            BigInteger negOne = new BigInteger("-1");
            MulNode cosDer = new MulNode(new ConstNode(negOne),new MulNode(mul1,mul2));
            return cosDer;
        }
    }
}
