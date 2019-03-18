package method.string;

import method.ToString;
import node.Node;
import node.func.CosNode;
import node.func.FuncNode;
import node.func.PowerNode;
import node.func.SinNode;

import java.math.BigInteger;

public class FuncToString implements ToString {
    public String print(Node node){
        StringBuilder strBild = new StringBuilder();
        // special occasion
        if(node instanceof FuncNode){
            if(((FuncNode) node).getPower().equals(BigInteger.ZERO)){
                return "1";
            }else if(node.isZero()){
                return "0";
            }
        }
        // judge type: sin, cos, power
        if(node instanceof SinNode){
            strBild.append("sin(");
            if(((SinNode) node).isUnit())
                strBild.append("x)");
            else
                strBild.append(((SinNode) node).getSon().toString()+")");
        }else if(node instanceof CosNode){
            strBild.append("cos(");
            if(((CosNode) node).isUnit())
                strBild.append("x)");
            else
                strBild.append(((CosNode) node).getSon().toString()+")");
        }else if(node instanceof PowerNode){
            strBild.append("x");
        }
        // add power
        if(node instanceof FuncNode &&
            !((FuncNode) node).getPower().equals(BigInteger.ONE)){
            strBild.append("^"+((FuncNode) node).getPower().toString());
        }
        return strBild.toString();
    }
}
