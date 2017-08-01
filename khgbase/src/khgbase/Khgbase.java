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
    static int pageSize = 512;

    static final int RECORD_NULL_TINYINT = 0x00;
    static final int RECORD_NULL_SMALLINT = 0x01;
    static final int RECORD_NULL_INT = 0x02;
    static final int RECORD_NULL_DOUBLE = 0x03;
    static final int RECORD_TINYINT = 0x04;
    static final int RECORD_SMALLINT = 0x05;
    static final int RECORD_INT = 0x06;
    static final int RECORD_BIGINT = 0x07;
    static final int RECORD_REAL = 0x08;
    static final int RECORD_DOUBLE = 0x09;
    static final int RECORD_DATETIME = 0x0A;
    static final int RECORD_DATE = 0x0B;
    static final int RECORD_TEXT = 0x0C;

    static final int RECORD_NULL_TINYINT_LEN = 1;
    static final int RECORD_NULL_SMALLINT_LEN = 2;
    static final int RECORD_NULL_INT_LEN = 4;
    static final int RECORD_NULL_DOUBLE_LEN = 8;
    static final int RECORD_TINYINT_LEN = 1;
    static final int RECORD_SMALLINT_LEN = 2;
    static final int RECORD_INT_LEN = 4;
    static final int RECORD_BIGINT_LEN = 8;
    static final int RECORD_REAL_LEN = 4;
    static final int RECORD_DOUBLE_LEN = 8;
    static final int RECORD_DATETIME_LEN = 8;
    static final int RECORD_DATE_LEN = 8;

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

    // TODO: Assuming that we start with a fresh database
    static int tableCount = 2;
    static int columnCount = 10;

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

