package factor;

import constant.RegexConst;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Factor implements Cloneable{
    protected String base;
    public Factor(String str){
        base = str;
    }

    @Override
    public boolean equals(Object obj){
        if(this.getClass()!=obj.getClass()){
            return false;
        }
        Factor temp = (Factor)obj;
        return this.base.equals(temp.base);
    }

    @Override
    public int hashCode(){
        return base.hashCode();
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

    @Override
    public Object clone() {
        Object obj=null;
        try {
            obj= super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public String getBase() {
        return base;
    }
}
