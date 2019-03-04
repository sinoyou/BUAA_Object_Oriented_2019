import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Poly {

    // 3 states of poly units: 1) C*x^n. 2)C. 3)[+-]x^n
    private static final String polyUnitRegex =
        "(" +
            "([+-]?\\d+\\s*\\*\\s*x(\\s*\\^\\s*[+-]?\\d+)?)|" +
            "([+-]?\\d+)|" +
            "([+-]?\\s*x(\\s*\\^\\s*[+-]?\\d+)?)" +
            ")";
    private static final String polyRegex =
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
        // TODO
        // valid = formatCheck(str);
        valid = formatCheckSequence(str);

        // split valid expression into polyUnit
        if (valid) {
            String strNew = polySimplify(str);
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

        return polySimplify(polyGenerate(0));
    }

    /**
     * return formatted expression after one getDerivation
     *
     * @return expression
     */
    public String getDerivativePoly() {
        return polySimplify(polyGenerate(1));
    }

    /**
     * check format using poly unit, to avoid stack overflow when string is to
     * large
     *
     * @param str expression string to be checked
     * @return
     */
    private boolean formatCheckSequence(String str) {
        // case 1: empty string
        if (str.isEmpty()) {
            return false;
        }
        // case 2: check first and following poly unit
        else {
            String firstUnit = "\\s*[+-]?\\s*" + polyUnitRegex + "\\s*";
            String followUnit = "\\s*[+-]\\s*" + polyUnitRegex + "\\s*";
            Pattern firstUnitPattern = Pattern.compile(firstUnit);
            Pattern followUnitPattern = Pattern.compile(followUnit);
            Matcher firstMatcher = firstUnitPattern.matcher(str);
            Matcher followMatcher = followUnitPattern.matcher(str);
            // check first poly unit
            if (firstMatcher.find() && firstMatcher.start() == 0) {
                // check following unit
                int p = firstMatcher.end();
                while (followMatcher.find(p) && followMatcher.start() == p) {
                    p = followMatcher.end();
                }
                // reach the end of expression -> true
                return (p == str.length());
            }
        }
        return false;
    }


    /**
     * delete empty space and replace double operation
     * Applied for number with 000123
     *
     * @param str original str
     */
    private String polySimplify(String str) {
        String newStr = str;
        // basic simplify
        newStr = newStr.replaceAll("\\s", "");
        newStr = newStr.replaceAll("(\\+-)|(-\\+)", "-");
        newStr = newStr.replaceAll("(\\+{2})|(-{2})", "+");
        return newStr;
    }

    /**
     * generate poly of original poly given getDerivation number
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
            strBuf.append(temp.getDerivation(derivationNum));
        }
        return strBuf.toString();
    }

    /*public static void main(String[] arg) {
        String s = "         -123987* x^123  + -x^1 + 192873          ";
        Poly p = new Poly(s);
        p.formatCheckSequence(s);
    }*/
}
