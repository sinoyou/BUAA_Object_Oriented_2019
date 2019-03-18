import parse.Parse;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try{
            if (scan.hasNextLine()) {
                String line = scan.nextLine();
                Parse parse = new Parse(line);
                if (parse.isValid()) {
                    System.out.println(parse.getRoot().getDerivate().toString());
                } else {
                    System.out.println("WRONG FORMAT!");
                    System.err.println("Can't meet primary request.");
                }
            } else {
                System.out.println("WRONG FORMAT!");
                System.err.println("Empty Line.");
            }
        }catch (Exception e){
            System.out.println("WRONG FORMAT!");
            System.err.println("Unknown Exception:"+e.toString());
        }
    }
}
