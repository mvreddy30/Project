package com.zork;

import java.util.Scanner;

public class ZorkUtil {
    /**Scanner initialization to scan the console inputs by player*/
    public static Scanner consoleScanner = new Scanner(System.in);

    /**
     * used to print the messages on console
     * @param message
     *          message to be printed
     */
    public static void print(String message) {
        System.out.println(message);
    }

    /**
     * used to take the input value using Scanner
     * @return
     */
    public static String receiveValue(){
        return consoleScanner.nextLine();
    }

    /**
     * used to take the Game input value using Scanner
     * @return
     */
    public static String receiveGameInputValue() {
        System.out.print("\n>");
        return consoleScanner.nextLine();
    }
}
