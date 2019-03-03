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
                    "([+-]?x(\\s*\\^\\s*[+-]?\\d+)?)" +
                    ")";
    private static String polyRegex =
            "\\s*" +
                    "\\s*[+-]?\\s*" + polyUnitRegex + "\\s*" +
                    "(" + "\\s*[+-]\\s*" + polyUnitRegex + "\\s*" + ")*" +
                    "\\s*";
    // valid of input expression
    private boolean valid;
    // record appeared x's index
    private ArrayList<Integer> indexList = new ArrayList<>();
    // record coefficient of corresponding index
    private HashMap<Integer, Integer> coeMap = new HashMap<>();

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
            str = polySimplify(str);
            Pattern polyUnitPattern = Pattern.compile(polyUnitRegex);
            Matcher polyUnitMatch = polyUnitPattern.matcher(str);
            while (polyUnitMatch.find()) {
                polyUnitRecord(polyUnitMatch.group(0));
            }
        }
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
     * analysis unit information and update indexMap and coeList
     *
     * @param str string of poly unit
     */
    private void polyUnitRecord(String str) {
//        System.out.println(str);
        int coe, index;
        // take out coefficient and index in total 5 situation
        if (str.matches("[+-]?\\d+")) {
            coe = Integer.parseInt(str);
            index = 0;
        } else if (str.matches("[+-]?x")) {
            coe = 1;
            index = 1;
        } else if (str.matches("[+-]?x\\^[+-]?\\d+")) {
            coe = 1;
            index = Integer.parseInt(str.substring(str.indexOf("^") + 1));
        } else if (str.matches("[+-]?\\d+\\*x")) {
            coe = Integer.parseInt(str.substring(0, str.indexOf("*")));
            index = 1;
        } else {
            coe = Integer.parseInt(str.substring(0, str.indexOf("*")));
            index = Integer.parseInt(str.substring(str.indexOf("^") + 1));
        }

        // add index in indexList and update coe map
        if (!indexList.contains(index)) {
            indexList.add(index);
        }
        if (coeMap.containsKey(index)) {
            int oriValue = coeMap.get(index);
            coeMap.replace(index, oriValue + coe);
        } else {
            coeMap.put(index, coe);
        }
    }

    /**
     * delete empty space and replace double operation
     *
     * @param str original str
     */
    private String polySimplify(String str) {
        // basic simplify
        str = str.replaceAll("\\s", "");
        str = str.replaceAll("(\\+-)|(-\\+)", "-");
        str = str.replaceAll("(\\+{2})|(-{2})", "+");
        // advance simplify
        str = str.replaceAll("x\\^\\+?1","x");
        str = str.replaceAll("\\*x\\^[+-]?0","");
        str = str.replaceAll("1\\*x]","x");

        return str;
    }

    /**
     * given poly unit's coefficient and index return simplified unit's expression
     *
     * @param coe   coefficient
     * @param index index
     * @return simplified format of unit
     */
    private String unitGenerate(int coe, int index) {
        StringBuffer strb = new StringBuffer();
        // special occasion coefficient = 0
        if(coe==0){
            return strb.toString();
        }
        else{
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
            int index = indexList.get(i);
            int coe = coeMap.get(index);
            for (int j = 0; j < derivationNum; j++) {
                coe *= index;
                index -= 1;
            }
            strBuf.append(unitGenerate(coe, index));
        }
        return strBuf.toString();
    }

    /**
     * check format validation of poly
     */
    public boolean formatCheck() {
        return valid;
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
        return polySimplify(polyGenerate(1));
    }

    // test function
    public static void main(String[] arg) {
        ArrayList<String> strItem = new ArrayList<>();
//        strItem.add("+ -5*x^5 - 6 + 7*x + x^5");
//        strItem.add("1926 0817 * x");
        strItem.add("1");
        strItem.add("4*x+x^2+x");
        strItem.add("4*x+x^2+x");
        strItem.add("4x+x^2+x");
        strItem.add("- -4*x + x ^ 2 + x");
        strItem.add("+4*x - -x^2 + x");
        strItem.add("+19260817*x");
        strItem.add("+ 19260817*x");
        strItem.add("+ +19260817*x");
        strItem.add("++ 19260817*x");
        strItem.add("1926 0817 * x");
        strItem.add(" ");
        strItem.add("");
        for(int i=0;i<strItem.size();i++){
            Poly poly = new Poly(strItem.get(i));
            if(poly.formatCheck()){
                System.out.print(poly.getOriginalPoly()+"  <------->  ");
                System.out.println(poly.getDerivativePoly());
            }else {
                System.out.println("WRONG FORMAT!");
            }
        }
    }
}
