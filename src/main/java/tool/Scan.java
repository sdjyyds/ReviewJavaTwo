package tool;

import java.util.Scanner;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public abstract class Scan {
    private static final Scanner scanner = new Scanner(System.in);
    private Scan(){};
    public static Scanner getScanner(){
        return scanner;
    }
    public static void closeScanner(){
        scanner.close();
    }
}
