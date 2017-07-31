/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khgbase;

import java.io.IOException;
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
    
    static String tablesCatalogFile = "../data/catalog/khgbase_tables.tbl";
    static String columnsCatalogFile = "../data/catalog/khgbase_tables.tbl";

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
        userCommand = "create khgbase_tables ( rowid int NOT_NULL, "
                + "table_name text NOT_NULL)";
        
        System.out.println(userCommand);
        parseUserCommand(userCommand);
        userCommand = "create khgbase_columns ( rowid int NOT_NULL, "
                + "table_name text NOT_NULL, "
                + "column_name text NOT_NULL, "
                + "data_type text NOT_NULL, "
                + "ordinal_position tinyint NOT_NULL "
                + "is_nullable text NOT_NULL)";
        System.out.println(userCommand);
        parseUserCommand(userCommand);

//        while (!isExit) {
//            System.out.print(prompt);
//            /* toLowerCase() renders command case insensitive */
//            userCommand = scanner.next().replace("\n", "").replace("\r", "").trim().toLowerCase();
//            
//            userCommand = create khgbase_tables ( rowid int, table_name text);
//            System.out.println(userCommand);
//            parseUserCommand(userCommand);
//        }
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

    public static class Table {

        String name;
        String fileName;
        ArrayList<String> columnNames = new ArrayList<>();
        ArrayList<String> columnTypes = new ArrayList<>();
        ArrayList<String> columnProps = new ArrayList<>();
       
    }

    public static void insert(){
        
    }
    
    public static void createTable(Table table) {
        
        byte pageType_leafTable = 0x0D;
        
        
        
        /* YOUR CODE GOES HERE */
        /*  Code to create a .tbl file to contain table data */
        try {
            /*  Create RandomAccessFile tableFile in read-write mode. 
             Assuming that exiting files do not exist, will overwrite
             */
            RandomAccessFile tableFile = new RandomAccessFile(table.fileName, "rw");
            tableFile.setLength(0);
            tableFile.setLength(pageSize);
            
            tableFile.seek(0);
            
            // Set the file type
            tableFile.write(pageType_leafTable);
            
            // Set the number of records
            tableFile.write(0x00);
            
            // Initialize the start of content and right page pointer
            for(int i = 0; i < 6; i++) {
                tableFile.write(0xFF);
            }
            
            tableFile.close();
            
            /* Add this table to the tables and columns catalog */
            

            /* Write the header to the table file */
        } catch (IOException e) {
            System.out.println(e);
        }
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

        int table_name_pos = 1;
        Table table = new Table();

        // Remove the parens
        commandString = commandString.replace("(", "");
        commandString = commandString.replace(")", "");

        // Split the string into tokens
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(commandString.split(" ")));

        // Remove any tokens with just whitespace
        int token_len = tokens.size();
        for (int i = 0; i < token_len; i++) {
            if (tokens.get(i).toString().trim().length() == 0) {
                tokens.remove(i);
                i--;
                token_len--;
            }
        }

        // Add the file path
        table.name = tokens.get(table_name_pos).toString();
        switch (table.name) {
            case "khgbase_tables":
            case "khgbase_columns":
                table.fileName = "../data/catalog/" + table.name + ".tbl";
                break;
            default:
                table.fileName = "../data/user_data/" + table.name + ".tbl";
        }

        System.out.println("table_file_name = " + table.fileName);
        System.out.println("table_name = " + table.name);

        // Parse the remainer of the tokens for the column names, types, and properties
        int columnPos = 0;
        for (int i = table_name_pos + 1; i < token_len; i++) {

            if (columnPos == 0) {
                // Add a new columnName
                table.columnNames.add(tokens.get(i).toString());
                columnPos = 1;
            } else if (columnPos == 1) {
                // Add the columnType
                table.columnTypes.add(tokens.get(i).toString());

                if (tokens.get(i).contains(",")) {
                    // The properties is NULL, add the default
                    table.columnProps.add("None");
                    columnPos = 0;
                } else {

                    if (i == token_len - 1) {
                        // Add the none if this is the last token
                        table.columnProps.add("None");
                    }
                    columnPos = 2;
                }

            } else {
                // Add the column Properties
                table.columnProps.add(tokens.get(i).toString());
                columnPos = 0;
            }
        }

        createTable(table);
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
