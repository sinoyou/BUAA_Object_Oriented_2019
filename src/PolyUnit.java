import java.math.BigInteger;

public class PolyUnit {
    private BigInteger coe;
    private BigInteger index;

    PolyUnit(String str) {
        polyUnitRecord(str);
    }

    PolyUnit(BigInteger coe, BigInteger index) {
        this.coe = coe;
        this.index = index;
    }

    public String getUnit() {
        return unitGenerate(this.coe, this.index);
    }

    public BigInteger getCoe() {
        return coe;
    }

    public BigInteger getIndex() {
        return index;
    }

    /**
     * update coefficient of a unit.
     *
     * @param newCoe
     */
    public void updateCoe(BigInteger newCoe) {
        this.coe = this.coe.add(newCoe);
    }

    public String derivation(int derivationNum) {
        BigInteger index = this.index;
        BigInteger coe = this.coe;
        for (int j = 0; j < derivationNum; j++) {
            coe = coe.multiply(index);
            index = index.add(new BigInteger("-1"));
        }
        return unitGenerate(coe, index);
    }

    /**
     * given poly unit's coefficient and index return simplified
     * unit's expression
     *
     * @param coe   coefficient
     * @param index index
     * @return simplified format of unit
     */
    private String unitGenerate(BigInteger coe, BigInteger index) {
        StringBuffer strb = new StringBuffer();
        // special occasion coefficient = 0
        if (coe.equals(new BigInteger("0"))) {
            return strb.toString();
        } else {
            strb.append("+");
            // coe
            strb.append(coe);
            strb.append("*x");
            // index
            strb.append("^");
            strb.append(index);
            return strb.toString();
        }
    }

    /**
     * analysis unit information and update indexMap and coeList
     *
     * @param str string of poly unit
     */
    private void polyUnitRecord(String str) {
        BigInteger coe = BigInteger.ZERO;
        BigInteger index = BigInteger.ZERO;
        // take out coefficient and index in total 5 situation
        if (str.matches("[+-]?\\d+")) {
            coe = new BigInteger(str);
            index = new BigInteger("0");
        } else if (str.matches("[+-]?x")) {
            if (str.charAt(0) == '-') {
                coe = new BigInteger("-1");
            } else {
                coe = new BigInteger("1");
            }
            index = new BigInteger("1");
        } else if (str.matches("[+-]?x\\^[+-]?\\d+")) {
            if (str.charAt(0) == '-') {
                coe = new BigInteger("-1");
            } else {
                coe = new BigInteger("1");
            }
            index = new BigInteger(str.substring(str.indexOf("^") + 1));
        } else if (str.matches("[+-]?\\d+\\*x")) {
            coe = new BigInteger(str.substring(0, str.indexOf("*")));
            index = new BigInteger("1");
        } else {
            coe = new BigInteger(str.substring(0, str.indexOf("*")));
            index = new BigInteger(str.substring(str.indexOf("^") + 1));
        }
        this.index = index;
        this.coe = coe;
    }
}
