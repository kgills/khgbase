/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khgbase;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Khgbase {

    /* This can be changed to whatever you like */
    static String prompt = "khgq> ";
    static boolean isExit = false;
    /*
     * Page size for alll files is 512 bytes by default.
     * You may choose to make it user modifiable
     */
    static long pageSize = 512;

    /* 
     *  The Scanner class is used to collect user commands from the prompt
     *  There are many ways to do this. This is just one.
     *
     *  Each time the semicolon (;) delimiter is entered, the userCommand 
     *  String is re-populated.
     */
    static Scanner scanner = new Scanner(System.in).useDelimiter(";");

    /**
     * ***********************************************************************
     * Main method
     *
     * @param args
     */
    public static void main(String[] args) {

        /* Display the welcome screen */
        splashScreen();

        /* Variable to collect user input from the prompt */
        String userCommand = "";

        while (!isExit) {
            System.out.print(prompt);
            /* toLowerCase() renders command case insensitive */
            userCommand = scanner.next().replace("\n", "").replace("\r", "").trim().toLowerCase();
            parseUserCommand(userCommand);
        }
        System.out.println("Exiting...");

    }

    public static void splashScreen() {
        System.out.println(line("-", 80));
        System.out.println("Welcome to KHGBaseLite"); // Display the string.
        System.out.println("\nType \"help;\" to display supported commands.");
        System.out.println(line("-", 80));
    }

    public static String line(String s, int num) {
        String a = "";
        for (int i = 0; i < num; i++) {
            a += s;
        }
        return a;
    }

    public static void help() {
        System.out.println(line("*", 80));
        System.out.println("SUPPORTED COMMANDS");
        System.out.println("All commands below are case insensitive");
        System.out.println();
        System.out.println("\tSELECT * FROM table_name;                        Display all records in the table.");
        System.out.println("\tSELECT * FROM table_name WHERE rowid = <value>;  Display records whose rowid is <id>.");
        System.out.println("\tDROP TABLE table_name;                           Remove table data and its schema.");
        System.out.println("\tHELP;                                            Show this help information");
        System.out.println("\tEXIT;                                            Exit the program");
        System.out.println();
        System.out.println();
        System.out.println(line("*", 80));
    }

    public static void parseUserCommand(String userCommand) {

        /* commandTokens is an array of Strings that contains one token per array element 
         * The first token can be used to determine the type of command 
         * The other tokens can be used to pass relevant parameters to each command-specific
         * method inside each case statement */
        ArrayList<String> commandTokens = new ArrayList<>(Arrays.asList(userCommand.split(" ")));

        switch (commandTokens.get(0)) {
            case "select":
                parseQueryString(userCommand);
                break;
            case "drop":
                parseDropString(userCommand);
                break;
            case "create":
                parseCreateString(userCommand);
                break;
            case "insert":
                parseInsertString(userCommand);
                break;
            case "delete":
                parseDeleteString(userCommand);
                break;
            case "show":
                parseShowString(userCommand);
                break;
            case "update":
                parseUpdateString(userCommand);
                break;
            case "help":
                help();
                break;
            case "exit":
                isExit = true;
                break;
            case "quit":
                isExit = true;
            default:
                System.out.println("I didn't understand the command: \"" + userCommand + "\"");
                break;
        }
    }

    public static void parseDropString(String commandString) {
        System.out.println("STUB: Method for drop");
    }

    public static void parseQueryString(String commandString) {
        System.out.println("STUB: Method for query");
    }

    public static void parseCreateString(String commandString) {

        System.out.println("STUB: Method for create");
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(commandString.split(" ")));

        // TODO: Need to check token length

        /* Define table file name */
        String tableFileName = tokens.get(2) + ".tbl";

        /* YOUR CODE GOES HERE */
        /*  Code to create a .tbl file to contain table data */
        try {
            /*  Create RandomAccessFile tableFile in read-write mode.
             *  Note that this doesn't create the table file in the correct directory structure
             */
            RandomAccessFile tableFile = new RandomAccessFile(tableFileName, "rw");
            tableFile.setLength(pageSize);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void parseInsertString(String commandString) {
        System.out.println("STUB: Method for insert");
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(commandString.split(" ")));
    }

    public static void parseDeleteString(String commandString) {
        System.out.println("STUB: Method for delete");
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(commandString.split(" ")));
    }

    public static void parseShowString(String commandString) {
        System.out.println("STUB: Method for show");
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(commandString.split(" ")));
    }

    public static void parseUpdateString(String commandString) {
        System.out.println("STUB: Method for update");
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(commandString.split(" ")));
    }
}
