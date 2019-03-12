import poly.Poly;
import poly.PolyTrigoSimple;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scan = new Scanner(System.in);
            if (scan.hasNextLine()) {
                String str = scan.nextLine();
                Poly poly = new Poly(str);
                Poly polySimple = new PolyTrigoSimple(str);
                if (poly.getValid()) {
                    String strStd = poly.getDerivate().toString();
                    String strSimple = polySimple.getDerivate().toString();
                    if (strSimple.length() < strStd.length()) {
                        System.out.println(strSimple);
                    } else {
                        System.out.println(strStd);
                    }

                } else {
                    System.out.println("WRONG FORMAT!");
                }
            } else {
                System.out.println("WRONG FORMAT!");
            }
        } catch (Exception e) {
            System.out.println("WRONG FORMAT!");
        }
    }
}
