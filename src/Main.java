import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        if(scan.hasNextLine()){
            Poly poly = new Poly(scan.nextLine());
            if(poly.getValid())
                System.out.println(PrintMethod.printExpression(poly.getDerivate()));
            else
                System.out.println("WRONG FORMAT!");
        }
    }
}
