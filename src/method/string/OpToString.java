package method.string;

import method.ToString;
import node.Node;
import node.operation.AddNode;
import node.operation.MulNode;
import node.operation.OpNode;
import node.operation.SubNode;


public class OpToString implements ToString {
    @Override
    public String print(Node node) {
        StringBuilder strBild = new StringBuilder();
        // 1: add left surround
        strBild.append("(");
        // 2: first node
        if(node instanceof OpNode){
            strBild.append(((OpNode) node).getLeft().toString());
        }
        // 3:operation
        if(node instanceof AddNode){
            strBild.append("+");
        }else if(node instanceof SubNode){
            strBild.append("-");
        }else if(node instanceof MulNode){
            strBild.append("*");
        }
        // 4:second node
        if(node instanceof OpNode){
            strBild.append(((OpNode) node).getRight().toString());
        }
        // 5: add right surround
        strBild.append(")");
        return strBild.toString();
    }
}
