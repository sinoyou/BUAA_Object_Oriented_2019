package node;

import java.math.BigInteger;

public class ConstNode extends Node {
    private BigInteger value;

    public ConstNode(BigInteger value) {
        super();
        this.value = value;
        if (value.equals(BigInteger.ZERO)) {
            setZero(true);
        } else if (value.equals(BigInteger.ONE)) {
            setOne(true);
        }
    }

    @Override
    public Node getDerivate() {
        return new ConstNode(BigInteger.ZERO);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
