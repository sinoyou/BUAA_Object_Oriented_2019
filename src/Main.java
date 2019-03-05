// import java.util.ArrayList;

import java.util.Scanner;

public class Main {    // test function
    public static void main(String[] arg) {
        Scanner scan = new Scanner(System.in);
        if (scan.hasNextLine()) {
            Poly poly = new Poly(scan.nextLine());
            if (poly.getFormat()) {
                System.out.println(poly.getDerivativePoly());
            } else {
                System.out.println("WRONG FORMAT!");
            }
        } else {
            System.out.println("WRONG FORMAT!");
        }
        /*
                ArrayList<String> strItem = new ArrayList<>();
                strItem.add("+ -5*x^5 - 6 + 7*x + x^5");
                strItem.add("1926 0817 * x");
                strItem.add("127 * x+ -0 - -x   -x   ^ -0+   -09*   x ^-123");
                strItem.add("1");
                strItem.add("4*x+x^2+x");
                strItem.add("4*x+x^2+x");
                strItem.add("4x+x^2+x");
                strItem.add("- -4*x + x ^ 2 + x");
                strItem.add("+4*x - -x^2 + x");
                strItem.add("+19260817*x");
                strItem.add("+ 19260817*x");
                strItem.add("+ +19260817*x");
                strItem.add("++ 19260817*x");
                strItem.add("1926 0817 * x");
                strItem.add(" ");
                strItem.add("");
                for (int i = 0; i < strItem.size(); i++) {
                    Poly poly = new Poly(strItem.get(i));
                    if (poly.formatCheck()) {
                        System.out.print(poly.getOriginalPoly() + " <----> ");
                        System.out.println(poly.getDerivativePoly());
                    } else {
                        System.out.println("WRONG FORMAT!");
                    }
                }
     */
    }
}
