import constant.RegexConst;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Try {
    public void find(String str){
        Matcher mTemp = Pattern.compile(RegexConst.powerRegex).matcher(str);
        if(mTemp.find()){
            System.out.println(mTemp.groupCount());
            if(mTemp.groupCount()>=2){
                System.out.println(mTemp.group(2));
            }
        }
    }

    public static void main(String[] args){
        Try t = new Try();
        t.find("x^+100");
        t.find("x^-100");
        t.find("x^100");
        t.find("x");
    }
}
