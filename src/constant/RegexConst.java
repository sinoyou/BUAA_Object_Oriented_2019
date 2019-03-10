package constant;

public interface RegexConst {
    // allowed char
    String allowRegex = "[0-9]|[+-\\^\\*]|(sin)|(cos)|[()]|[ \\t]";
    String forbidRegex = "[^0-9+-^*sincos()x \\t]";
    // illegal space regex: 1.space in number. 2.space bettwen +- and
    // num following * or ^. 3.space in cos/sin. 4.three [+-] op [space] number
    String illegalSpaceRegex = "(\\d+\\s+\\d+)|"+
        "([^*]\\s*[+-]\\s+\\d+)|"+
        "((s\\s+in)|(si\\s+)|(c\\s+os)|(co\\s+s))|"+
        "[+-]\\s*[+-]\\s*[+-]\\s+\\d";
    String constRegex = "[+-]?\\d+";
    String powerRegex = "x(\\^([+-]?\\d+))?";
    String sinRegex = "sin\\(x\\)(\\^([+-]?\\d+))?";
    String cosRegex = "cos\\(x\\)(\\^([+-]?\\d+))?";
    String factorRegex = "(" + constRegex + "|" + powerRegex + "|"
        + sinRegex + "|" + cosRegex + ")";
    String itemRegex = "([+-]?)" + factorRegex + "(\\*" + factorRegex + ")*";
//    String polyRegex = itemRegex + "([+-]" + itemRegex + ")*";
}
