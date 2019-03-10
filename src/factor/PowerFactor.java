package factor;

public class PowerFactor extends Factor {
    // protected String base;
    public PowerFactor(String base) {
        super(base);
    }
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

}
