package constant;

public interface RegexConst {
    String constRegex = "[+-]?\\d+";
    String powerRegex = "x(\\^([+-]?\\d+))?";
    String sinRegex = "sin(x)(\\^([+-]?\\d+))?";
    String cosRegex = "cos(x)(\\^([+-]?\\d+))?";
    String factorRegex = "(" + constRegex + "|" + powerRegex + "|"
        + sinRegex + "|" + cosRegex + ")";
    String itemRegex = "([+-]?)" + factorRegex + "(\\*" + factorRegex + ")*";
    String polyRegex = itemRegex + "([+-]" + itemRegex + ")*";
}
