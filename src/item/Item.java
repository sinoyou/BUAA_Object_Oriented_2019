package item;

import constant.RegexConst;
import factor.CosFactor;
import factor.Factor;
import factor.PowerFactor;
import factor.SinFactor;
import sun.plugin.viewer.context.IExplorerAppletContext;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Item implements Cloneable {
    // define a map to store all kinds of basic factors(except const)
    // and its index.basic factors definition: function(const)
    // factor with index = 1.
    private HashMap<Factor, BigInteger> factorMap = new HashMap<>();
    private BigInteger coe = BigInteger.ONE;

    public Item() {

    }

    public Item(String expr) {
        String temp = expr;
        // special occasion, short +/- at first
        if (temp.charAt(0) == '+') {
            temp = temp.substring(1);
        } else if (temp.charAt(0) == '-') {
            coe = new BigInteger("-1");
            temp = temp.substring(1);
        }

        // split into factors by *
        String[] factorList = temp.split("\\*");
        for (String factorStr : factorList) {
            factorStrAdd(factorStr);
        }
    }

    public void addFactor(Factor factor, BigInteger index) {
        factorMerge(factor, index);
    }

    public Set<Factor> getMapSet() {
        return factorMap.keySet();
    }

    public BigInteger getIndex(Factor i) {
        return factorMap.getOrDefault(i, null);
    }

    public BigInteger getCoe() {
        return coe;
    }

    public void setCoe(BigInteger coe) {
        this.coe = coe;
    }

    public void removeFactor(Factor i) {
        this.factorMap.remove(i);
    }

    public BigInteger getMapIndex(Factor i) {
        return this.factorMap.getOrDefault(i, null);
    }

    /*
    @Override
    public Object clone() {
        Item item = new Item();
        item.coe = this.coe;
        for (Factor i : this.factorMap.keySet()) {
            item.factorMap.put((Factor) i.clone(), this.factorMap.get(i));
        }
        return item;
    }
    */

    public static Item itemMultiply(Item a, Item b) {
        Item newItem = new Item();
        newItem.coe = b.coe.multiply(a.coe);
        for (Factor i : a.factorMap.keySet()) {
            newItem.addFactor(i, a.factorMap.get(i));
        }
        for (Factor i : b.factorMap.keySet()) {
            newItem.addFactor(i, b.factorMap.get(i));
        }
        return newItem;
    }


    /**
     * check if temp's factors(with index) are included in this.
     * if a include b && b include a -> a = b;
     *
     * @param temp
     * @return
     */
    public boolean include(Item temp) {
        for (Factor i : temp.factorMap.keySet()) {
            if (!(this.factorMap.containsKey(i)
                && temp.factorMap.get(i).equals(factorMap.get(i)))) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        Set<Factor> set = this.getMapSet();
        // coefficient is zero.
        if (this.getCoe().equals(BigInteger.ZERO)) {
            str.append("0");
            return str.toString();
        }
        // item is only a number.
        if (set.size() == 0) {
            str.append(this.getCoe());
        }
        // item is with function(index is non-zero)
        else {
            // define flag for skip * output when the coefficient is 1/-1.
            boolean flag = false;
            // coefficient
            if (this.getCoe().equals(BigInteger.ONE)) {
                flag = true;
            } else if (this.getCoe().equals(new BigInteger("-1"))) {
                str.append("-");
                flag = true;
            } else {
                str.append(this.getCoe());
            }
            for (Factor factor : set) {
                if (!flag) {
                    str.append("*");
                } else {
                    flag = false;
                }
                if (factor.getClass() == PowerFactor.class) {
                    str.append(factor.getBase());
                } else if (factor.getClass() == SinFactor.class) {
                    str.append("sin(" + factor.getBase() + ")");
                } else if (factor.getClass() == CosFactor.class) {
                    str.append("cos(" + factor.getBase() + ")");
                }
                // add index
                if (!this.getIndex(factor).equals(BigInteger.ONE)) {
                    str.append("^");
                    str.append(this.getIndex(factor));
                }
            }
        }
        return str.toString();
    }

    private void factorMerge(Factor temp, BigInteger index) {
        // merge into present map, add or update
        if (factorMap.containsKey(temp)) {
            BigInteger oriIndex = factorMap.get(temp);
            oriIndex = oriIndex.add(index);
            if (!oriIndex.equals(BigInteger.ZERO)) {
                factorMap.replace(temp, oriIndex);
            }
        } else {
            if (!index.equals(BigInteger.ZERO)) {
                factorMap.put(temp, index);
            }
        }
    }

    private void factorStrAdd(String factorStr) {
        // power
        if (factorStr.matches(RegexConst.powerRegex)) {
            factorMerge(new PowerFactor("x"), Factor.getIndex(factorStr));
        }
        // sin function
        else if (factorStr.matches(RegexConst.sinRegex)) {
            factorMerge(new SinFactor("x"), Factor.getIndex(factorStr));
        }
        // cos function
        else if (factorStr.matches(RegexConst.cosRegex)) {
            factorMerge(new CosFactor("x"), Factor.getIndex(factorStr));
        }
        // constant
        else {
            coe = coe.multiply(new BigInteger(factorStr));
        }
    }
}
