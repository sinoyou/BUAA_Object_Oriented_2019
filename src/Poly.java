import Item.Item;
import constant.RegexConst;
import method.DerivateMethod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Poly {

    private boolean valid = true;
    ArrayList<Item> itemList = new ArrayList<>();

    public Poly(){
        super();
    }

    public Poly(String str){
        // check format
        valid = checkFormat(str);

        // simplify
        String simple = simplify(str);
        // split into item
        if(valid){
            Matcher match = Pattern.compile(RegexConst.itemRegex).matcher(simple);
            while(match.find()){
                Item unit = new Item(match.group());
                itemAdd(unit);
            }
        }
    }

    private String simplify(String str) {
        str = str.replaceAll("\\s","");
        str = str.replaceAll("(\\+\\+)|(--)","+");
        str = str.replaceAll("(\\+-)|(-\\+)","-");
        return str;
    }

    public Boolean getValid(){
        return valid;
    }

    public Iterator<Item> getItr(){
        return itemList.iterator();
    }

    public Poly getDerivate(){
        Poly newPoly = new Poly();
        for(Item item:itemList){
            newPoly.itemList.addAll(DerivateMethod.itemDer(item));
        }
        return newPoly;
    }

    private boolean checkFormat(String str){
        // character use check
        if(Pattern.compile(RegexConst.forbidRegex).matcher(str).find())
            return false;
        // illegal space check
        if(Pattern.compile(RegexConst.illegalSpaceRegex).matcher(str).find())
            return false;

        // clear space and format expression for further check
        String temp = str.replaceAll("\\s","");
        if(!(temp.charAt(0)=='+'|temp.charAt(0)=='-'))
            temp = "+"+temp;
        // sequence check
        String regex = "[+-]"+RegexConst.itemRegex;
        Matcher match = Pattern.compile(regex).matcher(temp);
        int head = 0;
        while(match.find(head)&&match.start()==head){
            head = match.end();
        }
        return (head==temp.length());
    }

    private void itemAdd(Item item){
        boolean flag = false;
        for(Item i:itemList){
            // a include b && b include a -> a = b
            if(i.include(item)&&item.include(i)){
                i.setCoe(i.getCoe().add(item.getCoe()));
                flag = true;
                break;
            }
        }
        if(!flag){
            itemList.add(item);
        }
    }

}
