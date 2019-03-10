package factor;

import constant.RegexConst;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConstFactor extends Factor {
    protected BigInteger coe;

    public ConstFactor(String str) {
        Matcher mTemp = Pattern.compile(RegexConst.constRegex).matcher(str);
        if (mTemp.find()) {
            this.coe = new BigInteger(mTemp.group(0));
        }
    }

    public ConstFactor(BigInteger coe) {
        this.coe = coe;
    }

    @Override
    public boolean equals(Object obj) {
        // wrong class;
        return classCheck(obj);
    }

    @Override
    public int hashCode() {
        return this.getClass().getName().hashCode();
    }

    public BigInteger getCoe() {
        return coe;
    }

    public static void main(String[] args){
        ConstFactor cf = new ConstFactor("1");
        System.out.println(cf.equals(new ConstFactor("-1")));
    }
}
