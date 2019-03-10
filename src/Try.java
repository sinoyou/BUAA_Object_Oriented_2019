import constant.RegexConst;
import factor.Factor;
import factor.PowerFactor;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Try {
    public void find(HashMap<Factor,Integer> i){
        i.replace(new PowerFactor("x"),10);
    }

    public static void main(String[] args){
        HashMap<Factor,Integer> a = new HashMap<>();
        a.put(new PowerFactor("x"),1);
        System.out.println(a.get(new PowerFactor("x")));
        Try t = new Try();
        t.find(a);
        System.out.println(a.get(new PowerFactor("x")));
    }
}
