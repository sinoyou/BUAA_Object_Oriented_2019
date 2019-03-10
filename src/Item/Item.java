package Item;

import constant.RegexConst;
import factor.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Set;

public class Item implements Cloneable {
    // define a map to store all kinds of basic factors(except const) and its index.
    // basic factors definition: function(const) factor with index = 1.
    HashMap<Factor, BigInteger> factorMap = new HashMap<>();
    BigInteger coe = BigInteger.ONE;

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

    /*
    public HashMap<Factor, BigInteger> getFactorMap() {
        return factorMap;
    }
    */

    public Set<Factor> getMapSet(){ return factorMap.keySet();}

    public BigInteger getIndex(Factor i){
        return factorMap.getOrDefault(i,null);
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

    @Override
    public Object clone() {
        Item item = new Item();
        item.coe = this.coe;
        for(Factor i:this.factorMap.keySet()){
            item.factorMap.put((Factor)i.clone(),this.factorMap.get(i));
        }
        return item;
    }

    public Item itemMultiply(Item a) {
        Item newItem = (Item) this.clone();
        newItem.coe = newItem.coe.multiply(a.getCoe());
        for (Factor i : a.factorMap.keySet()) {
            newItem.addFactor(i, a.factorMap.get(i));
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

    private void factorMerge(Factor temp, BigInteger index) {
        // merge into present map, add or update
        if (factorMap.containsKey(temp)) {
            BigInteger oriIndex = factorMap.get(temp);
            oriIndex = oriIndex.add(index);
            if(!oriIndex.equals(BigInteger.ZERO))
                factorMap.replace(temp, oriIndex);
        } else {
            if(!index.equals(BigInteger.ZERO))
                factorMap.put(temp, index);
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
