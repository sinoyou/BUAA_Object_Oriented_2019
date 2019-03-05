import java.util.ArrayList;

public class main_test {
    public static void main(String[] arg){
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
        System.out.println("Wrong Format Checking.");
        strItem.add("+++5*x");
        strItem.add("--+5*x");
        strItem.add("X");
        strItem.add("Y");
        strItem.add("5**x^101");
        strItem.add("5*x^*-101");
        strItem.add("5x");
        strItem.add("6*x+-101x");
        strItem.add("6*x-+-5");
        strItem.add("-1 00000000");
        strItem.add("- 100000000");
        strItem.add("-10000000 0");
        strItem.add("+10000000  *       x           ^       -199900");
        strItem.add("+10000000  *    x ^     - 199900");
        strItem.add("+10 000000*    x^-199900");
        strItem.add("-x      ^    +        00031");
        strItem.add("- x ^ + 00031");
        strItem.add("-x^+0 0031");
        for (int i = 0; i < strItem.size(); i++) {
            Poly poly = new Poly(strItem.get(i));
            if (poly.getFormat()) {
                System.out.print(poly.getOriginalPoly() + " <----> ");
                System.out.println(poly.getDerivativePoly());
            } else {
                System.out.println("WRONG FORMAT!");
            }
        }
    }
}
