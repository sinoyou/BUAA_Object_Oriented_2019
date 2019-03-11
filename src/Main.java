import poly.Poly;
import poly.PolyTrigoSimple;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scan = new Scanner(System.in);
            if (scan.hasNextLine()) {
                Poly poly = new PolyTrigoSimple(scan.nextLine());
                if (poly.getValid()) {
                    System.out.println(poly.getDerivate().toString());
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