//        userCommand = "create table test ( rowid int NOT NULL, "
//                + "table_name text NOT NULL, "
//                + "column_name text NOT NULL, "
//                + "data_type text NOT NULL, "
//                + "ordinal_position tinyint NOT NULL "
//                + "is_nullable text NOT NULL)";
//        userCommand = "select * from khgbase_tables";
        userCommand = "select rowid, table_name from khgbase_tables";
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

    public static class Record {

        String tableName;
        ArrayList<String> values = new ArrayList<>();
    }

    public static class Query {

        String tableName = "";
        ArrayList<String> columnNames = new ArrayList<>();
        ArrayList<String> columnWhere = new ArrayList<>();
        ArrayList<String> columnOps = new ArrayList<>();
        ArrayList<String> columnOpValues = new ArrayList<>();

    }

    public static void insert(Record record) {
        // TODO: Insert the given record into the propper file
        // ASSUMPTION: The table given in this function actually exists
        // TODO: Going to do a non-sorted insert at first for simplicity
        // TODO: Not going to do any indexing or tree structure at first for simplicity
        //          Will be a unordered linked list of blocks

        // Open the file
        // Seek to the next block if this one has a pointer
        // See if we have to allocate more space for this file
        // Insert the contents
        // Close the file
    }

    public static void createTable(Table table) {

        byte pageType_leafTable = 0x0D;

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
            for (int i = 0; i < 6; i++) {
                tableFile.write(0xFF);
            }

            tableFile.close();

            /* Add this table to the tables table in the catalog */
            Record tableRecord = new Record();
            tableRecord.tableName = "khgbase_tables";
            tableRecord.values.add(Integer.toString(tableCount++));
            tableRecord.values.add(table.name);
            insert(tableRecord);

            /* Add the columns to the column table in the catalog */
            int ord = 1;
            for (int i = 0; i < table.columnNames.size(); i++) {
                Record columnRecord = new Record();

                columnRecord.tableName = "khgbase_columns";
                columnRecord.values.add(Integer.toString(columnCount++));
                columnRecord.values.add(table.name);
                columnRecord.values.add(table.columnNames.get(i));
                columnRecord.values.add(table.columnTypes.get(i).toUpperCase());
                columnRecord.values.add(Integer.toString(ord++));
                if (table.columnProps.get(i).equals("NOT_NULL")) {
                    columnRecord.values.add("NO");
                } else {
                    columnRecord.values.add("YES");
                }

                insert(columnRecord);
            }

            /* Write the header to the table file */
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void select(Query query) {
        // TODO: Execute the query and print the results
        // TODO: Currently ignoring the where conditions

        int numRecordOffset = 1;
        int firstRecordOffset = 8;
        int recordOffsetSize = 2;

        // Translate the path to the table
        // Add the file path
        String tableFileName;
        switch (query.tableName) {
            case "khgbase_tables":
            case "khgbase_columns":
                tableFileName = "../data/catalog/" + query.tableName + ".tbl";
                break;
            default:
                tableFileName = "../data/user_data/" + query.tableName + ".tbl";
        }

        // Figure out which columns we need to retrieve by getting the 
        // ordinality from the columns table in the catalog file
        // Open the columns table

        // Open the table
        try {
            // Open the file
            // ASSUMTION: Assuming that the file exists
            RandomAccessFile tableFile = new RandomAccessFile(tableFileName, "r");

            int lastPage = 0;
            int pageCount = 0;
            while (lastPage == 0) {
                // Get the number of records
                int pageStartOffset = pageCount * pageSize;
                tableFile.seek(numRecordOffset + pageStartOffset);
                int numRecords = tableFile.readByte();
                int contentOffset = tableFile.readShort();
                int nextPage = tableFile.readInt();

                // Check if there is another block
                if (nextPage == -1) {
                    lastPage = 1;
                }

                // Print all of the records on this page
                for (int record = 0; record < numRecords; record++) {
                    tableFile.seek(pageStartOffset + firstRecordOffset + record * recordOffsetSize);
                    int recordOffset = tableFile.readShort() + pageStartOffset;
                    tableFile.seek(recordOffset);

                    int recordSize = tableFile.readShort();
                    int rowid = tableFile.readInt();
                    int numColumns = tableFile.read();

                    System.out.println("recordSize = " + recordSize);
                    System.out.println("rowid = " + rowid);
                    System.out.println("num_records = " + numColumns);

                    // Save the record types
                    ArrayList<Integer> columnTypes = new ArrayList<>();
                    for (int i = 0; i < numColumns; i++) {
                        columnTypes.add(tableFile.read());
                    }

                    // Print the rowid
                    System.out.print(rowid+"\t");
                    
                    // Get the data
                    for (int i = 0; i < numColumns; i++) {
                        switch (columnTypes.get(i)) {
                            case RECORD_NULL_TINYINT:
                                tableFile.skipBytes(RECORD_NULL_TINYINT_LEN);
                                System.out.print("NULL");
                                break;
                            case RECORD_NULL_SMALLINT:
                                tableFile.skipBytes(RECORD_NULL_SMALLINT_LEN);
                                System.out.print("NULL");
                                break;
                            case RECORD_NULL_INT:
                                tableFile.skipBytes(RECORD_NULL_INT_LEN);
                                System.out.print("NULL");
                                break;
                            case RECORD_NULL_DOUBLE:
                                tableFile.skipBytes(RECORD_NULL_DOUBLE_LEN);
                                System.out.print("NULL");
                                break;
                            case RECORD_TINYINT:
                                System.out.print(tableFile.readByte());
                                break;
                            case RECORD_SMALLINT:
                                System.out.print(tableFile.readShort());
                                break;
                            case RECORD_INT:
                                System.out.print(tableFile.readInt());
                                break;
                            case RECORD_BIGINT:
                                System.out.print(tableFile.readLong());
                                break;
                            case RECORD_REAL:
                                System.out.print(tableFile.readFloat());
                                break;
                            case RECORD_DOUBLE:
                                System.out.print(tableFile.readDouble());
                                break;
                            case RECORD_DATETIME:
                                // TODO: Convert this to a datetime
                                System.out.print(tableFile.readLong());
                                break;
                            case RECORD_DATE:
                                // TODO: Convert this to a date
                                System.out.print(tableFile.readLong());
                                break;
                            default:
                                // This is a text type
                                int dataLen = columnTypes.get(i) - RECORD_TEXT;
                                for(int j = 0; j < dataLen; j++) {
                                    System.out.print((char)tableFile.readByte());
                                }
                                break;
                        }
                        
                        System.out.println("\t ");
                    }

                }

                System.out.println("numRecords = " + numRecords);
            }

            tableFile.close();


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
                parseSelectString(userCommand);
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

    public static void parseSelectString(String commandString) {

        // TODO: Assuming that we are not getting conditions
        Query query = new Query();

        // Split command string on from to get the column names
        commandString = commandString.replace("select ", "");
        String columnNames = commandString.split("from")[0];
        ArrayList<String> columns = new ArrayList<>(Arrays.asList(columnNames.split(",")));
        String tableName = commandString.split("from")[1].trim();

        query.tableName = tableName;
        // Trim the column names and add to the query
        for (int i = 0; i < columns.size(); i++) {
            columns.set(i, columns.get(i).trim());
            query.columnNames.add(columns.get(i));
        }

        System.out.println("Table = " + tableName);
        System.out.println("Columns:");
        for (int i = 0; i < columns.size(); i++) {
            System.out.println(columns.get(i));
        }

        select(query);
    }

    public static void parseCreateString(String commandString) {

        int table_name_pos = 1;
        Table table = new Table();

        // Remove the parens
        commandString = commandString.replace("(", "");
        commandString = commandString.replace(")", "");

        // Convert "NOT NULL" to "NOT_NULL" to make the parsing easier
        commandString = commandString.replace("NOT NULL", "NOT_NULL");

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
