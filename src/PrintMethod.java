import Item.Item;
import factor.CosFactor;
import factor.Factor;
import factor.PowerFactor;
import factor.SinFactor;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.Set;
//todo
// 将print方法移植到item和poly类中。
public class PrintMethod {
    public static String printExpression(Poly expr){
        StringBuilder str= new StringBuilder();
        Iterator<Item> itr= expr.getItr();
        // empty
        if(!itr.hasNext()){
            str.append("0");
        }
        // not empty
        else{
            while(itr.hasNext()){
                Item i = itr.next();
                String itemStr = printItem(i);
                if(!itemStr.isEmpty()){
                    str.append("+");
                    str.append(itemStr);
                }

            }
        }
        return str.toString();
    }

    private static String printItem(Item item){
        StringBuilder str = new StringBuilder();
        Set<Factor> set = item.getMapSet();
        // coefficient is zero.
        if(item.getCoe().equals(BigInteger.ZERO)){
            str.append("0");
            return str.toString();
        }
        // item is only a number.
        if(set.size()==0){
            str.append(item.getCoe());
        }
        // item is with function(index is non-zero)
        else {
            // coefficient
            if(item.getCoe().equals(BigInteger.ONE))
                str.append("+");
            else if(item.getCoe().equals(new BigInteger("-1")))
                str.append("-");
            else
                str.append(item.getCoe());
            for(Factor factor:set){
                str.append("*");
                if(factor.getClass()== PowerFactor.class){
                    str.append(factor.getBase());
                }else if(factor.getClass()== SinFactor.class){
                    str.append("sin("+factor.getBase()+")");
                }else if(factor.getClass()== CosFactor.class){
                    str.append("cos("+factor.getBase()+")");
                }
                // add index
                if(!item.getIndex(factor).equals(BigInteger.ONE)){
                    str.append("^");
                    str.append(item.getIndex(factor));
                }
            }
        }
        return str.toString();
    }

    private static void trigoSimplify(Poly expr){

    }
}
