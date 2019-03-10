package Item;

import constant.RegexConst;
import factor.*;

import java.math.BigInteger;
import java.util.HashMap;

public class Item {
    // define a map to store all kinds of basic factors(except number) and its index.
    // basic factors definition: function(const) factor with index = 1.
    HashMap<Factor, BigInteger> factorMap = new HashMap<>();
    BigInteger coe = BigInteger.ONE;

    public Item(){

    }

    public Item(String expr){
        String temp = expr;
        // special occasion, short +/- at first
        if(temp.charAt(0)=='+'){
            factorMerge(new ConstFactor("1"),Factor.getIndex("1"));
            temp = temp.substring(1);
        } else if(temp.charAt(0)=='-'){
            factorMerge(new ConstFactor("-1"),Factor.getIndex("-1"));
            temp = temp.substring(1);
        }

        // split into factors by *
        String[] factorList = temp.split("\\*");
        for(String factorStr:factorList){
            factorStrAdd(factorStr);
        }
    }

    public void factorAdd(Factor factor, BigInteger index){
        factorMerge(factor,index);
    }

    public HashMap<Factor, BigInteger> getFactorMap() {
        return factorMap;
    }

    private void factorMerge(Factor temp, BigInteger index){
        // merge into present map, add or update
        if(factorMap.containsKey(temp)){
            // if constant class
            if(temp.getClass()==ConstFactor.class){
                BigInteger oriCoe = factorMap.get(temp);
                oriCoe = oriCoe.multiply(((ConstFactor) temp).getCoe());
                factorMap.replace(temp,oriCoe);
            }else{
                BigInteger oriIndex = factorMap.get(temp);
                oriIndex = oriIndex.add(index);
                factorMap.replace(temp,oriIndex);
            }
        }else{
            // if constant class
            if(temp.getClass()==ConstFactor.class){
                factorMap.put(temp,((ConstFactor) temp).getCoe());
            }else{
                factorMap.put(temp,index);
            }
        }
    }

    private void factorStrAdd(String factorStr){
        // power
        if(factorStr.matches(RegexConst.powerRegex)){
            factorMerge(new PowerFactor(factorStr),Factor.getIndex(factorStr));
        }
        // sin function
        else if(factorStr.matches(RegexConst.sinRegex)){
            factorMerge(new SinFactor(factorStr),Factor.getIndex(factorStr));
        }
        // cos function
        else if(factorStr.matches(RegexConst.cosRegex)){
            factorMerge(new CosFactor(factorStr),Factor.getIndex(factorStr));
        }
        // constant
        else {
            factorMerge(new ConstFactor(factorStr),Factor.getIndex(factorStr));
        }
    }
}
