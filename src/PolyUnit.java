import java.math.BigInteger;

public class PolyUnit {
    private BigInteger coe;
    private BigInteger index;
    // define 4 simplified format of poly unit.
    private static final String constant = "[+-]?\\d+";
    private static final String xNoCoeNoIndex = "[+-]?x";
    private static final String xNoCoe = "[+-]?x\\^[+-]?\\d+";
    private static final String xNoIndex = "[+-]?\\d+\\*x";

    PolyUnit(String str) {
        polyUnitRecord(str);
    }

    PolyUnit(BigInteger coe, BigInteger index) {
        this.coe = coe;
        this.index = index;
    }

    /**
     * update coefficient of a unit.
     *
     * @param newCoe
     */
    public void updateCoe(BigInteger newCoe) {
        this.coe = this.coe.add(newCoe);
    }

    public BigInteger getCoe() {
        return coe;
    }

    public BigInteger getIndex() {
        return index;
    }

    public String getUnit() {
        return unitSimpleGenerate(this.coe, this.index);
    }

    public String getDerivation(int derivationNum) {
        BigInteger index = this.index;
        BigInteger coe = this.coe;
        for (int j = 0; j < derivationNum; j++) {
            coe = coe.multiply(index);
            index = index.add(new BigInteger("-1"));
        }
        return unitSimpleGenerate(coe, index);
    }

    /**
     * given poly unit's coefficient and index return standard
     * unit's expression
     *
     * @param coe   coefficient
     * @param index index
     * @return simplified format of unit
     */
    private String unitStdGenerate(BigInteger coe, BigInteger index) {
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
     * given poly unit's coefficient and index return standard
     * unit's expression.
     * Simplification Rule:
     * 1. +1*x, -1*x ---> +x, -x
     * 2. x^0 ---> none
     * 3. x^1 ---> x
     * 4. coe = 0 ---> empty
     *
     * @param coe   coefficient
     * @param index index
     * @return
     */
    private String unitSimpleGenerate(BigInteger coe, BigInteger index) {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append("+");
        // if ignore total unit
        if (coe.equals(BigInteger.ZERO)) {
            return "";
        } else {
            // if constant
            if (index.equals(BigInteger.ZERO)) {
                // if zero -> ignored
                strBuf.append(coe);
            }
            // if not constant
            else {
                // coefficient and x without index
                if (coe.equals(new BigInteger("1"))) {
                    strBuf.append("+x");
                } else if (coe.equals(new BigInteger("-1"))) {
                    strBuf.append("-x");
                } else {
                    strBuf.append(coe);
                    strBuf.append("*x");
                }
                // x's index
                if (!index.equals(new BigInteger("1"))) {
                    strBuf.append("^");
                    strBuf.append(index);
                }
            }
        }
        return strBuf.toString();
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
        if (str.matches(constant)) {
            coe = new BigInteger(str);
            index = new BigInteger("0");
        } else if (str.matches(xNoCoeNoIndex)) {
            if (str.charAt(0) == '-') {
                coe = new BigInteger("-1");
            } else {
                coe = new BigInteger("1");
            }
            index = new BigInteger("1");
        } else if (str.matches(xNoCoe)) {
            if (str.charAt(0) == '-') {
                coe = new BigInteger("-1");
            } else {
                coe = new BigInteger("1");
            }
            index = new BigInteger(str.substring(str.indexOf("^") + 1));
        } else if (str.matches(xNoIndex)) {
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
