package regex;

public interface RegexConst {
    // allowed char
    String forbidRegex = "[^0-9+-^*sincos()x \\t]";
    // illegal space regex: 1.space in number. 2.space bettwen +- and
    // num following * or ^. 3.space in cos/sin. 4.three [+-] op [space] number
    String illegalSpaceRegex = "(\\d+\\s+\\d+)|" +
        "([\\^\\*]\\s*[+-]\\s+\\d+)|" +
        "((s\\s+i\\s*n)|(s\\s*i\\s+)|(c\\s+o\\s*s)|(c\\s*o\\s+s))|" +
        "[+-]\\s*[+-]\\s*[+-]\\s+\\d";
    String constRegex = "[+-]?\\d+";
    String powerRegex = "x(\\^([+-]?\\d+))?";
    String sinRegex = "sin\\(x\\)(\\^([+-]?\\d+))?";
    String cosRegex = "cos\\(x\\)(\\^([+-]?\\d+))?";
    String sinHeadRegex = "sin\\(";
    String cosHeadRegex = "cos\\(";

    /*
    String factorRegex = "(" + constRegex + "|" + powerRegex + "|"

        + sinRegex + "|" + cosRegex + ")";
    String itemRegex = "([+-]?)" + factorRegex + "(\\*" + factorRegex + ")*";
    String polyRegex = itemRegex + "([+-]" + itemRegex + ")*";
    */
}
