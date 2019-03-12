package poly;

import item.Item;
import constant.RegexConst;
import method.DerivateMethod;
import method.ItemComparator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Poly {

    private boolean valid = false;
    private ArrayList<Item> itemList = new ArrayList<>();

    public Poly() {
        super();
    }

    public Poly(String str) {
        // check format
        valid = checkFormat(str);

        // simplify
        String simple = simplify(str);
        // split into item
        if (valid) {
            Matcher m = Pattern.compile(RegexConst.itemRegex).matcher(simple);
            while (m.find()) {
                Item unit = new Item(m.group());
                itemMerge(unit);
            }
        }
    }

    private String simplify(String str) {
        String newStr = str;
        newStr = newStr.replaceAll("\\s", "");
        newStr = newStr.replaceAll("(\\+\\+)|(--)", "+");
        newStr = newStr.replaceAll("(\\+-)|(-\\+)", "-");
        return newStr;
    }

    public Boolean getValid() {
        return valid;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public Poly getDerivate() {
        Poly newPoly = new Poly();
        for (Item item : itemList) {
            ArrayList<Item> itemDer = DerivateMethod.itemDer(item);
            for (Item newItem : itemDer) {
                newPoly.itemMerge(newItem);
            }
            // newPoly.itemList.addAll(DerivateMethod.itemDer(item));
        }
        return newPoly;
    }

    public void addItem(Item item) {
        itemMerge(item);
    }

    public String toString() {
        // sort poly's item by item's coefficient
        itemList.sort(new ItemComparator());
        StringBuilder str = new StringBuilder();
        Iterator<Item> itr = this.itemList.iterator();
        // empty
        if (!itr.hasNext()) {
            str.append("0");
        }
        // not empty
        else {
            while (itr.hasNext()) {
                Item i = itr.next();
                String itemStr = i.toString();
                if (!(itemStr.isEmpty() || itemStr.equals("0"))) {
                    str.append("+");
                    str.append(itemStr);
                }
            }
        }
        String strStd = str.toString();
        // format simplification
        strStd = strStd.replaceAll("\\+\\+|--", "+");
        strStd = strStd.replaceAll("-\\+|\\+-", "-");
        if (strStd.charAt(0) == '+') {
            strStd = strStd.substring(1);
        }
        return strStd;
    }

    private boolean checkFormat(String str) {
        // character use check
        if (Pattern.compile(RegexConst.forbidRegex).matcher(str).find()) {
            return false;
        }
        // illegal space check
        if (Pattern.compile(RegexConst.illegalSpaceRegex).matcher(str).find()) {
            return false;
        }

        // clear space and format expression for further check
        String temp = str.replaceAll("\\s", "");
        if (temp.isEmpty()) {
            return false;
        }
        if (!(temp.charAt(0) == '+' | temp.charAt(0) == '-')) {
            temp = "+" + temp;
        }
        // sequence check
        String regex = "[+-]" + RegexConst.itemRegex;
        Matcher match = Pattern.compile(regex).matcher(temp);
        int head = 0;
        while (match.find(head) && match.start() == head) {
            head = match.end();
        }
        return (head == temp.length());
    }

    private void itemMerge(Item item) {
        boolean flag = false;
        Iterator<Item> ite = itemList.iterator();
        while (ite.hasNext()) {
            Item i = ite.next();
            // a include b && b include a -> a = b
            if (i.include(item) && item.include(i)) {
                i.setCoe(i.getCoe().add(item.getCoe()));
                // clear if it's zero
                if (i.getCoe().equals(BigInteger.ZERO)) {
                    ite.remove();
                }
                flag = true;
                break;
            }
        }

        // ignore item with coefficient is zero.
        if (!flag && !item.getCoe().equals(BigInteger.ZERO)) {
            itemList.add(item);
        }
    }
}
