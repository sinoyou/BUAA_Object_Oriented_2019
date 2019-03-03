import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Poly {

    // 3 states of poly units: 1) C*x^n. 2)C. 3)[+-]x^n
    private static String polyUnitRegex =
        "(" +
            "([+-]?\\d+\\s*\\*\\s*x(\\s*\\^\\s*[+-]?\\d+)?)|" +
            "([+-]?\\d+)|" +
            "([+-]?\\s*x(\\s*\\^\\s*[+-]?\\d+)?)" +
            ")";
    private static String polyRegex =
        "\\s*" +
            "\\s*[+-]?\\s*" + polyUnitRegex + "\\s*" +
            "(" + "\\s*[+-]\\s*" + polyUnitRegex + "\\s*" + ")*" +
            "\\s*";
    // valid of input expression
    private boolean valid;
    // record appeared x's index
    private ArrayList<BigInteger> indexList = new ArrayList<>();
    // record coefficient of corresponding index
    private HashMap<BigInteger, PolyUnit> coeMap = new HashMap<>();

    /**
     * read expression and construct object
     *
     * @param str expression in one line
     */
    public Poly(String str) {
        // format check
        valid = formatCheck(str);

        // split valid expression into polyUnit
        if (valid) {
            String strNew = polySafeSimplify(str);
            Pattern polyUnitPattern = Pattern.compile(polyUnitRegex);
            Matcher polyUnitMatch = polyUnitPattern.matcher(strNew);
            while (polyUnitMatch.find()) {
                PolyUnit polyUnit = new PolyUnit(polyUnitMatch.group(0));
                // add index in indexList
                BigInteger index = polyUnit.getIndex();
                BigInteger coe = polyUnit.getCoe();
                if (!indexList.contains(index)) {
                    indexList.add(index);
                }
                // update(or add) Coefficient map
                if (coeMap.containsKey(index)) {
                    PolyUnit temp = coeMap.get(index);
                    temp.updateCoe(coe);
                    coeMap.replace(index, temp);
                } else {
                    coeMap.put(index, polyUnit);
                }
            }
        }
    }

    /**
     * check format validation of poly
     */
    public boolean formatCheck() {
        return valid;
    }

    /**
     * check format validation of given expression
     *
     * @param str String to be checked
     */
    private boolean formatCheck(String str) {
        Pattern polyPattern = Pattern.compile(polyRegex);
        Matcher polyMatch = polyPattern.matcher(str);
        return polyMatch.matches();
    }

    /**
     * return formatted original expression
     *
     * @return expression
     */
    public String getOriginalPoly() {
        return polyGenerate(0);
    }

    /**
     * return formatted expression after one derivation
     *
     * @return expression
     */
    public String getDerivativePoly() {
        String safeSimplify = polySafeSimplify(polyGenerate(1));
        String advanceSimplify = polyAdvanceSimplify(safeSimplify);
        return advanceSimplify;
    }

    /**
     * delete empty space and replace double operation
     * Applied for number with 000123
     * @param str original str
     */
    private String polySafeSimplify(String str) {

        String newStr = str;
        // basic simplify
        newStr = newStr.replaceAll("\\s", "");
        newStr = newStr.replaceAll("(\\+-)|(-\\+)", "-");
        newStr = newStr.replaceAll("(\\+{2})|(-{2})", "+");
        return newStr;
    }

    /**
     * advance simplify expression, unnecessary front 0 must be removed first!
     * @param str original str
     * @return
     */
    private String polyAdvanceSimplify(String str) {
        String newStr = str;
        // advance simplify
        newStr = newStr.replaceAll("x\\^\\+?1", "x");
        newStr = newStr.replaceAll("\\*x\\^[+-]?0", "");
        newStr = newStr.replaceAll("1\\*x", "x");
        return newStr;
    }


    /**
     * generate poly of original poly given derivation number
     *
     * @param derivationNum times of derivative
     * @return poly expression
     */
    private String polyGenerate(int derivationNum) {
        // sort by index
        indexList.sort(Collections.reverseOrder());
        // generate message
        StringBuffer strBuf = new StringBuffer();
        strBuf.append("0");
        for (int i = 0; i < indexList.size(); i++) {
            PolyUnit temp = coeMap.get(indexList.get(i));
            strBuf.append(temp.derivation(derivationNum));
        }
        return strBuf.toString();
    }

}
