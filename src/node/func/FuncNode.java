package node.func;

import method.ToString;
import method.string.FuncToString;
import node.Node;

import java.math.BigInteger;

public abstract class FuncNode extends Node {
    private BigInteger power;
    private Node son;
    private boolean unit;

    FuncNode(Node node, BigInteger power) {
        super();
        this.son = node;
        this.power = power;
        this.unit = false;
    }

    FuncNode(BigInteger power) {
        this.son = null;
        this.power = power;
        this.unit = true;
    }

    public Node getSon() {
        return son;
    }

    public BigInteger getPower() {
        return power;
    }

    public boolean isUnit() {
        return unit;
    }

    @Override
    public String toString() {
        ToString str = new FuncToString();
        return str.print(this);
    }

}
