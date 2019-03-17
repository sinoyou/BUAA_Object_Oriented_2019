package parse;

import Const.RegexConst;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class recurParse {
    private ArrayList<String> recurRecord = new ArrayList<>();
    private String exp = null;

    recurParse(String str) {
        this.exp = str;
        expr(false,false);
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

    // <Num> = [+-]?[0-9]+
    public BigInteger getNumber() {
        Matcher match = Pattern.compile(RegexConst.constRegex).matcher(exp);
        if (match.lookingAt()) {
            BigInteger num = new BigInteger(exp.substring(0, match.end()));
            exp = exp.substring(match.end());
            return num;
        }
        else {
            errorExit("Error in Number");
            return null;
        }
    }

    // <Factor> = <Num> | x(^<Num>)? | sin(<Factor>)(^<Num>)? |
    // cos(<Factor>)(^<Num>)? | (Expr)
    public void factor() {
        recurRecord.add("<Factor>");
        Matcher sinMatch = Pattern.compile(RegexConst.sinHeadRegex).matcher(exp);
        Matcher cosMatch = Pattern.compile(RegexConst.cosHeadRegex).matcher(exp);
        // empty judge
        if(exp.isEmpty()){
            errorExit("empty string in factor.");
        }
        // number
        if (isDigit(exp.charAt(0))) {
            getNumber();
        }
        // power function
        else if (isUnit(exp.charAt(0))) {
            exp = exp.substring(1);
            BigInteger power = BigInteger.ONE;
            // check power description
            if (!exp.isEmpty() & exp.charAt(0) == '^') {
                exp = exp.substring(1);
                power = getNumber();
            }
        }
        // sin function
        else if (sinMatch.lookingAt()) {
            exp = exp.substring(sinMatch.end());
            BigInteger power = BigInteger.ONE;
            factor();
            if (isRightParen(exp.charAt(0))) {
                exp = exp.substring(1);
                if(!exp.isEmpty() & exp.charAt(0) == '^'){
                    exp = exp.substring(1);
                    power = getNumber();
                }
            }else {
                errorExit("no right paren match in sin.");
            }
        }
        // cos function
        else if (cosMatch.lookingAt()) {
            exp = exp.substring(cosMatch.end());
            BigInteger power = BigInteger.ONE;
            factor();
            if (isRightParen(exp.charAt(0))) {
                exp = exp.substring(1);
                if(!exp.isEmpty() & exp.charAt(0) == '^'){
                    exp = exp.substring(1);
                    power = getNumber();
                }
            }else {
                errorExit("no right paren match in cos.");
            }
        }
        // sub expression
        else if (isLeftParen(exp.charAt(0))) {
            expr(false,true);
            if (isRightParen(exp.charAt(0))) {
                exp = exp.substring(1);
            }else {
                errorExit("no right paren match in inner exp.");
            }
        }
        // no available factor -> false
        else {
            errorExit("unknown type in factor.");
        }
    }

    // <Item> = [+-]?<Factor>(*<SubItem>)?
    // <subItem> = <Factor>(*<subItem>)?
    public void item(boolean sub) {
        recurRecord.add("<Item>");
        char c = '+';
        if(!sub){
            if (isSign(exp.charAt(0))) {
                c = exp.charAt(0);
                exp = exp.substring(1);
            }
        }
        factor();
        // 继承情况：接下来是乘号运算符。
        if (!exp.isEmpty() & isMul(exp.charAt(0))) {
            exp = exp.substring(1);
            item(true);
        }
        // 结束情况：交由上级处理。
        else {
            // return
        }
    }


    // 当确认第一个Item后的其他情况：1-继承，2-结束，3-错误
    // <Expr> = [+-]?<Item>([+-]<Expr>)?
    public void expr(boolean sub, boolean factor) {
        recurRecord.add("<Expression>");
        char c = '+';
        if(isSign(exp.charAt(0))){
            c = exp.charAt(0);
            exp = exp.substring(1);
        }
        item(false);
        // 结束判断1:非因子串，结束符：字符串结束
        if(!factor & exp.isEmpty()){
            return;
        }
        // 结束判断2：因子表达式，结束符：)
        else if(factor & isRightParen(exp.charAt(0))){
            return;
        }
        // 继承判断：接下来是运算符号
        else if(isSign(exp.charAt(0))){
            char op = exp.charAt(0);
            exp = exp.substring(1);
            expr(true,factor);
        }
        // 非语法情况：错误
        else{
            errorExit("unknown type in expression.");
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
            System.out.println(step);
        }
    }
}
