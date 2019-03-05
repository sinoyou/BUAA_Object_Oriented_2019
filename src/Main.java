import java.util.Scanner;

public class Main {
    public static void main(String[] arg) {
        Scanner scan = new Scanner(System.in);
        if (scan.hasNextLine()) {
            // if any exception happens when running, print "WRONG FORMAT".
            try {
                Poly poly = new Poly(scan.nextLine());
                if (poly.getFormat()) {
                    System.out.println(poly.getDerivativePoly());
                } else {
                    System.out.println("WRONG FORMAT!");
                }
            } catch (Exception e) {
                System.out.println("WRONG FORMAT!");
                System.exit(0);
            }
        } else {
            System.out.println("WRONG FORMAT!");
        }
        scan.close();
    }
}
