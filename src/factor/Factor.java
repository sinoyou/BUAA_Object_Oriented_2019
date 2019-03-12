package factor;

import constant.RegexConst;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Factor implements Cloneable {
    private String base;

    public Factor(String str) {
        base = str;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Factor temp = (Factor) obj;
        return this.base.equals(temp.base);
    }

    @Override
    public int hashCode() {
        // System.out.println((this.getClass().getName()+base).hashCode());
        // return base.hashCode();
        return (this.getClass().getName() + base).hashCode();
    }

    public static BigInteger getIndex(String str) {
        Matcher matchSin = Pattern.compile(RegexConst.sinRegex).matcher(str);
        Matcher matchCos = Pattern.compile(RegexConst.cosRegex).matcher(str);
        Matcher matchPow = Pattern.compile(RegexConst.powerRegex).matcher(str);

        BigInteger index = BigInteger.ONE;
        if (matchSin.find()) {
            if (matchSin.group(2) != null) {
                index = new BigInteger(matchSin.group(2));
            } else {
                index = BigInteger.ONE;
            }
        } else if (matchCos.find()) {
            if (matchCos.group(2) != null) {
                index = new BigInteger(matchCos.group(2));
            } else {
                index = BigInteger.ONE;
            }
        } else if (matchPow.find()) {
            if (matchPow.group(2) != null) {
                index = new BigInteger(matchPow.group(2));
            } else {
                index = BigInteger.ONE;
            }
        }
        return index;
    }

    @Override
    public Object clone() {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public String getBase() {
        return base;
    }
}
