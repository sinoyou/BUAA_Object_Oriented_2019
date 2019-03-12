package poly;

import factor.CosFactor;
import factor.PowerFactor;
import factor.SinFactor;
import item.Item;

import java.math.BigInteger;
import java.util.ArrayList;

public class PolyTrigoSimple extends Poly {

    public PolyTrigoSimple(String str) {
        super(str);
        trigoSimple();
    }

    @Override
    public String toString() {
        trigoSimple();
        return super.toString();
    }

    /**
     * only applied for simple sin(x) cos(x) x
     * n * x^i *sin^(p+2)cos^(q) + m * x^i *sin^(p)cos^(q+2)
     * -> n * x^i * sin^p * cos^q   +  (m-n) * x^i * sin^(p) * cos^(q+2)
     */
    public void trigoSimple() {
        boolean flag = true;
        while (flag) {
            flag =  trigoSimplify();
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
                sinSub.equals(new BigInteger("2")));
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

    private boolean trigoSimplify() {
        ArrayList<Item> itemList = super.getItemList();
        for (Item itemA : itemList) {
            for (Item itemB : itemList) {
                // index of itemA's sin(x) must be the bigger one, or it can't
                // work well.
                if (similarJudge(itemA, itemB)) {
                    BigInteger indexP = itemA.getIndex(new SinFactor("x"))
                        .min(itemB.getIndex(new SinFactor("x")));
                    BigInteger indexQ = itemA.getIndex(new CosFactor("x"))
                        .min(itemB.getIndex(new CosFactor("x")));
                    BigInteger powerIndex = itemA.getIndex(
                        new PowerFactor("x"));
                    BigInteger nnCoe = itemA.getCoe();
                    BigInteger mmCoe = itemB.getCoe();
                    itemList.remove(itemA);
                    itemList.remove(itemB);
                    // n * x^i * sin^p * cos^q
                    Item newItemA = getSimpleItem(
                        nnCoe, indexP, indexQ, powerIndex);
                    // (m-n) * x^i * sin^(p) * cos^(q+2)
                    Item newItemB = getSimpleItem(mmCoe.subtract(nnCoe),
                        indexP, indexQ.add(new BigInteger("2")),
                        powerIndex);
                    addItem(newItemA);
                    addItem(newItemB);
                    return true;
                }
            }
        }
        return false;
    }
}
