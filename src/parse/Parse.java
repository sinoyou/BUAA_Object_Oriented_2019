package parse;

import constant.RegexConst;
import node.Node;

import java.util.regex.Pattern;

public class Parse {
    private boolean primaryValid;
    private RecurParse recur = null;

    public Parse(String str) throws Exception {
        primaryValid = primaryCheck(str);
        if (primaryValid) {
            String simpleStr = simplify(str);
            recur = new RecurParse(simpleStr);
        }
    }

    public Node getRoot() {
        return recur.getRoot();
    }

    public boolean isValid() {
        return primaryValid;
    }

    /**
     * 1. illegal char use.
     * 2. wrong empty space.
     * 3. left and right paren's match.
     *
     * @param str
     * @return
     */
    public static boolean primaryCheck(String str) {
        boolean valid = true;
        // 1
        if (Pattern.compile(RegexConst.forbidRegex).matcher(str).find()) {
            valid = false;
        }
        // 2
        if (Pattern.compile(RegexConst.illegalSpaceRegex).matcher(str).find()) {
            valid = false;
        }
        // 3
        int cnt = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                cnt++;
            } else if (str.charAt(i) == ')') {
                cnt--;
            }
        }
        if (cnt != 0) {
            valid = false;
        }
        // 4:empty string
        if (str.isEmpty()) {
            valid = false;
        }
        return valid;
    }

    /**
     * simplify preparation rule:
     * 1. space clear.
     *
     * @param str
     * @return
     */
    private static String simplify(String str) {
        String newStr = str;
        newStr = newStr.replaceAll("[ \t]", "");
        return newStr;
    }
}
