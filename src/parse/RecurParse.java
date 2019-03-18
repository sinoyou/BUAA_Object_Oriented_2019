package parse;

import constant.RangeConst;
import constant.RegexConst;
import node.ConstNode;
import node.Node;
import node.func.CosNode;
import node.func.PowerNode;
import node.func.SinNode;
import node.operation.AddNode;
import node.operation.MulNode;
import node.operation.SubNode;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecurParse {
    private ArrayList<String> recurRecord = new ArrayList<>();
    private String exp = null;
    private Node root = null;

    RecurParse(String str) {
        this.exp = str;
        root = expr(false, false);
        printTrack();
    }

    private static boolean isSign(char c) {
        return (c == '+' | c == '-');
    }

    private static boolean isDigit(char c) {
        return (c >= '0' & c <= '9');
    }

    private static boolean isUnit(char c) {
        return (c == 'x');
    }

    private static boolean isLeftParen(char c) {
        return (c == '(');
    }

    private static boolean isRightParen(char c) {
        return (c == ')');
    }

    private static boolean isMul(char c) {
        return (c == '*');
    }

    private void emptyJugde(String place) {
        // empty judge
        if (exp.isEmpty()) {
            errorExit("empty string in " + place);
        }
    }

    private BigInteger power() {
        if (!exp.isEmpty() && exp.charAt(0) == '^') {
            exp = exp.substring(1);
            BigInteger power = number();
            if (power.compareTo(RangeConst.power) > 0) {
                errorExit("power index out of range.");
                return null;
            } else {
                return power;
            }
        } else {
            return BigInteger.ONE;
        }
    }

    public Node getRoot() {
        return root;
    }

    // <Num> = [+-]?[0-9]+
    public BigInteger number() {
        recurRecord.add("<Number>");
        emptyJugde("number");
        Matcher match = Pattern.compile(RegexConst.constRegex).matcher(exp);
        if (match.lookingAt()) {
            BigInteger num = new BigInteger(exp.substring(0, match.end()));
            exp = exp.substring(match.end());
            return num;
        } else {
            errorExit("Error in Number");
            return null;
        }
    }

    // <Factor> = <Num> | x(^<Num>)? | sin(<Factor>)(^<Num>)? |
    // cos(<Factor>)(^<Num>)? | (Expr)
    public Node factor() {
        emptyJugde("factor");
        Matcher sinMatch = Pattern.compile(RegexConst.sinHeadRegex)
            .matcher(exp);
        Matcher cosMatch = Pattern.compile(RegexConst.cosHeadRegex)
            .matcher(exp);
        // number
        if (isDigit(exp.charAt(0)) | isSign(exp.charAt(0))) {
            recurRecord.add("<Factor:Num>");
            BigInteger num = number();
            return new ConstNode(num);
        }
        // power function
        else if (isUnit(exp.charAt(0))) {
            recurRecord.add("<Factor:Power>");
            exp = exp.substring(1);
            // get power
            BigInteger power = power();
            return new PowerNode(power);
        }
        // sin function
        else if (sinMatch.lookingAt()) {
            recurRecord.add("<Factor:Sin>");
            exp = exp.substring(sinMatch.end());
            Node inner = factor();
            if (!exp.isEmpty() && isRightParen(exp.charAt(0))) {
                exp = exp.substring(1);
                BigInteger power = power();
                return new SinNode(inner, power);
            } else {
                errorExit("wrong format of sin()");
                return null;
            }
        }
        // cos function
        else if (cosMatch.lookingAt()) {
            recurRecord.add("<Factor:Cos>");
            exp = exp.substring(cosMatch.end());
            Node inner = factor();
            if (!exp.isEmpty() && isRightParen(exp.charAt(0))) {
                exp = exp.substring(1);
                BigInteger power = power();
                return new CosNode(inner, power);
            } else {
                errorExit("wrong format of cos()");
                return null;
            }
        }
        // sub expression
        else if (isLeftParen(exp.charAt(0))) {
            recurRecord.add("<Factor:SubExpr>");
            exp = exp.substring(1);
            Node inner = expr(false, true);
            if (!exp.isEmpty() && isRightParen(exp.charAt(0))) {
                exp = exp.substring(1);
            } else {
                errorExit("no right paren match in inner exp.");
            }
            return inner;
        }
        // no available factor -> false
        else {
            errorExit("unknown type in factor.");
            return null;
        }
    }

    // <Item> = [+-]?<Factor>(*<SubItem>)?
    // <subItem> = <Factor>(*<subItem>)?
    public Node item(boolean sub) {
        recurRecord.add("<Item>");
        emptyJugde("item");
        char c = '+';
        if (!sub) {
            if (isSign(exp.charAt(0))) {
                c = exp.charAt(0);
                exp = exp.substring(1);
            }
        }
        Node factorNode = factor();
        // 首个项的省略
        if (c == '-') {
            factorNode = new MulNode(new ConstNode(new BigInteger("-1")),
                factorNode);
        }
        // 继承情况：接下来是乘号运算符。
        if (!exp.isEmpty() && isMul(exp.charAt(0))) {
            exp = exp.substring(1);
            Node itemNode = item(true);
            return new MulNode(factorNode, itemNode);
        }
        // 结束情况：交由上级处理。
        else {
            return factorNode;
        }
    }


    // 当确认第一个Item后的其他情况：1-继承，2-结束，3-错误
    // <Expr> = [+-]?<Item>([+-]<Expr>)?
    public Node expr(boolean sub, boolean factor) {
        recurRecord.add("<Expression>");
        emptyJugde("expression");
        char c = '+';
        if (!sub && isSign(exp.charAt(0))) {
            c = exp.charAt(0);
            exp = exp.substring(1);
        }
        Node itemNode = item(false);
        // 特殊情况：首个表达式且为负数
        if (c == '-') {
            itemNode = new MulNode(new ConstNode(new BigInteger("-1")),
                itemNode);
        }
        // 结束判断1:非因子串，结束符：字符串结束
        if (!factor && exp.isEmpty()) {
            return itemNode;
        }
        // 结束判断2：因子表达式，结束符：)
        else if (factor && !exp.isEmpty() && isRightParen(exp.charAt(0))) {
            return itemNode;
        }
        // 继承判断：接下来是运算符号
        else if (!exp.isEmpty() && isSign(exp.charAt(0))) {
            char op = exp.charAt(0);
            exp = exp.substring(1);
            Node exprNode = expr(true, factor);
            if (op == '+') {
                return new AddNode(itemNode, exprNode);
            } else {
                return new SubNode(itemNode, exprNode);
            }
        }
        // 非语法情况：错误
        else {
            errorExit("unknown type in expression.");
            return null;
        }
    }

    // if unexpected situation happen -> wrong format and exit.
    private void errorExit(String reason) {
        System.out.println("WRONG FORMAT!");
        System.err.println(reason);
        printTrack();
        System.exit(0);
    }

    // print visit track
    private void printTrack() {
        for (String step : recurRecord) {
            System.err.println(step);
        }
    }
}
