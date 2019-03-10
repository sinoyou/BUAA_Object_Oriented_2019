package method;

import Item.Item;
import factor.*;

import java.math.BigInteger;
import java.util.HashMap;

public class DerivateMethod {

    // 将一个非常数因子的求导，返回一个Item型的序列(目前不考虑裂解为两项)。
    // 常数+幂函数+内函数求导
    public static Item factorDer(Factor factor, BigInteger index){
        // initial return value
        Item derItem = new Item();

        // special occasion: factor is a const
        if(factor.getClass()==ConstFactor.class){
            derItem.factorAdd(new ConstFactor("0"),Factor.getIndex("0"));
        }
        else{
            // 1.constant part
            ConstFactor partConst = new ConstFactor(index);
            derItem.factorAdd(partConst,BigInteger.ONE);
            // 2.power part
            derItem.factorAdd(factor,index.subtract(BigInteger.ONE));
            // 3.inner function part: power and constant factor's inner derivation
            // is ignored.
            if(factor.getClass()==SinFactor.class){
                derItem.factorAdd(new CosFactor("x"),BigInteger.ONE);
            }else if(factor.getClass()==CosFactor.class){
                // (cosx)' = -1 * sinx
                derItem.factorAdd(new SinFactor("x"),BigInteger.ONE);
                derItem.factorAdd(new ConstFactor("-1"),Factor.getIndex("-1"));
            }
        }
        // try
        HashMap<Factor,BigInteger> map = derItem.getFactorMap();
        for(Factor key:map.keySet()){
            System.out.println(key+" "+map.get(key));
        }
        return derItem;
    }

}
