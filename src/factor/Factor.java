package factor;

import constant.RegexConst;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Factor {

    public Factor(){}

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

    protected boolean classCheck(Object factor){
        return (factor.getClass()==this.getClass());
    }

    public static BigInteger getIndex(String str){
        Matcher mSin = Pattern.compile(RegexConst.sinRegex).matcher(str);
        Matcher mCos = Pattern.compile(RegexConst.cosRegex).matcher(str);
        Matcher mPower = Pattern.compile(RegexConst.powerRegex).matcher(str);

        BigInteger index = BigInteger.ONE;
        if(mSin.find()){
            if (mSin.group(2) != null) {
                index = new BigInteger(mSin.group(2));
            } else {
                index = BigInteger.ONE;
            }
        }else if(mCos.find()){
            if(mCos.group(2)!=null){
                index = new BigInteger(mCos.group(2));
            }else{
                index = BigInteger.ONE;
            }
        }else if(mPower.find()){
            if(mPower.group(2)!=null){
                index = new BigInteger(mPower.group(2));
            }else{
                index = BigInteger.ONE;
            }
        }
        return index;
    }

}
