package poly;

import factor.CosFactor;
import factor.Factor;
import factor.PowerFactor;
import factor.SinFactor;
import item.Item;

import java.math.BigInteger;
import java.util.Iterator;

public class PolyTrigoSimple extends Poly {

    public PolyTrigoSimple(String str) {
        super(str);
        trigoSimpleBasic();
    }

    @Override
    public String toString() {
        trigoSimpleBasic();
        return super.toString();
    }

    /**
     * only applied for simple sin(x) cos(x) x
     * n * x^i *sin^(p+2)cos^(q) + m * x^i *sin^(p)cos^(q+2)
     * -> n * x^i * sin^p * cos^q   +  (m-n) * x^i * sin^(p) * cos^(q+2)
     */
    public void trigoSimpleBasic() {
        boolean flag = true;
        while (flag) {
            flag = false;
            Iterator<Item> iteA = itemList.iterator();
            Iterator<Item> iteB = itemList.iterator();
            for(Item itemA: itemList) {
                // Item itemA = iteA.next();
                for(Item itemB: itemList) {
                    // Item itemB = iteB.next();
                    if (similarJudge(itemA, itemB)) {
                        flag = true;
                        BigInteger pIndex = itemA.getIndex(new SinFactor("x"))
                            .min(itemB.getIndex(new SinFactor("x")));
                        BigInteger qIndex = itemA.getIndex(new CosFactor("x"))
                            .min(itemB.getIndex(new CosFactor("x")));
                        BigInteger powerIndex = itemA.getIndex(new PowerFactor("x"));
                        BigInteger nCoe = itemA.getCoe();
                        BigInteger mCoe = itemB.getCoe();
                        // n * x^i * sin^p * cos^q
                        Item newItemA = getSimpleItem(nCoe, pIndex, qIndex, powerIndex);
                        // (m-n) * x^i * sin^(p) * cos^(q+2)
                        Item newItemB = getSimpleItem(mCoe.subtract(nCoe),
                            pIndex, qIndex.add(new BigInteger("2")), powerIndex);
                        itemList.remove(itemA);
                        itemList.remove(itemB);
                        // iteA.remove();
                        // iteB.remove();
                        addItem(newItemA);
                        addItem(newItemB);
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
        }
    }

    public static boolean similarJudge(Item a, Item b) {
        if (!a.getIndex(new PowerFactor("x")).equals(
            b.getIndex(new PowerFactor("x")))) {
            return false;
        } else {
            BigInteger sinA = a.getIndex(new SinFactor("x"));
            BigInteger sinB = b.getIndex(new SinFactor("x"));
            BigInteger sinSub = sinA.subtract(sinB);

            BigInteger cosA = a.getIndex(new CosFactor("x"));
            BigInteger cosB = b.getIndex(new CosFactor("x"));
            BigInteger cosSub = cosA.subtract(cosB);

            // sinSub + cosSub == 0 && |sinSub|==|cosSub|==2
            return (sinSub.add(cosSub).equals(BigInteger.ZERO) &&
                sinSub.abs().equals(new BigInteger("2")));
        }
    }

    public static Item getSimpleItem(BigInteger coe, BigInteger sin,
                                     BigInteger cos, BigInteger power) {
        Item newItem = new Item();
        newItem.setCoe(coe);
        newItem.addFactor(new SinFactor("x"), sin);
        newItem.addFactor(new CosFactor("x"), cos);
        newItem.addFactor(new PowerFactor("x"), power);
        return newItem;
    }
}
