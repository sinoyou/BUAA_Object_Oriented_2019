package method;

import item.Item;
import factor.CosFactor;
import factor.Factor;
import factor.SinFactor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Set;

public class DerivateMethod {

    // 将一个非常数因子的求导，返回一个Item型的序列(目前不考虑裂解为两项)。
    // 常数+幂函数+内函数求导
    private static Item factorDer(Factor factor, BigInteger index) {
        // initial return value
        Item derItem = new Item();

        // 1.constant part
        derItem.setCoe(derItem.getCoe().multiply(index));
        // 2.power part
        derItem.addFactor(factor, index.subtract(BigInteger.ONE));
        // 3.inner function part: power and constant factor's inner derivation
        // is ignored.
        if (factor.getClass() == SinFactor.class) {
            derItem.addFactor(new CosFactor("x"), BigInteger.ONE);
        } else if (factor.getClass() == CosFactor.class) {
            // (cosx)' = -1 * sinx
            derItem.addFactor(new SinFactor("x"), BigInteger.ONE);
            derItem.setCoe(derItem.getCoe().multiply(new BigInteger("-1")));
        }
        return derItem;
    }

    /**
     * return derivation of item, if item is shaped like coe*f(x)*g(x)*h(x)
     * the number of arrayList will be 3.
     *
     * @param item expression split by +/-.
     * @return item list.
     */
    public static ArrayList<Item> itemDer(Item item) {
        ArrayList<Item> itemList = new ArrayList<>();

        // get factor set and do derivation to each factor
        // HashMap<Factor,BigInteger> factorMap = item.getFactorMap();
        Set<Factor> factorSet = item.getMapSet();

        for (Factor i : factorSet) {
            // create a deep clone of hashMap
            Item oriItem = (Item) item.clone();
            oriItem.removeFactor(i);
            // derivation present factor
            Item newItem = factorDer(i, item.getIndex(i));
            // multiply two item
            itemList.add(newItem.itemMultiply(oriItem));
        }

        return itemList;
    }

}
