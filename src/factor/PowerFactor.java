package factor;

public class PowerFactor extends Factor {
    protected String base;

    /*
    // given power factor's string, generate factor instance.
    public PowerFactor(String str) {
        Matcher mTemp = Pattern.compile(RegexConst.powerRegex).matcher(str);
        this.base = "x";
        if (mTemp.find()) {
            if (mTemp.group(2) != null) {
                this.index = new BigInteger(mTemp.group(2));
            } else {
                this.index = BigInteger.ONE;
            }
        }
    }
    */

    public PowerFactor(String base) {
        this.base = base;
    }

    @Override
    public boolean equals(Object obj) {
        Boolean flag = classCheck(obj);
        if(flag){
            PowerFactor temp = (PowerFactor) obj;
            if(!temp.base.equals(this.base)){
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public int hashCode() {
        return base.hashCode();
    }

    public String getBase() {
        return base;
    }
}
