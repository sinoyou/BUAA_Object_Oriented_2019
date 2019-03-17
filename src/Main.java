import parse.Parse;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        if(scan.hasNextLine()){
            String line = scan.nextLine();
            Parse parse = new Parse(line);
        }else {
            System.out.println("WRONG FORMAT!");
            System.err.println("Empty Line.");
        }
    }
}
